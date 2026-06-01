package com.koperasi.ui.admin;

import com.koperasi.app.AppContext;
import com.koperasi.model.Pinjaman;
import com.koperasi.ui.components.RoundedPanel;
import com.koperasi.ui.components.UIConstants;
import com.koperasi.ui.components.UIHelper;
import com.koperasi.util.CurrencyFormatter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

/** Fitur Admin: ringkasan angka penting + rekap seluruh pinjaman. */
public class LaporanPanel extends JPanel {

    private final AppContext context;

    public LaporanPanel(AppContext context) {
        this.context = context;
        setBackground(UIConstants.BACKGROUND);
        setLayout(new BorderLayout(0, 20));
        add(buildStats(), BorderLayout.NORTH);
        add(buildTable(), BorderLayout.CENTER);
    }

    private JPanel buildStats() {
        JPanel grid = new JPanel(new GridLayout(1, 4, 16, 0));
        grid.setOpaque(false);

        var laporan = context.getLaporanService();
        grid.add(UIHelper.statCard("Total Anggota",
                String.valueOf(laporan.totalAnggota()), UIConstants.PRIMARY));
        grid.add(UIHelper.statCard("Total Simpanan",
                CurrencyFormatter.format(laporan.totalSimpanan()), UIConstants.SUCCESS));
        grid.add(UIHelper.statCard("Total Piutang Berjalan",
                CurrencyFormatter.format(laporan.totalPiutangBerjalan()), UIConstants.ACCENT));
        grid.add(UIHelper.statCard("Total Angsuran Diterima",
                CurrencyFormatter.format(laporan.totalAngsuranDiterima()), UIConstants.PRIMARY_DARK));
        return grid;
    }

    private JPanel buildTable() {
        RoundedPanel card = new RoundedPanel(16, UIConstants.CARD);
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(BorderFactory.createEmptyBorder(20, 22, 22, 22));

        JLabel judul = new JLabel("Rekapitulasi Pinjaman");
        judul.setFont(UIConstants.FONT_HEADING);
        judul.setForeground(UIConstants.TEXT);

        String[] kolom = {"ID", "Anggota", "Pokok", "Total Kewajiban", "Sudah Dibayar", "Sisa", "Status"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        List<Pinjaman> semua = context.getPinjamanService().getSemua();
        for (Pinjaman p : semua) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getAnggota().getNamaLengkap(),
                    CurrencyFormatter.format(p.getPokok()),
                    CurrencyFormatter.format(p.getTotalKewajiban()),
                    CurrencyFormatter.format(p.getTotalDibayar()),
                    CurrencyFormatter.format(p.getSisaPinjaman()),
                    p.getStatus().toString()
            });
        }
        JTable table = new JTable(model);
        UIHelper.styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER));
        scroll.setPreferredSize(new Dimension(0, 300));

        card.add(judul, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        return card;
    }
}
