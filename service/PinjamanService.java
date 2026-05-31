package com.koperasi.service;

import com.koperasi.model.Anggota;
import com.koperasi.model.Angsuran;
import com.koperasi.model.Pinjaman;
import com.koperasi.model.StatusPinjaman;
import com.koperasi.repository.PinjamanRepository;
import com.koperasi.util.IDGenerator;

import java.util.List;

/** Logika bisnis pinjaman (input pengajuan, approve/tolak, terima angsuran). */
public class PinjamanService {

    private final PinjamanRepository pinjamanRepository;

    public PinjamanService(PinjamanRepository pinjamanRepository) {
        this.pinjamanRepository = pinjamanRepository;
    }

    /** Kasir mengajukan pinjaman atas nama anggota -> status PENGAJUAN. */
    public Pinjaman ajukan(Anggota anggota, double pokok, double bungaPersen, int tenorBulan) {
        String id = IDGenerator.generate("PJM");
        Pinjaman pinjaman = new Pinjaman(id, anggota, pokok, bungaPersen, tenorBulan);
        return pinjamanRepository.simpan(pinjaman);
    }

    public List<Pinjaman> getByStatus(StatusPinjaman status) {
        return pinjamanRepository.cariByStatus(status);
    }

    public List<Pinjaman> getByAnggota(Anggota anggota) {
        return pinjamanRepository.cariByAnggota(anggota);
    }

    public List<Pinjaman> getSemua() {
        return pinjamanRepository.ambilSemua();
    }

    /** Admin menyetujui pinjaman. */
    public void setujui(Pinjaman pinjaman) {
        pinjaman.setStatus(StatusPinjaman.DISETUJUI);
    }

    /** Admin menolak pinjaman. */
    public void tolak(Pinjaman pinjaman) {
        pinjaman.setStatus(StatusPinjaman.DITOLAK);
    }

    /** Kasir menerima pembayaran angsuran. */
    public Angsuran terimaAngsuran(Pinjaman pinjaman, double jumlah) {
        int ke = pinjaman.getDaftarAngsuran().size() + 1;
        String id = IDGenerator.generate("ANG");
        Angsuran angsuran = new Angsuran(id, ke, jumlah);
        pinjaman.tambahAngsuran(angsuran);
        return angsuran;
    }
}
