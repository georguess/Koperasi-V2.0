package com.koperasi.service;

import com.koperasi.model.Anggota;
import com.koperasi.model.Pinjaman;
import com.koperasi.model.StatusPinjaman;
import com.koperasi.repository.UserRepository;
import com.koperasi.util.CurrencyFormatter;
import com.koperasi.util.IDGenerator;

import java.util.List;

/** Logika bisnis pengelolaan anggota (fitur Admin: Kelola Anggota). */
public class AnggotaService {

    private final UserRepository userRepository;
    private final PinjamanService pinjamanService;
    private final SimpananService simpananService;

    public AnggotaService(UserRepository userRepository, PinjamanService pinjamanService, SimpananService simpananService) {
        this.userRepository = userRepository;
        this.pinjamanService = pinjamanService;
        this.simpananService = simpananService;
    }

    public Anggota tambahAnggota(String namaLengkap, String username, String password,
                                 String alamat, String noTelepon) {
        String id = IDGenerator.generate("USR");
        String nomorAnggota = IDGenerator.generate("AGT");
        Anggota anggota = new Anggota(id, username, password, namaLengkap,
                nomorAnggota, alamat, noTelepon);
        userRepository.simpan(anggota);
        return anggota;
    }

    public List<Anggota> getSemuaAnggota() {
        return userRepository.ambilSemuaAnggota();
    }

    public void hapusAnggota(Anggota anggota) {
        // Cek apakah anggota memiliki pinjaman aktif (pengajuan atau disetujui yang belum lunas)
        List<Pinjaman> pinjamans = pinjamanService.getByAnggota(anggota);
        for (Pinjaman p : pinjamans) {
            if (p.getStatus() == StatusPinjaman.PENGAJUAN) {
                throw new IllegalStateException("Tidak dapat menghapus anggota. Anggota memiliki pengajuan pinjaman aktif (" + p.getId() + ") yang belum diproses.");
            }
            if (p.getStatus() == StatusPinjaman.DISETUJUI && p.getSisaPinjaman() > 0) {
                throw new IllegalStateException("Tidak dapat menghapus anggota. Anggota memiliki pinjaman berjalan yang belum lunas (Sisa: " + CurrencyFormatter.format(p.getSisaPinjaman()) + ").");
            }
        }

        // Cek apakah anggota memiliki saldo simpanan
        double totalSimpanan = simpananService.getTotalSimpanan(anggota);
        if (totalSimpanan > 0) {
            throw new IllegalStateException("Tidak dapat menghapus anggota. Anggota masih memiliki saldo simpanan sebesar " + CurrencyFormatter.format(totalSimpanan) + ". Lakukan penarikan simpanan terlebih dahulu.");
        }

        userRepository.hapus(anggota);
    }

    public boolean usernameDipakai(String username) {
        return userRepository.cariByUsername(username) != null;
    }
}
