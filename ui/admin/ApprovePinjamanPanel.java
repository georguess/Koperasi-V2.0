package com.koperasi.ui.admin;

import com.koperasi.app.AppContext;
import com.koperasi.model.Pinjaman;
import com.koperasi.model.StatusPinjaman;
import com.koperasi.ui.components.RoundButton;
import com.koperasi.ui.components.RoundedPanel;
import com.koperasi.ui.components.UIConstants;
import com.koperasi.ui.components.UIHelper;
import com.koperasi.util.CurrencyFormatter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

/** Fitur Admin: menyetujui atau menolak pengajuan pinjaman. */
public class ApprovePinjamanPanel extends JPanel {

    private final AppContext context;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Pinjaman> dataPengajuan;

    public ApprovePinjamanPanel(AppContext context) {
        this.context = context;
        setBackground(UIConstants.BACKGROUND);
        setLayout(new BorderLayout());
        add(buildCard(), BorderLayout.CENTER);
        refresh();
    }

    private JPanel buildCard() {
        RoundedPanel card = new RoundedPanel(16, UIConstants.CARD);
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(BorderFactory.createEmptyBorder(20, 22, 22, 22));

        JLabel judul = new JLabel("Pengajuan Pinjaman Menunggu Persetujuan");
        judul.setFont(UIConstants.FONT_HEADING);
        judul.setForeground(UIConstants.TEXT);

        String[] kolom = {"ID", "Anggota", "Pokok", "Bunga", "Tenor", "Total Kewajiban", "Angsuran/Bln"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        UIHelper.styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER));
        scroll.setPreferredSize(new Dimension(0, 320));

        RoundButton setujui = new RoundButton("Setujui", UIConstants.SUCCESS);
        setujui.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        setujui.addActionListener(e -> proses(true));

        RoundButton tolak = new RoundButton("Tolak", UIConstants.DANGER);
        tolak.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        tolak.addActionListener(e -> proses(false));

        JPanel aksi = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        aksi.setOpaque(false);
        aksi.add(tolak);
        aksi.add(setujui);

        card.add(judul, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        card.add(aksi, BorderLayout.SOUTH);
        return card;
    }

    private void proses(boolean setujui) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                    "Pilih satu pengajuan terlebih dahulu.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Pinjaman pinjaman = dataPengajuan.get(row);
        if (setujui) {
            context.getPinjamanService().setujui(pinjaman);
            JOptionPane.showMessageDialog(this,
                    "Pinjaman " + pinjaman.getId() + " disetujui.",
                    "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        } else {
            context.getPinjamanService().tolak(pinjaman);
            JOptionPane.showMessageDialog(this,
                    "Pinjaman " + pinjaman.getId() + " ditolak.",
                    "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        }
        refresh();
    }

    private void refresh() {
        tableModel.setRowCount(0);
        dataPengajuan = context.getPinjamanService().getByStatus(StatusPinjaman.PENGAJUAN);
        for (Pinjaman p : dataPengajuan) {
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getAnggota().getNamaLengkap(),
                    CurrencyFormatter.format(p.getPokok()),
                    p.getBungaPersen() + " %",
                    p.getTenorBulan() + " bln",
                    CurrencyFormatter.format(p.getTotalKewajiban()),
                    CurrencyFormatter.format(p.getAngsuranPerBulan())
            });
        }
    }
}
