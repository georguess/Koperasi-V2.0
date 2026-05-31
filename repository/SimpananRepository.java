package com.koperasi.repository;

import com.koperasi.model.Anggota;
import com.koperasi.model.Simpanan;

import java.util.ArrayList;
import java.util.List;

public class SimpananRepository extends InMemoryRepository<Simpanan> {

    public List<Simpanan> cariByAnggota(Anggota anggota) {
        List<Simpanan> hasil = new ArrayList<>();
        for (Simpanan s : data) {
            if (s.getAnggota().getId().equals(anggota.getId())) {
                hasil.add(s);
            }
        }
        return hasil;
    }
}
