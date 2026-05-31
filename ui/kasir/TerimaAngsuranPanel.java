package com.koperasi.ui.kasir;

import com.koperasi.app.AppContext;
import com.koperasi.model.Pinjaman;
import com.koperasi.model.StatusPinjaman;
import com.koperasi.ui.components.RoundButton;
import com.koperasi.ui.components.RoundedPanel;
import com.koperasi.ui.components.UIConstants;
import com.koperasi.ui.components.UIHelper;
import com.koperasi.util.CurrencyFormatter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

/** Fitur Kasir: menerima pembayaran angsuran dari pinjaman yang berjalan. */
public class TerimaAngsuranPanel extends JPanel {

    private final AppContext context;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Pinjaman> dataAktif;
    private final JTextField fJumlah = UIHelper.textField();

    public TerimaAngsuranPanel(AppContext context) {
        this.context = context;
        setBackground(UIConstants.BACKGROUND);
        setLayout(new BorderLayout(0, 18));
        add(buildTable(), BorderLayout.CENTER);
        add(buildForm(), BorderLayout.SOUTH);
        refresh();
    }

    private JPanel buildTable() {
        RoundedPanel card = new RoundedPanel(16, UIConstants.CARD);
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(BorderFactory.createEmptyBorder(20, 22, 22, 22));

        JLabel judul = new JLabel("Pinjaman Berjalan");
        judul.setFont(UIConstants.FONT_HEADING);
        judul.setForeground(UIConstants.TEXT);

        String[] kolom = {"ID", "Anggota", "Total Kewajiban", "Sudah Dibayar", "Sisa", "Angsuran/Bln"};
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
        scroll.setPreferredSize(new Dimension(0, 300));

        card.add(judul, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildForm() {
        RoundedPanel card = new RoundedPanel(16, UIConstants.CARD);
        card.setLayout(new BorderLayout(16, 0));
        card.setBorder(BorderFactory.createEmptyBorder(16, 22, 16, 22));

        JPanel kiri = new JPanel();
        kiri.setOpaque(false);
        kiri.setLayout(new BoxLayout(kiri, BoxLayout.Y_AXIS));
        JLabel l = UIHelper.fieldLabel("Jumlah Angsuran (Rp) untuk baris terpilih");
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        fJumlah.setAlignmentX(Component.LEFT_ALIGNMENT);
        fJumlah.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        kiri.add(l);
        kiri.add(Box.createVerticalStrut(6));
        kiri.add(fJumlah);

        RoundButton bayar = new RoundButton("Bayar Angsuran", UIConstants.PRIMARY);
        bayar.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        bayar.addActionListener(e -> bayar());
        JPanel kanan = new JPanel(new BorderLayout());
        kanan.setOpaque(false);
        kanan.add(bayar, BorderLayout.SOUTH);

        card.add(kiri, BorderLayout.CENTER);
        card.add(kanan, BorderLayout.EAST);
        return card;
    }

    private void bayar() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                    "Pilih pinjaman yang akan dibayar terlebih dahulu.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double jumlah;
        try {
            jumlah = Double.parseDouble(fJumlah.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Jumlah harus berupa angka.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (jumlah <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Jumlah harus lebih besar dari 0.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Pinjaman pinjaman = dataAktif.get(row);
        context.getPinjamanService().terimaAngsuran(pinjaman, jumlah);
        String pesan = "Angsuran " + CurrencyFormatter.format(jumlah)
                + " untuk " + pinjaman.getAnggota().getNamaLengkap() + " dicatat.";
        if (pinjaman.getStatus() == StatusPinjaman.LUNAS) {
            pesan += "\nPinjaman kini LUNAS.";
        }
        JOptionPane.showMessageDialog(this, pesan, "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        fJumlah.setText("");
        refresh();
    }

    private void refresh() {
        tableModel.setRowCount(0);
        dataAktif = context.getPinjamanService().getByStatus(StatusPinjaman.DISETUJUI);
        for (Pinjaman p : dataAktif) {
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getAnggota().getNamaLengkap(),
                    CurrencyFormatter.format(p.getTotalKewajiban()),
                    CurrencyFormatter.format(p.getTotalDibayar()),
                    CurrencyFormatter.format(p.getSisaPinjaman()),
                    CurrencyFormatter.format(p.getAngsuranPerBulan())
            });
        }
    }
}
