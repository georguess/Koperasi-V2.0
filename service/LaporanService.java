package com.koperasi.service;

import com.koperasi.model.Pinjaman;
import com.koperasi.model.StatusPinjaman;
import com.koperasi.repository.PinjamanRepository;
import com.koperasi.repository.UserRepository;

/** Menyusun ringkasan data untuk fitur Admin: Laporan. */
public class LaporanService {

    private final UserRepository userRepository;
    private final SimpananService simpananService;
    private final PinjamanRepository pinjamanRepository;

    public LaporanService(UserRepository userRepository,
                          SimpananService simpananService,
                          PinjamanRepository pinjamanRepository) {
        this.userRepository = userRepository;
        this.simpananService = simpananService;
        this.pinjamanRepository = pinjamanRepository;
    }

    public int totalAnggota() {
        return userRepository.ambilSemuaAnggota().size();
    }

    public double totalSimpanan() {
        return simpananService.getTotalSeluruhSimpanan();
    }

    public int jumlahPinjamanPengajuan() {
        return pinjamanRepository.cariByStatus(StatusPinjaman.PENGAJUAN).size();
    }

    public int jumlahPinjamanDisetujui() {
        return pinjamanRepository.cariByStatus(StatusPinjaman.DISETUJUI).size();
    }

    public double totalPinjamanDisetujui() {
        double total = 0;
        for (Pinjaman p : pinjamanRepository.cariByStatus(StatusPinjaman.DISETUJUI)) {
            total += p.getTotalKewajiban();
        }
        return total;
    }

    public double totalPiutangBerjalan() {
        double total = 0;
        for (Pinjaman p : pinjamanRepository.cariByStatus(StatusPinjaman.DISETUJUI)) {
            total += p.getSisaPinjaman();
        }
        return total;
    }

    public double totalAngsuranDiterima() {
        double total = 0;
        for (Pinjaman p : pinjamanRepository.ambilSemua()) {
            if (p.getStatus() == StatusPinjaman.DISETUJUI || p.getStatus() == StatusPinjaman.LUNAS) {
                total += p.getTotalDibayar();
            }
        }
        return total;
    }
}
