package com.koperasi.service;

import com.koperasi.model.User;
import com.koperasi.repository.UserRepository;

/** Menangani proses autentikasi (login) pengguna. */
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Melakukan login. Mengembalikan objek User sesuai peran
     * (Admin / Kasir / Anggota) jika berhasil, atau null jika gagal.
     */
    public User login(String username, String password) {
        User user = userRepository.cariByUsername(username);
        if (user != null && user.cekPassword(password)) {
            return user;
        }
        return null;
    }
}
