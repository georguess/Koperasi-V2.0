package com.koperasi.service;

import com.koperasi.model.Anggota;
import com.koperasi.model.JenisSimpanan;
import com.koperasi.model.Simpanan;
import com.koperasi.repository.SimpananRepository;
import com.koperasi.util.IDGenerator;

import java.util.List;

/** Logika bisnis simpanan (fitur Kasir: Setor Simpanan). */
public class SimpananService {

    private final SimpananRepository simpananRepository;

    public SimpananService(SimpananRepository simpananRepository) {
        this.simpananRepository = simpananRepository;
    }

    public Simpanan setor(Anggota anggota, JenisSimpanan jenis, double jumlah) {
        String id = IDGenerator.generate("SMP");
        Simpanan simpanan = new Simpanan(id, anggota, jenis, jumlah);
        return simpananRepository.simpan(simpanan);
    }

    public List<Simpanan> getByAnggota(Anggota anggota) {
        return simpananRepository.cariByAnggota(anggota);
    }

    public List<Simpanan> getSemua() {
        return simpananRepository.ambilSemua();
    }

    public double getTotalSimpanan(Anggota anggota) {
        double total = 0;
        for (Simpanan s : simpananRepository.cariByAnggota(anggota)) {
            total += s.getJumlah();
        }
        return total;
    }

    public double getTotalSeluruhSimpanan() {
        double total = 0;
        for (Simpanan s : simpananRepository.ambilSemua()) {
            total += s.getJumlah();
        }
        return total;
    }
}
