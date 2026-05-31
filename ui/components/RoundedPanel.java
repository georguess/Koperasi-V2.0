package com.koperasi.ui.components;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Panel dengan sudut membulat. Dipakai sebagai "kartu" pembungkus konten.
 */
public class RoundedPanel extends JPanel {

    private final int radius;
    private final Color background;

    public RoundedPanel(int radius, Color background) {
        this.radius = radius;
        this.background = background;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(background);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();
        super.paintComponent(g);
    }
}
