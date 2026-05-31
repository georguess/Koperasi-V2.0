package com.koperasi.model;

/** Enum peran pengguna dalam sistem koperasi. */
public enum Role {
    ADMIN("Administrator"),
    KASIR("Kasir"),
    ANGGOTA("Anggota");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
