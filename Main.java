package com.koperasi;

import com.koperasi.app.AppContext;
import com.koperasi.ui.LoginFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Titik masuk aplikasi Koperasi Simpan Pinjam.
 * Menyiapkan look-and-feel bawaan sistem lalu menampilkan layar login.
 */
public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Jika gagal, tetap lanjut dengan look-and-feel default.
        }

        AppContext context = new AppContext();
        SwingUtilities.invokeLater(() -> new LoginFrame(context).setVisible(true));
    }
}
