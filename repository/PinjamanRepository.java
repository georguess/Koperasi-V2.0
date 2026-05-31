package com.koperasi.repository;

import com.koperasi.model.Anggota;
import com.koperasi.model.Pinjaman;
import com.koperasi.model.StatusPinjaman;

import java.util.ArrayList;
import java.util.List;

public class PinjamanRepository extends InMemoryRepository<Pinjaman> {

    public List<Pinjaman> cariByAnggota(Anggota anggota) {
        List<Pinjaman> hasil = new ArrayList<>();
        for (Pinjaman p : data) {
            if (p.getAnggota().getId().equals(anggota.getId())) {
                hasil.add(p);
            }
        }
        return hasil;
    }

    public List<Pinjaman> cariByStatus(StatusPinjaman status) {
        List<Pinjaman> hasil = new ArrayList<>();
        for (Pinjaman p : data) {
            if (p.getStatus() == status) {
                hasil.add(p);
            }
        }
        return hasil;
    }
}
