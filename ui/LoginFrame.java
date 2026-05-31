package com.koperasi.ui;

import com.koperasi.app.AppContext;
import com.koperasi.model.Admin;
import com.koperasi.model.Anggota;
import com.koperasi.model.Kasir;
import com.koperasi.model.User;
import com.koperasi.ui.admin.AdminDashboard;
import com.koperasi.ui.anggota.AnggotaDashboard;
import com.koperasi.ui.kasir.KasirDashboard;
import com.koperasi.ui.components.RoundButton;
import com.koperasi.ui.components.UIConstants;
import com.koperasi.ui.components.UIHelper;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
//import javax.swing.SwingConstants;
//import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Layar login. Mengenali akun berdasarkan username & password, lalu
 * membuka dashboard yang SESUAI dengan peran (Admin / Kasir / Anggota).
 * Pemilihan dashboard memakai instanceof => contoh POLIMORFISME.
 */
public class LoginFrame extends JFrame {

    private final AppContext context;
    private JTextField fieldUsername;
    private JPasswordField fieldPassword;
    private JLabel errorLabel;

    public LoginFrame(AppContext context) {
        this.context = context;

        setTitle("Koperasi Simpan Pinjam - Login");
        setSize(880, 540);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        add(buildBrandingPanel());
        add(buildFormPanel());
    }

    /** Panel kiri: branding dengan latar gradien. */
    private JPanel buildBrandingPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                        0, 0, UIConstants.PRIMARY,
                        0, getHeight(), UIConstants.PRIMARY_DARK);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));

        JLabel logo = new JLabel("\u25C9");
        logo.setFont(logo.getFont().deriveFont(64f));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel baris1 = new JLabel("KOPERASI");
        baris1.setFont(UIConstants.FONT_LOGO);
        baris1.setForeground(Color.WHITE);
        baris1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel baris2 = new JLabel("SIMPAN PINJAM");
        baris2.setFont(UIConstants.FONT_LOGO);
        baris2.setForeground(Color.WHITE);
        baris2.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel tagline = new JLabel("Sejahtera Bersama Anggota");
        tagline.setFont(UIConstants.FONT_NORMAL);
        tagline.setForeground(new Color(0xD7ECE6));
        tagline.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(logo);
        panel.add(Box.createVerticalStrut(20));
        panel.add(baris1);
        panel.add(baris2);
        panel.add(Box.createVerticalStrut(12));
        panel.add(tagline);
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    /** Panel kanan: form login. */
    private JPanel buildFormPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(UIConstants.CARD);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));

        JLabel judul = new JLabel("Selamat Datang");
        judul.setFont(UIConstants.FONT_TITLE);
        judul.setForeground(UIConstants.TEXT);
        judul.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Silakan masuk untuk melanjutkan");
        sub.setFont(UIConstants.FONT_NORMAL);
        sub.setForeground(UIConstants.TEXT_MUTED);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelUser = UIHelper.fieldLabel("Username");
        labelUser.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldUsername = UIHelper.textField();
        fieldUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        fieldUsername.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelPass = UIHelper.fieldLabel("Password");
        labelPass.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldPassword = new JPasswordField();
        fieldPassword.setFont(UIConstants.FONT_NORMAL);
        fieldPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER, 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        fieldPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        fieldPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldPassword.addActionListener(e -> doLogin());

        JCheckBox showPasswordCb = new JCheckBox("Tampilkan Password");
        showPasswordCb.setFont(UIConstants.FONT_SMALL);
        showPasswordCb.setBackground(UIConstants.CARD);
        showPasswordCb.setForeground(UIConstants.TEXT_MUTED);
        showPasswordCb.setAlignmentX(Component.LEFT_ALIGNMENT);
        showPasswordCb.addActionListener(e -> {
            if (showPasswordCb.isSelected()) {
                fieldPassword.setEchoChar((char) 0);
            } else {
                fieldPassword.setEchoChar('\u2022');
            }
        });

        errorLabel = new JLabel(" ");
        errorLabel.setFont(UIConstants.FONT_SMALL);
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundButton tombolMasuk = new RoundButton("MASUK", UIConstants.PRIMARY);
        tombolMasuk.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        tombolMasuk.setAlignmentX(Component.LEFT_ALIGNMENT);
        tombolMasuk.addActionListener(e -> doLogin());

        panel.add(Box.createVerticalGlue());
        panel.add(judul);
        panel.add(Box.createVerticalStrut(6));
        panel.add(sub);
        panel.add(Box.createVerticalStrut(28));
        panel.add(labelUser);
        panel.add(Box.createVerticalStrut(6));
        panel.add(fieldUsername);
        panel.add(Box.createVerticalStrut(16));
        panel.add(labelPass);
        panel.add(Box.createVerticalStrut(6));
        panel.add(fieldPassword);
        panel.add(Box.createVerticalStrut(4));
        panel.add(showPasswordCb);
        panel.add(Box.createVerticalStrut(6));
        panel.add(errorLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(tombolMasuk);
        panel.add(Box.createVerticalStrut(24));
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private void doLogin() {
        String username = fieldUsername.getText().trim();
        String password = new String(fieldPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Username dan password wajib diisi.");
            return;
        }

        User user = context.getAuthService().login(username, password);
        if (user == null) {
            errorLabel.setText("Username atau password salah.");
            fieldPassword.setText("");
            return;
        }

        errorLabel.setText(" ");
        bukaDashboard(user);
    }

    /** Membuka dashboard sesuai peran user (role-based routing). */
    private void bukaDashboard(User user) {
        JFrame dashboard;
        if (user instanceof Admin) {
            dashboard = new AdminDashboard(context, (Admin) user);
        } else if (user instanceof Kasir) {
            dashboard = new KasirDashboard(context, (Kasir) user);
        } else {
            dashboard = new AnggotaDashboard(context, (Anggota) user);
        }
        dashboard.setVisible(true);
        dispose();
    }
}
