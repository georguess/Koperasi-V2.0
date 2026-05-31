package com.koperasi.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository generik berbasis memori.
 * Memakai GENERICS <T> agar dapat dipakai ulang oleh berbagai entitas (CLEAN CODE,
 * menghindari duplikasi kode penyimpanan).
 */
public abstract class InMemoryRepository<T> {

    protected final List<T> data = new ArrayList<>();

    public T simpan(T item) {
        data.add(item);
        return item;
    }

    public List<T> ambilSemua() {
        return new ArrayList<>(data);
    }

    public void hapus(T item) {
        data.remove(item);
    }

    public int jumlah() {
        return data.size();
    }
}
