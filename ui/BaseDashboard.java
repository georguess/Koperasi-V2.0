package com.koperasi.ui;

import com.koperasi.app.AppContext;
import com.koperasi.model.User;
import com.koperasi.ui.components.RoundButton;
import com.koperasi.ui.components.UIConstants;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
//import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 * Kerangka dasar dashboard yang dipakai bersama oleh seluruh peran
 * (Admin, Kasir, Anggota) => contoh PEWARISAN di lapisan UI.
 *
 * Struktur: sidebar menu di kiri + area konten di kanan (CardLayout).
 * Tiap subclass cukup menambah menu lewat addMenu(...) di dalam buildMenu().
 *
 * Catatan penting: buildMenu() dipanggil dari konstruktor ini (super),
 * sehingga berjalan SEBELUM field milik subclass terisi. Karena itu di dalam
 * buildMenu() gunakan getUser() dan context (yang sudah di-set di sini),
 * bukan field milik subclass.
 */
public abstract class BaseDashboard extends JFrame {

    protected final AppContext context;
    private final User user;

    private final JPanel content;
    private final java.awt.CardLayout cardLayout;
    private final JPanel navArea;
    private final List<RoundButton> navButtons = new ArrayList<>();
    private final JLabel pageTitle;
    private int jumlahMenu = 0;

    protected BaseDashboard(AppContext context, User user) {
        this.context = context;
        this.user = user;

        this.cardLayout = new java.awt.CardLayout();
        this.content = new JPanel(cardLayout);
        this.navArea = new JPanel();
        this.pageTitle = new JLabel();

        setTitle(user.getDashboardTitle() + " - Koperasi Simpan Pinjam");
        setSize(1040, 660);
        setMinimumSize(new Dimension(940, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.add(buildSidebar(), BorderLayout.WEST);
        root.add(buildMain(), BorderLayout.CENTER);
        setContentPane(root);

        // Diisi oleh subclass; berjalan setelah kerangka siap.
        buildMenu();
    }

    /** Subclass mengisi menu sesuai peran di sini. */
    protected abstract void buildMenu();

    protected User getUser() {
        return user;
    }

    /**
     * Menambahkan satu menu: membuat tombol di sidebar + mendaftarkan
     * halaman (panel) ke CardLayout. Menu pertama otomatis tampil.
     */
    protected void addMenu(String label, JComponent panel) {
        final String cardId = "card-" + jumlahMenu;

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        content.add(scroll, cardId);

        RoundButton tombol = new RoundButton(label, UIConstants.SIDEBAR);
        tombol.setHorizontalAlignment(SwingConstants.LEFT);
        tombol.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        tombol.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        tombol.setAlignmentX(Component.LEFT_ALIGNMENT);
        tombol.addActionListener(e -> {
            cardLayout.show(content, cardId);
            pageTitle.setText(label);
            highlight(tombol);
        });

        navButtons.add(tombol);
        navArea.add(tombol);
        navArea.add(javax.swing.Box.createVerticalStrut(6));

        // Menu pertama menjadi tampilan awal.
        if (jumlahMenu == 0) {
            pageTitle.setText(label);
            highlight(tombol);
        }
        jumlahMenu++;

        navArea.revalidate();
        navArea.repaint();
    }

    private void highlight(RoundButton aktif) {
        for (RoundButton b : navButtons) {
            b.setBaseColor(b == aktif ? UIConstants.PRIMARY : UIConstants.SIDEBAR);
        }
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(UIConstants.SIDEBAR);
        sidebar.setPreferredSize(new Dimension(248, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(24, 18, 24, 18));

        JLabel brand = new JLabel("\u25C9  KOPERASI");
        brand.setFont(UIConstants.FONT_HEADING);
        brand.setForeground(Color.WHITE);
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Simpan Pinjam");
        sub.setFont(UIConstants.FONT_SMALL);
        sub.setForeground(new Color(0x9FC4BB));
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);

        navArea.setOpaque(false);
        navArea.setLayout(new BoxLayout(navArea, BoxLayout.Y_AXIS));
        navArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(brand);
        sidebar.add(sub);
        sidebar.add(javax.swing.Box.createVerticalStrut(28));
        sidebar.add(navArea);
        sidebar.add(javax.swing.Box.createVerticalGlue());
        return sidebar;
    }

    private JPanel buildMain() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(UIConstants.BACKGROUND);

        // Header atas
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIConstants.CARD);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UIConstants.BORDER),
                BorderFactory.createEmptyBorder(16, 24, 16, 24)));

        pageTitle.setFont(UIConstants.FONT_TITLE);
        pageTitle.setForeground(UIConstants.TEXT);

        JPanel kanan = new JPanel();
        kanan.setOpaque(false);
        kanan.setLayout(new BoxLayout(kanan, BoxLayout.X_AXIS));

        JPanel userBox = new JPanel();
        userBox.setOpaque(false);
        userBox.setLayout(new BoxLayout(userBox, BoxLayout.Y_AXIS));
        JLabel nama = new JLabel(user.getNamaLengkap());
        nama.setFont(UIConstants.FONT_BOLD);
        nama.setForeground(UIConstants.TEXT);
        nama.setAlignmentX(Component.RIGHT_ALIGNMENT);
        JLabel peran = new JLabel(user.getRole().getLabel());
        peran.setFont(UIConstants.FONT_SMALL);
        peran.setForeground(UIConstants.TEXT_MUTED);
        peran.setAlignmentX(Component.RIGHT_ALIGNMENT);
        userBox.add(nama);
        userBox.add(peran);

        RoundButton keluar = new RoundButton("Keluar", UIConstants.DANGER);
        keluar.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        keluar.addActionListener(e -> doLogout());

        kanan.add(userBox);
        kanan.add(javax.swing.Box.createHorizontalStrut(18));
        kanan.add(keluar);

        header.add(pageTitle, BorderLayout.WEST);
        header.add(kanan, BorderLayout.EAST);

        content.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        content.setBackground(UIConstants.BACKGROUND);

        main.add(header, BorderLayout.NORTH);
        main.add(content, BorderLayout.CENTER);
        return main;
    }

    private void doLogout() {
        int pilih = JOptionPane.showConfirmDialog(this,
                "Yakin ingin keluar dari aplikasi?",
                "Konfirmasi Keluar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (pilih == JOptionPane.YES_OPTION) {
            new LoginFrame(context).setVisible(true);
            dispose();
        }
    }
}
