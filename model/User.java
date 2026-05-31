package com.koperasi.model;

/**
 * Kelas ABSTRAK induk untuk seluruh pengguna sistem.
 * Menerapkan ENKAPSULASI (atribut private + getter/setter) dan
 * menjadi dasar PEWARISAN bagi Admin, Kasir, dan Anggota.
 */
public abstract class User {

    private String id;
    private String username;
    private String password;
    private String namaLengkap;
    private final Role role;

    /** Konstruktor dipanggil oleh subclass melalui super(...). */
    protected User(String id, String username, String password,
                   String namaLengkap, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.namaLengkap = namaLengkap;
        this.role = role;
    }

    /** Method abstrak -> tiap peran memberi judul dashboard berbeda (POLIMORFISME). */
    public abstract String getDashboardTitle();

    /** Verifikasi password tanpa mengekspos field password. */
    public boolean cekPassword(String input) {
        return password != null && password.equals(input);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }

    public Role getRole() { return role; }

    @Override
    public String toString() {
        return namaLengkap + " (" + role.getLabel() + ")";
    }
}
