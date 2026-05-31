package com.koperasi.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Memformat angka menjadi format Rupiah, contoh: 1500000 -> "Rp 1.500.000".
 * Menggunakan titik sebagai pemisah ribuan sesuai kebiasaan di Indonesia.
 */
public final class CurrencyFormatter {

    private static final DecimalFormat FORMAT;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        FORMAT = new DecimalFormat("#,##0", symbols);
    }

    private CurrencyFormatter() {
    }

    public static String format(double jumlah) {
        return "Rp " + FORMAT.format(jumlah);
    }
}
