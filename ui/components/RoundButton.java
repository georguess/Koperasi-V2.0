package com.koperasi.ui.components;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Tombol dengan sudut membulat dan warna yang bisa diatur.
 * Otomatis menjadi sedikit lebih gelap saat ditekan dan lebih terang saat di-hover.
 */
public class RoundButton extends JButton {

    private Color baseColor;

    public RoundButton(String text, Color baseColor) {
        super(text);
        this.baseColor = baseColor;
        setForeground(Color.WHITE);
        setFont(UIConstants.FONT_BOLD);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
        repaint();
    }

    public Color getBaseColor() {
        return baseColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color fill = baseColor;
        if (getModel().isPressed()) {
            fill = baseColor.darker();
        } else if (getModel().isRollover()) {
            fill = brighten(baseColor);
        }

        g2.setColor(fill);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
        g2.dispose();

        super.paintComponent(g);
    }

    private Color brighten(Color c) {
        int r = Math.min(255, (int) (c.getRed() + (255 - c.getRed()) * 0.12));
        int g = Math.min(255, (int) (c.getGreen() + (255 - c.getGreen()) * 0.12));
        int b = Math.min(255, (int) (c.getBlue() + (255 - c.getBlue()) * 0.12));
        return new Color(r, g, b);
    }
}
