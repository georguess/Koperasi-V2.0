package com.koperasi.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Pembuat ID unik berurutan per-prefix. Dipakai untuk membuat
 * ID user, nomor anggota, simpanan, pinjaman, dan angsuran.
 *
 * Contoh hasil: USR-0001, AGT-0001, PJM-0002, dst.
 * Class util murni: konstruktor di-private agar tidak bisa di-instansiasi.
 */
public final class IDGenerator {

    private static final Map<String, Integer> counter = new HashMap<>();

    private IDGenerator() {
        // mencegah pembuatan objek
    }

    public static synchronized String generate(String prefix) {
        int next = counter.getOrDefault(prefix, 0) + 1;
        counter.put(prefix, next);
        return String.format("%s-%04d", prefix, next);
    }
}
