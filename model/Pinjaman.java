package com.koperasi.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Pinjaman milik seorang anggota.
 * Menerapkan KOMPOSISI: sebuah pinjaman memiliki daftar Angsuran.
 * Berisi pula method bisnis (hitung total kewajiban, sisa, dll).
 */
public class Pinjaman {

    private String id;
    private Anggota anggota;
    private double pokok;
    private double bungaPersen;
    private int tenorBulan;
    private StatusPinjaman status;
    private LocalDate tanggalPengajuan;
    private final List<Angsuran> daftarAngsuran;

    public Pinjaman(String id, Anggota anggota, double pokok,
                    double bungaPersen, int tenorBulan) {
        this.id = id;
        this.anggota = anggota;
        this.pokok = pokok;
        this.bungaPersen = bungaPersen;
        this.tenorBulan = tenorBulan;
        this.status = StatusPinjaman.PENGAJUAN;
        this.tanggalPengajuan = LocalDate.now();
        this.daftarAngsuran = new ArrayList<>();
    }

    /** Total kewajiban = pokok + bunga. */
    public double getTotalKewajiban() {
        return pokok + (pokok * bungaPersen / 100.0);
    }

    public double getAngsuranPerBulan() {
        return tenorBulan == 0 ? 0 : getTotalKewajiban() / tenorBulan;
    }

    public double getTotalDibayar() {
        double total = 0;
        for (Angsuran a : daftarAngsuran) {
            total += a.getJumlah();
        }
        return total;
    }

    public double getSisaPinjaman() {
        double sisa = getTotalKewajiban() - getTotalDibayar();
        return sisa < 0 ? 0 : sisa;
    }

    /** Menambah angsuran; otomatis menjadi LUNAS bila sisa habis. */
    public void tambahAngsuran(Angsuran angsuran) {
        daftarAngsuran.add(angsuran);
        if (getSisaPinjaman() <= 0) {
            this.status = StatusPinjaman.LUNAS;
        }
    }

    public String getId() { return id; }
    public Anggota getAnggota() { return anggota; }
    public double getPokok() { return pokok; }
    public double getBungaPersen() { return bungaPersen; }
    public int getTenorBulan() { return tenorBulan; }
    public StatusPinjaman getStatus() { return status; }
    public void setStatus(StatusPinjaman status) { this.status = status; }
    public LocalDate getTanggalPengajuan() { return tanggalPengajuan; }
    public List<Angsuran> getDaftarAngsuran() { return daftarAngsuran; }
}
