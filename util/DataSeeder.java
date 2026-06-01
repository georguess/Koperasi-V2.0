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

        // --- 20 Anggota Dummy ---
        String[][] dummyData = {
                {"Andi Wijaya", "andi", "andi123", "Jl. Melati No. 10, Jakarta", "081234567890"},
                {"Dewi Lestari", "dewi", "dewi123", "Jl. Kenanga No. 5, Bandung", "081298765432"},
                {"Rini Susanti", "rini", "rini123", "Jl. Mawar No. 15, Surabaya", "081345678901"},
                {"Budi Hartono", "budi", "budi123", "Jl. Tulip No. 20, Medan", "081456789012"},
                {"Siti Nurhaliza", "siti", "siti123", "Jl. Matahari No. 8, Yogyakarta", "081567890123"},
                {"Ahmad Rafsanjani", "ahmad", "ahmad123", "Jl. Bulan No. 12, Semarang", "081678901234"},
                {"Ratna Wijaya", "ratna", "ratna123", "Jl. Bintang No. 6, Makassar", "081789012345"},
                {"Hendra Kusuma", "hendra", "hendra123", "Jl. Gunung No. 18, Palembang", "081890123456"},
                {"Tini Hasanah", "tini", "tini123", "Jl. Bukit No. 11, Bandarlampung", "081901234567"},
                {"Joko Santoso", "joko", "joko123", "Jl. Lembah No. 14, Medan", "082012345678"},
                {"Rina Wijaya", "rina", "rina123", "Jl. Danau No. 7, Jakarta", "082123456789"},
                {"Bambang Irawan", "bambang", "bambang123", "Jl. Hutan No. 19, Bandung", "082234567890"},
                {"Citra Dewi", "citra", "citra123", "Jl. Pantai No. 9, Bali", "082345678901"},
                {"Doni Pratama", "doni", "doni123", "Jl. Lapangan No. 22, Surabaya", "082456789012"},
                {"Erika Suhardi", "erika", "erika123", "Jl. Kolam No. 3, Yogyakarta", "082567890123"},
                {"Fajar Aditya", "fajar", "fajar123", "Jl. Embun No. 25, Semarang", "082678901234"},
                {"Gina Marlina", "gina", "gina123", "Jl. Pohon No. 13, Makassar", "082789012345"},
                {"Hadi Mulyanto", "hadi", "hadi123", "Jl. Sungai No. 16, Palembang", "082890123456"},
                {"Intan Permata", "intan", "intan123", "Jl. Tepi No. 21, Bandarlampung", "082901234567"},
                {"Jaya Kusuma", "jaya", "jaya123", "Jl. Jalan No. 4, Medan", "083012345678"}
        };

        Anggota[] anggotaList = new Anggota[20];
        for (int i = 0; i < dummyData.length; i++) {
            anggotaList[i] = anggotaService.tambahAnggota(
                    dummyData[i][0],  // nama
                    dummyData[i][1],  // username
                    dummyData[i][2],  // password
                    dummyData[i][3],  // alamat
                    dummyData[i][4]   // telepon
            );
        }

        // --- Simpanan awal untuk semua anggota ---
        for (int i = 0; i < anggotaList.length; i++) {
            // Setiap anggota punya simpanan pokok dan wajib dengan nominal berbeda
            long saldoPokok = 50000 + (i * 25000);  // Berkisar 50rb - 525rb
            long saldoWajib = 25000 + (i * 15000);  // Berkisar 25rb - 310rb
            
            simpananService.setor(anggotaList[i], JenisSimpanan.POKOK, saldoPokok);
            simpananService.setor(anggotaList[i], JenisSimpanan.WAJIB, saldoWajib);
            
            // Beberapa anggota memiliki simpanan sukarela
            if (i % 3 == 0) {
                simpananService.setor(anggotaList[i], JenisSimpanan.SUKARELA, 75000 + (i * 10000));
            }
        }

        // --- Contoh pengajuan pinjaman (beberapa menunggu persetujuan) ---
        pinjamanService.ajukan(anggotaList[0], 2000000, 5, 10);
        pinjamanService.ajukan(anggotaList[1], 3000000, 5, 12);
        pinjamanService.ajukan(anggotaList[5], 1500000, 4, 8);
    }
}
