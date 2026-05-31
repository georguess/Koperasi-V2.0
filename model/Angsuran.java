package com.koperasi.model;

import java.time.LocalDate;

/** Satu kali pembayaran angsuran terhadap sebuah pinjaman. */
public class Angsuran {

    private String id;
    private int angsuranKe;
    private double jumlah;
    private LocalDate tanggal;

    public Angsuran(String id, int angsuranKe, double jumlah) {
        this.id = id;
        this.angsuranKe = angsuranKe;
        this.jumlah = jumlah;
        this.tanggal = LocalDate.now();
    }

    public String getId() { return id; }
    public int getAngsuranKe() { return angsuranKe; }
    public double getJumlah() { return jumlah; }
    public LocalDate getTanggal() { return tanggal; }
}
