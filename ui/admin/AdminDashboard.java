package com.koperasi.ui.admin;

import com.koperasi.app.AppContext;
import com.koperasi.model.Admin;
import com.koperasi.ui.BaseDashboard;

/**
 * Dashboard untuk peran Admin. Menu sesuai gambar:
 * Kelola Anggota, Approve Pinjaman, Laporan.
 */
public class AdminDashboard extends BaseDashboard {

    public AdminDashboard(AppContext context, Admin admin) {
        super(context, admin);
    }

    @Override
    protected void buildMenu() {
        addMenu("Kelola Anggota", new KelolaAnggotaPanel(context));
        addMenu("Approve Pinjaman", new ApprovePinjamanPanel(context));
        addMenu("Laporan", new LaporanPanel(context));
    }
}
