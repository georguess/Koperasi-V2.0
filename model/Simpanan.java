package com.koperasi.model;

import java.time.LocalDate;

/** Catatan satu transaksi simpanan milik seorang anggota (ASOSIASI ke Anggota). */
public class Simpanan {

    private String id;
    private Anggota anggota;
    private JenisSimpanan jenis;
    private double jumlah;
    private LocalDate tanggal;

    public Simpanan(String id, Anggota anggota, JenisSimpanan jenis, double jumlah) {
        this.id = id;
        this.anggota = anggota;
        this.jenis = jenis;
        this.jumlah = jumlah;
        this.tanggal = LocalDate.now();
    }

    public String getId() { return id; }
    public Anggota getAnggota() { return anggota; }
    public JenisSimpanan getJenis() { return jenis; }
    public double getJumlah() { return jumlah; }
    public LocalDate getTanggal() { return tanggal; }

    public void setJumlah(double jumlah) { this.jumlah = jumlah; }
    public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }
}
