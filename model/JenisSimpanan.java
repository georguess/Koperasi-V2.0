package com.koperasi.model;

/** Jenis simpanan yang dikenal koperasi. */
public enum JenisSimpanan {
    POKOK("Simpanan Pokok"),
    WAJIB("Simpanan Wajib"),
    SUKARELA("Simpanan Sukarela");

    private final String label;

    JenisSimpanan(String label) {
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
