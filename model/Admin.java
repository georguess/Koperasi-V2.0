package com.koperasi.model;

/** Pengguna dengan hak akses tertinggi: kelola anggota, approve pinjaman, laporan. */
public class Admin extends User {

    public Admin(String id, String username, String password, String namaLengkap) {
        super(id, username, password, namaLengkap, Role.ADMIN);
    }

    @Override
    public String getDashboardTitle() {
        return "Dashboard Administrator";
    }
}
