package com.koperasi.model;

/** Pengguna operasional: setor simpanan, terima angsuran, input pengajuan pinjaman. */
public class Kasir extends User {

    public Kasir(String id, String username, String password, String namaLengkap) {
        super(id, username, password, namaLengkap, Role.KASIR);
    }

    @Override
    public String getDashboardTitle() {
        return "Dashboard Kasir";
    }
}
