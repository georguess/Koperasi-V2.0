package com.koperasi.util;

import com.koperasi.model.Admin;
import com.koperasi.model.Anggota;
import com.koperasi.model.JenisSimpanan;
import com.koperasi.model.Kasir;
import com.koperasi.repository.UserRepository;
import com.koperasi.service.AnggotaService;
import com.koperasi.service.PinjamanService;
import com.koperasi.service.SimpananService;

/**
 * Mengisi data awal (dummy) agar aplikasi langsung bisa dicoba.
 * Membuat akun admin, kasir, dan beberapa anggota beserta simpanan
 * serta satu contoh pengajuan pinjaman.
 */
public final class DataSeeder {

    private DataSeeder() {
    }

    public static void seed(UserRepository userRepository,
                            AnggotaService anggotaService,
                            SimpananService simpananService,
                            PinjamanService pinjamanService) {

        // --- Akun pengelola ---
        Admin admin = new Admin(IDGenerator.generate("USR"), "admin", "admin123", "Budi Santoso");
        Kasir kasir = new Kasir(IDGenerator.generate("USR"), "kasir", "kasir123", "Siti Aminah");
        userRepository.simpan(admin);
        userRepository.simpan(kasir);

        // --- Anggota ---
        Anggota andi = anggotaService.tambahAnggota(
                "Andi Wijaya", "andi", "andi123",
                "Jl. Melati No. 10, Jakarta", "081234567890");
        Anggota dewi = anggotaService.tambahAnggota(
                "Dewi Lestari", "dewi", "dewi123",
                "Jl. Kenanga No. 5, Bandung", "081298765432");

        // --- Simpanan awal ---
        simpananService.setor(andi, JenisSimpanan.POKOK, 100000);
        simpananService.setor(andi, JenisSimpanan.WAJIB, 50000);
        simpananService.setor(dewi, JenisSimpanan.POKOK, 100000);

        // --- Contoh pengajuan pinjaman (menunggu persetujuan admin) ---
        pinjamanService.ajukan(andi, 2000000, 5, 10);
    }
}
