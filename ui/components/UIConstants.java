package com.koperasi.ui.components;

import java.awt.Color;
import java.awt.Font;

/**
 * Kumpulan warna dan font yang dipakai di seluruh aplikasi.
 * Disatukan di sini agar tampilan konsisten dan mudah diubah.
 */
public final class UIConstants {

    private UIConstants() {
    }

    // Warna utama (tema hijau koperasi)
    public static final Color PRIMARY       = new Color(0x10745F);
    public static final Color PRIMARY_DARK  = new Color(0x0B5345);
    public static final Color ACCENT        = new Color(0xF2A900);
    public static final Color BACKGROUND    = new Color(0xF1F4F6);
    public static final Color CARD          = Color.WHITE;
    public static final Color SIDEBAR       = new Color(0x0B5345);
    public static final Color TEXT          = new Color(0x1F2933);
    public static final Color TEXT_MUTED    = new Color(0x66727F);
    public static final Color BORDER        = new Color(0xE2E8F0);
    public static final Color SUCCESS       = new Color(0x1E8E3E);
    public static final Color DANGER        = new Color(0xD93025);

    // Font
    public static final String FAMILY = "Segoe UI";

    public static final Font FONT_LOGO    = new Font(FAMILY, Font.BOLD, 26);
    public static final Font FONT_TITLE   = new Font(FAMILY, Font.BOLD, 22);
    public static final Font FONT_HEADING = new Font(FAMILY, Font.BOLD, 16);
    public static final Font FONT_NORMAL  = new Font(FAMILY, Font.PLAIN, 14);
    public static final Font FONT_BOLD    = new Font(FAMILY, Font.BOLD, 14);
    public static final Font FONT_SMALL   = new Font(FAMILY, Font.PLAIN, 12);
}
