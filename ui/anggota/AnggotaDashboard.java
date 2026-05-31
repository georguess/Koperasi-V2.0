package com.koperasi.ui.anggota;

import com.koperasi.app.AppContext;
import com.koperasi.model.Anggota;
import com.koperasi.ui.BaseDashboard;

/**
 * Dashboard untuk peran Anggota. Sesuai gambar, anggota hanya dapat
 * melihat saldo simpanan dan status keanggotaan/pinjamannya sendiri.
 */
public class AnggotaDashboard extends BaseDashboard {

    public AnggotaDashboard(AppContext context, Anggota anggota) {
        super(context, anggota);
    }

    @Override
    protected void buildMenu() {
        // getUser() dipakai (bukan field subclass) karena buildMenu()
        // berjalan saat konstruktor super, sebelum field subclass terisi.
        addMenu("Saldo & Status", new SaldoStatusPanel(context, (Anggota) getUser()));
    }
}
