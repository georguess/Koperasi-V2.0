package com.koperasi.ui.kasir;

import com.koperasi.app.AppContext;
import com.koperasi.model.Anggota;
import com.koperasi.model.JenisSimpanan;
import com.koperasi.model.Simpanan;
import com.koperasi.ui.components.RoundButton;
import com.koperasi.ui.components.RoundedPanel;
import com.koperasi.ui.components.UIConstants;
import com.koperasi.ui.components.UIHelper;
import com.koperasi.util.CurrencyFormatter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** Fitur Kasir: mencatat setoran simpanan anggota. */
public class SetorSimpananPanel extends JPanel {

    private static final DateTimeFormatter TGL = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final AppContext context;
    private JComboBox<Anggota> comboAnggota;
    private JComboBox<JenisSimpanan> comboJenis;
    private final JTextField fSearch = UIHelper.textField();
    private final JTextField fJumlah = UIHelper.textField();
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    public SetorSimpananPanel(AppContext context) {
        this.context = context;
        setBackground(UIConstants.BACKGROUND);
        setLayout(new BorderLayout(0, 20));
        add(buildForm(), BorderLayout.NORTH);
        add(buildTable(), BorderLayout.CENTER);
        refresh();
    }

    private JPanel buildForm() {
        RoundedPanel card = new RoundedPanel(16, UIConstants.CARD);
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(BorderFactory.createEmptyBorder(20, 22, 22, 22));

        JLabel judul = new JLabel("Form Setor Simpanan");
        judul.setFont(UIConstants.FONT_HEADING);
        judul.setForeground(UIConstants.TEXT);

        comboAnggota = new JComboBox<>(
                context.getAnggotaService().getSemuaAnggota().toArray(new Anggota[0]));
        styleCombo(comboAnggota);
        comboJenis = new JComboBox<>(JenisSimpanan.values());
        styleCombo(comboJenis);

        JPanel grid = new JPanel(new GridLayout(1, 3, 16, 0));
        grid.setOpaque(false);
        grid.add(labeled("Anggota", comboAnggota));
        grid.add(labeled("Jenis Simpanan", comboJenis));
        grid.add(labeled("Jumlah (Rp)", fJumlah));

        RoundButton tombol = new RoundButton("Setor", UIConstants.PRIMARY);
        tombol.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        tombol.addActionListener(e -> setor());
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

    private void styleCombo(JComboBox<?> combo) {
        combo.setFont(UIConstants.FONT_NORMAL);
        combo.setBackground(UIConstants.CARD);
        combo.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER, 1, true));
    }

    private JPanel buildTable() {
        RoundedPanel card = new RoundedPanel(16, UIConstants.CARD);
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(BorderFactory.createEmptyBorder(20, 22, 22, 22));

        JLabel judul = new JLabel("Riwayat Setoran");
        judul.setFont(UIConstants.FONT_HEADING);
        judul.setForeground(UIConstants.TEXT);

        String[] kolom = {"ID", "Tanggal", "Anggota", "Jenis", "Jumlah"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Search panel above table
        JPanel searchPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Cari:");
        searchLabel.setFont(UIConstants.FONT_NORMAL);
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(fSearch, BorderLayout.CENTER);
        fSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String text = fSearch.getText().trim();
                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        // Table with sorter and center alignment
        JTable table = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        UIHelper.styleTable(table);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER));
        scroll.setPreferredSize(new Dimension(0, 280));
        // Assemble top panel with title and search
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(judul, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);
        card.add(topPanel, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        return card;
    }

    private void setor() {
        Anggota anggota = (Anggota) comboAnggota.getSelectedItem();
        if (anggota == null) {
            JOptionPane.showMessageDialog(this,
                    "Belum ada anggota. Tambahkan anggota terlebih dahulu.",
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

        JenisSimpanan jenis = (JenisSimpanan) comboJenis.getSelectedItem();
        context.getSimpananService().setor(anggota, jenis, jumlah);
        JOptionPane.showMessageDialog(this,
                "Setoran " + CurrencyFormatter.format(jumlah) + " untuk "
                        + anggota.getNamaLengkap() + " berhasil dicatat.",
                "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        fJumlah.setText("");
        refresh();
    }

    private void refresh() {
        tableModel.setRowCount(0);
        List<Simpanan> daftar = context.getSimpananService().getSemua();
        for (Simpanan s : daftar) {
            tableModel.addRow(new Object[]{
                    s.getId(),
                    s.getTanggal().format(TGL),
                    s.getAnggota().getNamaLengkap(),
                    s.getJenis().toString(),
                    CurrencyFormatter.format(s.getJumlah())
            });
        }
    }
}
