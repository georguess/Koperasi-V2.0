package com.koperasi.service;

import com.koperasi.model.Anggota;
import com.koperasi.repository.UserRepository;
import com.koperasi.util.IDGenerator;

import java.util.List;

/** Logika bisnis pengelolaan anggota (fitur Admin: Kelola Anggota). */
public class AnggotaService {

    private final UserRepository userRepository;

    public AnggotaService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Anggota tambahAnggota(String namaLengkap, String username, String password,
                                 String alamat, String noTelepon) {
        String id = IDGenerator.generate("USR");
        String nomorAnggota = IDGenerator.generate("AGT");
        Anggota anggota = new Anggota(id, username, password, namaLengkap,
                nomorAnggota, alamat, noTelepon);
        userRepository.simpan(anggota);
        return anggota;
    }

    public List<Anggota> getSemuaAnggota() {
        return userRepository.ambilSemuaAnggota();
    }

    public void hapusAnggota(Anggota anggota) {
        userRepository.hapus(anggota);
    }

    public boolean usernameDipakai(String username) {
        return userRepository.cariByUsername(username) != null;
    }
}
