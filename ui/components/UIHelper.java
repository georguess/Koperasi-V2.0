package com.koperasi.ui.components;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

/**
 * Kumpulan fungsi pembantu untuk membuat komponen UI yang seragam:
 * gaya tabel, judul, kolom input, label, dan kartu statistik.
 */
public final class UIHelper {

    private UIHelper() {
    }

    /** Menyeragamkan tampilan tabel agar rapi dan modern. */
    public static void styleTable(JTable table) {
        table.setRowHeight(34);
        table.setFont(UIConstants.FONT_NORMAL);
        table.setShowVerticalLines(false);
        table.setGridColor(UIConstants.BORDER);
        table.setSelectionBackground(new Color(0xD7ECE6));
        table.setSelectionForeground(UIConstants.TEXT);
        table.setFillsViewportHeight(true);
        table.setBackground(Color.WHITE);
        table.setForeground(UIConstants.TEXT);

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 38));
        header.setReorderingAllowed(false);
        
        // Use a custom renderer to force the header background and foreground color
        javax.swing.table.DefaultTableCellRenderer headerRenderer = new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(UIConstants.PRIMARY);
                c.setForeground(Color.WHITE);
                c.setFont(UIConstants.FONT_BOLD);
                ((javax.swing.JComponent) c).setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, UIConstants.BORDER));
                return c;
            }
        };
        headerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        header.setDefaultRenderer(headerRenderer);
    }

    public static JLabel title(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UIConstants.FONT_TITLE);
        label.setForeground(UIConstants.TEXT);
        return label;
    }

    public static JLabel subtitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UIConstants.FONT_NORMAL);
        label.setForeground(UIConstants.TEXT_MUTED);
        return label;
    }

    public static JLabel fieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UIConstants.FONT_SMALL);
        label.setForeground(UIConstants.TEXT_MUTED);
        return label;
    }

    /** Kolom input teks dengan garis tepi membulat dan padding di dalam. */
    public static JTextField textField() {
        JTextField field = new JTextField();
        field.setFont(UIConstants.FONT_NORMAL);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER, 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        return field;
    }

    /**
     * Kartu statistik untuk dashboard/laporan: judul kecil di atas,
     * angka besar berwarna di bawah.
     */
    public static RoundedPanel statCard(String judul, String nilai, Color aksen) {
        RoundedPanel card = new RoundedPanel(16, UIConstants.CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JLabel judulLabel = new JLabel(judul.toUpperCase());
        judulLabel.setFont(UIConstants.FONT_SMALL);
        judulLabel.setForeground(UIConstants.TEXT_MUTED);
        judulLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nilaiLabel = new JLabel(nilai);
        nilaiLabel.setFont(UIConstants.FONT_TITLE);
        nilaiLabel.setForeground(aksen);
        nilaiLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(judulLabel);
        card.add(javax.swing.Box.createVerticalStrut(8));
        card.add(nilaiLabel);
        return card;
    }
}
