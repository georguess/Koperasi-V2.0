package com.koperasi.model;

import java.time.LocalDate;

/**
 * Anggota koperasi. Selain dapat login (mewarisi User), anggota juga
 * menyimpan data keanggotaan. Contoh PEWARISAN dengan tambahan atribut.
 */
public class Anggota extends User {

    private String nomorAnggota;
    private String alamat;
    private String noTelepon;
    private LocalDate tanggalBergabung;
    private boolean aktif;

    public Anggota(String id, String username, String password, String namaLengkap,
                   String nomorAnggota, String alamat, String noTelepon) {
        super(id, username, password, namaLengkap, Role.ANGGOTA);
        this.nomorAnggota = nomorAnggota;
        this.alamat = alamat;
        this.noTelepon = noTelepon;
        this.tanggalBergabung = LocalDate.now();
        this.aktif = true;
    }

    @Override
    public String getDashboardTitle() {
        return "Dashboard Anggota";
    }

    public String getNomorAnggota() { return nomorAnggota; }
    public void setNomorAnggota(String nomorAnggota) { this.nomorAnggota = nomorAnggota; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }

    public LocalDate getTanggalBergabung() { return tanggalBergabung; }
    public void setTanggalBergabung(LocalDate tanggalBergabung) { this.tanggalBergabung = tanggalBergabung; }

    public boolean isAktif() { return aktif; }
    public void setAktif(boolean aktif) { this.aktif = aktif; }

    public String getStatusText() {
        return aktif ? "Aktif" : "Nonaktif";
    }
}
