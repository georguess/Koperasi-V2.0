package com.koperasi.repository;

import com.koperasi.model.Anggota;
import com.koperasi.model.Role;
import com.koperasi.model.User;

import java.util.ArrayList;
import java.util.List;

/** Menyimpan seluruh pengguna (Admin, Kasir, Anggota) untuk kebutuhan login. */
public class UserRepository extends InMemoryRepository<User> {

    public User cariByUsername(String username) {
        for (User u : data) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    public User cariById(String id) {
        for (User u : data) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    /** Mengambil hanya pengguna berperan Anggota. */
    public List<Anggota> ambilSemuaAnggota() {
        List<Anggota> hasil = new ArrayList<>();
        for (User u : data) {
            if (u.getRole() == Role.ANGGOTA && u instanceof Anggota) {
                hasil.add((Anggota) u);
            }
        }
        return hasil;
    }
}
