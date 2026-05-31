package com.koperasi.ui.admin;

import com.koperasi.app.AppContext;
import com.koperasi.model.Anggota;
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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

/** Fitur Admin: menambah, menampilkan, dan menghapus anggota. */
public class KelolaAnggotaPanel extends JPanel {

    private final AppContext context;
    private final JTextField fNama = UIHelper.textField();
    private final JTextField fUsername = UIHelper.textField();
    private final JPasswordField fPassword = new JPasswordField();
    private final JTextField fTelepon = UIHelper.textField();
    private final JTextField fAlamat = UIHelper.textField();
    private DefaultTableModel tableModel;
    private JTable table;

    public KelolaAnggotaPanel(AppContext context) {
        this.context = context;
        setBackground(UIConstants.BACKGROUND);
        setLayout(new BorderLayout(0, 20));

        add(buildForm(), BorderLayout.NORTH);
        add(buildTable(), BorderLayout.CENTER);
        refreshTable();
    }

    private JPanel buildForm() {
        RoundedPanel card = new RoundedPanel(16, UIConstants.CARD);
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(BorderFactory.createEmptyBorder(20, 22, 22, 22));

        JLabel judul = new JLabel("Tambah Anggota Baru");
        judul.setFont(UIConstants.FONT_HEADING);
        judul.setForeground(UIConstants.TEXT);

        fPassword.setFont(UIConstants.FONT_NORMAL);
        fPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER, 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));

        JPanel grid = new JPanel(new GridLayout(2, 3, 16, 12));
        grid.setOpaque(false);
        grid.add(kolom("Nama Lengkap", fNama));
        grid.add(kolom("Username", fUsername));
        grid.add(kolom("Password", fPassword));
        grid.add(kolom("No. Telepon", fTelepon));
        grid.add(kolom("Alamat", fAlamat));

        RoundButton tombol = new RoundButton("+  Tambah Anggota", UIConstants.PRIMARY);
        tombol.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        tombol.addActionListener(e -> tambahAnggota());
        JPanel tombolBox = new JPanel(new BorderLayout());
        tombolBox.setOpaque(false);
        tombolBox.add(tombol, BorderLayout.WEST);
        grid.add(tombolBox);

        card.add(judul, BorderLayout.NORTH);
        card.add(grid, BorderLayout.CENTER);
        return card;
    }

    private JPanel kolom(String label, javax.swing.JComponent field) {
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

        JLabel judul = new JLabel("Daftar Anggota");
        judul.setFont(UIConstants.FONT_HEADING);
        judul.setForeground(UIConstants.TEXT);

        String[] kolom = {"No. Anggota", "Nama", "Username", "Telepon", "Total Simpanan", "Status"};
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
        scroll.setPreferredSize(new Dimension(0, 260));

        RoundButton hapus = new RoundButton("Hapus Terpilih", UIConstants.DANGER);
        hapus.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        hapus.addActionListener(e -> hapusAnggota());
        JPanel bawah = new JPanel(new BorderLayout());
        bawah.setOpaque(false);
        bawah.add(hapus, BorderLayout.EAST);

        JPanel atas = new JPanel(new BorderLayout());
        atas.setOpaque(false);
        atas.add(judul, BorderLayout.WEST);

        card.add(atas, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        card.add(bawah, BorderLayout.SOUTH);
        return card;
    }

    private void tambahAnggota() {
        String nama = fNama.getText().trim();
        String username = fUsername.getText().trim();
        String password = new String(fPassword.getPassword());
        String telepon = fTelepon.getText().trim();
        String alamat = fAlamat.getText().trim();

        if (nama.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nama, username, dan password wajib diisi.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (context.getAnggotaService().usernameDipakai(username)) {
            JOptionPane.showMessageDialog(this,
                    "Username sudah dipakai, gunakan yang lain.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        context.getAnggotaService().tambahAnggota(nama, username, password, alamat, telepon);
        JOptionPane.showMessageDialog(this,
                "Anggota \"" + nama + "\" berhasil ditambahkan.",
                "Berhasil", JOptionPane.INFORMATION_MESSAGE);

        fNama.setText("");
        fUsername.setText("");
        fPassword.setText("");
        fTelepon.setText("");
        fAlamat.setText("");
        refreshTable();
    }

    private void hapusAnggota() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                    "Pilih anggota yang ingin dihapus terlebih dahulu.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        List<Anggota> daftar = context.getAnggotaService().getSemuaAnggota();
        Anggota anggota = daftar.get(row);
        int pilih = JOptionPane.showConfirmDialog(this,
                "Hapus anggota \"" + anggota.getNamaLengkap() + "\"?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (pilih == JOptionPane.YES_OPTION) {
            context.getAnggotaService().hapusAnggota(anggota);
            refreshTable();
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Anggota> daftar = context.getAnggotaService().getSemuaAnggota();
        for (Anggota a : daftar) {
            double total = context.getSimpananService().getTotalSimpanan(a);
            tableModel.addRow(new Object[]{
                    a.getNomorAnggota(),
                    a.getNamaLengkap(),
                    a.getUsername(),
                    a.getNoTelepon(),
                    CurrencyFormatter.format(total),
                    a.getStatusText()
            });
        }
    }
}
