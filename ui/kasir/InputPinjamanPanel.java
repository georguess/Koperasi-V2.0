package com.koperasi.ui.kasir;

import com.koperasi.app.AppContext;
import com.koperasi.model.Anggota;
import com.koperasi.model.Pinjaman;
import com.koperasi.ui.components.RoundButton;
import com.koperasi.ui.components.RoundedPanel;
import com.koperasi.ui.components.SearchableComboBox;
import com.koperasi.ui.components.UIConstants;
import com.koperasi.ui.components.UIHelper;
import com.koperasi.util.CurrencyFormatter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
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
import java.awt.GridLayout;
import java.util.List;

/** Fitur Kasir: memasukkan pengajuan pinjaman baru atas nama anggota. */
public class InputPinjamanPanel extends JPanel {

    private final AppContext context;
    private SearchableComboBox<Anggota> comboAnggota;
    private final JTextField fPokok = UIHelper.textField();
    private final JTextField fBunga = UIHelper.textField();
    private final JTextField fTenor = UIHelper.textField();
    private DefaultTableModel tableModel;

    public InputPinjamanPanel(AppContext context) {
        this.context = context;
        setBackground(UIConstants.BACKGROUND);
        setLayout(new BorderLayout(0, 20));
        add(buildForm(), BorderLayout.NORTH);
        add(buildTable(), BorderLayout.CENTER);
        fBunga.setText("5");
        fTenor.setText("12");
        refresh();
    }

    private JPanel buildForm() {
        RoundedPanel card = new RoundedPanel(16, UIConstants.CARD);
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(BorderFactory.createEmptyBorder(20, 22, 22, 22));

        JLabel judul = new JLabel("Form Pengajuan Pinjaman");
        judul.setFont(UIConstants.FONT_HEADING);
        judul.setForeground(UIConstants.TEXT);

        comboAnggota = new SearchableComboBox<>(
                context.getAnggotaService().getSemuaAnggota().toArray(new Anggota[0]));
        comboAnggota.setFont(UIConstants.FONT_NORMAL);
        comboAnggota.setBackground(UIConstants.CARD);
        comboAnggota.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER, 1, true));

        JPanel grid = new JPanel(new GridLayout(1, 4, 16, 0));
        grid.setOpaque(false);
        grid.add(labeled("Anggota", comboAnggota));
        grid.add(labeled("Pokok Pinjaman (Rp)", fPokok));
        grid.add(labeled("Bunga (%)", fBunga));
        grid.add(labeled("Tenor (bulan)", fTenor));

        RoundButton tombol = new RoundButton("Ajukan Pinjaman", UIConstants.PRIMARY);
        tombol.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        tombol.addActionListener(e -> ajukan());
        JPanel tombolBox = new JPanel(new BorderLayout());
        tombolBox.setOpaque(false);
        tombolBox.setBorder(BorderFactory.createEmptyBorder(14, 0, 0, 0));
        tombolBox.add(tombol, BorderLayout.WEST);

        JPanel isi = new JPanel(new BorderLayout());
        isi.setOpaque(false);
        isi.add(grid, BorderLayout.CENTER);
        isi.add(tombolBox, BorderLayout.SOUTH);

        card.add(judul, BorderLayout.NORTH);
        card.add(isi, BorderLayout.CENTER);
        return card;
    }

    private JPanel labeled(String label, JComponent field) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        JLabel l = UIHelper.fieldLabel(label);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        p.add(l);
        p.add(Box.createVerticalStrut(6));
        p.add(field);
        return p;
    }

    private JPanel buildTable() {
        RoundedPanel card = new RoundedPanel(16, UIConstants.CARD);
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(BorderFactory.createEmptyBorder(20, 22, 22, 22));

        JLabel judul = new JLabel("Daftar Pengajuan & Pinjaman");
        judul.setFont(UIConstants.FONT_HEADING);
        judul.setForeground(UIConstants.TEXT);

        String[] kolom = {"ID", "Anggota", "Pokok", "Total Kewajiban", "Angsuran/Bln", "Status"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        UIHelper.styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER));
        scroll.setPreferredSize(new Dimension(0, 260));

        card.add(judul, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        return card;
    }

    private void ajukan() {
        Anggota anggota = (Anggota) comboAnggota.getSelectedItem();
        if (anggota == null) {
            JOptionPane.showMessageDialog(this,
                    "Belum ada anggota. Tambahkan anggota terlebih dahulu.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double pokok;
        double bunga;
        int tenor;
        try {
            pokok = Double.parseDouble(fPokok.getText().trim());
            bunga = Double.parseDouble(fBunga.getText().trim());
            tenor = Integer.parseInt(fTenor.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Pokok, bunga, dan tenor harus berupa angka yang valid.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (pokok <= 0 || tenor <= 0 || bunga < 0) {
            JOptionPane.showMessageDialog(this,
                    "Pokok & tenor harus lebih dari 0, bunga tidak boleh negatif.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        context.getPinjamanService().ajukan(anggota, pokok, bunga, tenor);
        JOptionPane.showMessageDialog(this,
                "Pengajuan pinjaman untuk " + anggota.getNamaLengkap()
                        + " berhasil dibuat dan menunggu persetujuan Admin.",
                "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        fPokok.setText("");
        refresh();
    }

    private void refresh() {
        tableModel.setRowCount(0);
        List<Pinjaman> daftar = context.getPinjamanService().getSemua();
        for (Pinjaman p : daftar) {
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getAnggota().getNamaLengkap(),
                    CurrencyFormatter.format(p.getPokok()),
                    CurrencyFormatter.format(p.getTotalKewajiban()),
                    CurrencyFormatter.format(p.getAngsuranPerBulan()),
                    p.getStatus().toString()
            });
        }
    }
}
