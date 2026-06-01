package com.koperasi.app;

import com.koperasi.repository.PinjamanRepository;
import com.koperasi.repository.SimpananRepository;
import com.koperasi.repository.UserRepository;
import com.koperasi.service.AnggotaService;
import com.koperasi.service.AuthService;
import com.koperasi.service.LaporanService;
import com.koperasi.service.PinjamanService;
import com.koperasi.service.SimpananService;
import com.koperasi.util.DataSeeder;

/**
 * Pusat perakitan aplikasi (dependency wiring).
 * Membuat semua repository lalu semua service, kemudian mengisi data awal.
 * Seluruh layar UI cukup membawa satu objek AppContext untuk mengakses service.
 */
public class AppContext {

    private final AuthService authService;
    private final AnggotaService anggotaService;
    private final SimpananService simpananService;
    private final PinjamanService pinjamanService;
    private final LaporanService laporanService;

    public AppContext() {
        UserRepository userRepository = new UserRepository();
        SimpananRepository simpananRepository = new SimpananRepository();
        PinjamanRepository pinjamanRepository = new PinjamanRepository();

        this.authService = new AuthService(userRepository);
        this.simpananService = new SimpananService(simpananRepository);
        this.pinjamanService = new PinjamanService(pinjamanRepository);
        this.anggotaService = new AnggotaService(userRepository, pinjamanService, simpananService);
        this.laporanService = new LaporanService(userRepository, simpananService, pinjamanRepository);

        DataSeeder.seed(userRepository, anggotaService, simpananService, pinjamanService);
    }

    public AuthService getAuthService() { return authService; }
    public AnggotaService getAnggotaService() { return anggotaService; }
    public SimpananService getSimpananService() { return simpananService; }
    public PinjamanService getPinjamanService() { return pinjamanService; }
    public LaporanService getLaporanService() { return laporanService; }
}
