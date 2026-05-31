package com.koperasi.ui.kasir;

import com.koperasi.app.AppContext;
import com.koperasi.model.Kasir;
import com.koperasi.ui.BaseDashboard;

/**
 * Dashboard untuk peran Kasir. Menu sesuai gambar:
 * Setor Simpanan, Terima Angsuran, Input Pengajuan Pinjaman.
 */
public class KasirDashboard extends BaseDashboard {

    public KasirDashboard(AppContext context, Kasir kasir) {
        super(context, kasir);
    }

    @Override
    protected void buildMenu() {
        addMenu("Setor Simpanan", new SetorSimpananPanel(context));
        addMenu("Terima Angsuran", new TerimaAngsuranPanel(context));
        addMenu("Input Pengajuan Pinjaman", new InputPinjamanPanel(context));
    }
}
