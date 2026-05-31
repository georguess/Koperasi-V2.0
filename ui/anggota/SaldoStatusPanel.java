package com.koperasi.ui.anggota;

import com.koperasi.app.AppContext;
import com.koperasi.model.Anggota;
import com.koperasi.model.Pinjaman;
import com.koperasi.model.Simpanan;
import com.koperasi.ui.components.RoundedPanel;
import com.koperasi.ui.components.UIConstants;
import com.koperasi.ui.components.UIHelper;
import com.koperasi.util.CurrencyFormatter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** Tampilan ringkasan untuk anggota: saldo, profil, dan status pinjaman. */
public class SaldoStatusPanel extends JPanel {

    private static final DateTimeFormatter TGL = DateTimeFormatter.ofPattern("dd MMM yyyy");

    private final AppContext context;
    private final Anggota anggota;

    public SaldoStatusPanel(AppContext context, Anggota anggota) {
        this.context = context;
        this.anggota = anggota;

        setBackground(UIConstants.BACKGROUND);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(buildGreeting());
        add(Box.createVerticalStrut(18));
        add(buildStats());
        add(Box.createVerticalStrut(18));
        add(buildProfil());
        add(Box.createVerticalStrut(18));
        add(buildSimpananTable());
        add(Box.createVerticalStrut(18));
        add(buildPinjamanTable());
    }

    private JPanel buildGreeting() {
        RoundedPanel card = new RoundedPanel(16, UIConstants.PRIMARY);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(22, 24, 22, 24));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        JLabel halo = new JLabel("Halo " + anggota.getNamaLengkap());
        halo.setFont(UIConstants.FONT_TITLE);
        halo.setForeground(Color.WHITE);
        halo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Selamat datang di Koperasi Simpan Pinjam. Berikut ringkasan akun Anda.");
        sub.setFont(UIConstants.FONT_NORMAL);
        sub.setForeground(new Color(0xD7ECE6));
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(halo);
        card.add(Box.createVerticalStrut(6));
        card.add(sub);
        return card;
    }

    private JPanel buildStats() {
        JPanel grid = new JPanel(new GridLayout(1, 3, 16, 0));
        grid.setOpaque(false);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        double totalSimpanan = context.getSimpananService().getTotalSimpanan(anggota);
        int jumlahPinjaman = context.getPinjamanService().getByAnggota(anggota).size();

        grid.add(UIHelper.statCard("Total Simpanan",
                CurrencyFormatter.format(totalSimpanan), UIConstants.PRIMARY));
        grid.add(UIHelper.statCard("Status Keanggotaan",
                anggota.getStatusText(), UIConstants.SUCCESS));
        grid.add(UIHelper.statCard("Jumlah Pinjaman",
                String.valueOf(jumlahPinjaman), UIConstants.ACCENT));
        return grid;
    }

    private JPanel buildProfil() {
        RoundedPanel card = new RoundedPanel(16, UIConstants.CARD);
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(BorderFactory.createEmptyBorder(20, 22, 22, 22));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        JLabel judul = new JLabel("Profil Keanggotaan");
        judul.setFont(UIConstants.FONT_HEADING);
        judul.setForeground(UIConstants.TEXT);

        JPanel grid = new JPanel(new GridLayout(0, 2, 24, 12));
        grid.setOpaque(false);
        grid.add(baris("Nomor Anggota", anggota.getNomorAnggota()));
        grid.add(baris("Nama Lengkap", anggota.getNamaLengkap()));
        grid.add(baris("No. Telepon", anggota.getNoTelepon()));
        grid.add(baris("Tanggal Bergabung", anggota.getTanggalBergabung().format(TGL)));
        grid.add(baris("Alamat", anggota.getAlamat()));

        card.add(judul, BorderLayout.NORTH);
        card.add(grid, BorderLayout.CENTER);
        return card;
    }

    private JPanel baris(String label, String nilai) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        JLabel l = UIHelper.fieldLabel(label);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel v = new JLabel(nilai == null || nilai.isEmpty() ? "-" : nilai);
        v.setFont(UIConstants.FONT_BOLD);
        v.setForeground(UIConstants.TEXT);
        v.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(l);
        p.add(Box.createVerticalStrut(3));
        p.add(v);
        return p;
    }

    private JPanel buildSimpananTable() {
        RoundedPanel card = new RoundedPanel(16, UIConstants.CARD);
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(BorderFactory.createEmptyBorder(20, 22, 22, 22));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 230));

        JLabel judul = new JLabel("Riwayat Simpanan");
        judul.setFont(UIConstants.FONT_HEADING);
        judul.setForeground(UIConstants.TEXT);

        String[] kolom = {"ID", "Tanggal", "Jenis", "Jumlah"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        List<Simpanan> daftar = context.getSimpananService().getByAnggota(anggota);
        for (Simpanan s : daftar) {
            model.addRow(new Object[]{
                    s.getId(),
                    s.getTanggal().format(TGL),
                    s.getJenis().toString(),
                    CurrencyFormatter.format(s.getJumlah())
            });
        }
        JTable table = new JTable(model);
        UIHelper.styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER));
        scroll.setPreferredSize(new Dimension(0, 150));

        card.add(judul, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildPinjamanTable() {
        RoundedPanel card = new RoundedPanel(16, UIConstants.CARD);
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(BorderFactory.createEmptyBorder(20, 22, 22, 22));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 230));

        JLabel judul = new JLabel("Status Pinjaman");
        judul.setFont(UIConstants.FONT_HEADING);
        judul.setForeground(UIConstants.TEXT);

        String[] kolom = {"ID", "Pokok", "Total Kewajiban", "Sudah Dibayar", "Sisa", "Status"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        List<Pinjaman> daftar = context.getPinjamanService().getByAnggota(anggota);
        for (Pinjaman p : daftar) {
            model.addRow(new Object[]{
                    p.getId(),
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
        scroll.setPreferredSize(new Dimension(0, 150));

        card.add(judul, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        return card;
    }
}
