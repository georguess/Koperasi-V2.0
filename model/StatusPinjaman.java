package com.koperasi.model;

/** Status siklus hidup sebuah pinjaman. */
public enum StatusPinjaman {
    PENGAJUAN("Menunggu Persetujuan"),
    DISETUJUI("Disetujui / Berjalan"),
    DITOLAK("Ditolak"),
    LUNAS("Lunas");

    private final String label;

    StatusPinjaman(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
