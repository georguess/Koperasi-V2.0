# Koperasi Simpan Pinjam

Aplikasi desktop **Pemrograman Berbasis Objek (PBO)** untuk koperasi simpan
pinjam, dibuat dengan **Java Swing**. Login mengenali akun lalu membuka
dashboard yang **hanya berisi fitur sesuai peran** (Admin / Kasir / Anggota),
persis seperti struktur pada gambar.

```
LOGIN
 ├── Admin   → Dashboard → Kelola Anggota, Approve Pinjaman, Laporan
 ├── Kasir   → Dashboard → Setor Simpanan, Terima Angsuran, Input Pengajuan Pinjaman
 └── Anggota → Dashboard → Lihat Saldo & Status
```

## Cara Menjalankan

Butuh **JDK 17+** (dikembangkan dengan JDK 21).

```bash
# 1. Masuk ke folder proyek
cd KoperasiSimpanPinjam

# 2. Kompilasi seluruh kode ke folder out/
javac -d out $(find src -name "*.java")

# 3. Jalankan aplikasi
java -cp out com.koperasi.Main
```

> Aplikasi ini berbasis GUI sehingga harus dijalankan di komputer yang
> memiliki tampilan (bukan server tanpa layar).

## Akun Demo

Data contoh sudah otomatis terisi saat aplikasi dijalankan.

| Peran   | Username | Password  |
|---------|----------|-----------|
| Admin   | admin    | admin123  |
| Kasir   | kasir    | kasir123  |
| Anggota | andi     | andi123   |
| Anggota | dewi     | dewi123   |

## Struktur Proyek

```
src/com/koperasi/
├── Main.java                  # Titik masuk aplikasi
├── app/
│   └── AppContext.java        # Perakitan semua repository & service (wiring)
├── model/                     # Kelas data + enum (inti OOP)
│   ├── User.java              # abstract: induk semua pengguna
│   ├── Admin.java             # extends User
│   ├── Kasir.java             # extends User
│   ├── Anggota.java           # extends User (+ data keanggotaan)
│   ├── Role.java              # enum peran
│   ├── Simpanan.java
│   ├── JenisSimpanan.java     # enum POKOK/WAJIB/SUKARELA
│   ├── Pinjaman.java          # punya List<Angsuran> (komposisi)
│   ├── Angsuran.java
│   └── StatusPinjaman.java    # enum status pinjaman
├── repository/                # Penyimpanan data (in-memory)
│   ├── InMemoryRepository.java# abstract generic <T>
│   ├── UserRepository.java
│   ├── SimpananRepository.java
│   └── PinjamanRepository.java
├── service/                   # Logika bisnis
│   ├── AuthService.java       # login & pengenalan peran
│   ├── AnggotaService.java
│   ├── SimpananService.java
│   ├── PinjamanService.java
│   └── LaporanService.java
├── util/
│   ├── IDGenerator.java       # pembuat ID unik (USR-0001, dst.)
│   ├── CurrencyFormatter.java # format Rupiah
│   └── DataSeeder.java        # data awal
└── ui/                        # Tampilan (Swing)
    ├── LoginFrame.java        # layar login + routing per peran
    ├── BaseDashboard.java     # abstract: kerangka dashboard bersama
    ├── components/            # komponen UI dipakai ulang
    │   ├── UIConstants.java   # warna & font
    │   ├── RoundedPanel.java
    │   ├── RoundButton.java
    │   └── UIHelper.java
    ├── admin/                 # 3 fitur Admin
    ├── kasir/                 # 3 fitur Kasir
    └── anggota/               # 1 fitur Anggota
```

## Konsep OOP yang Diterapkan

- **Pewarisan (Inheritance):** `Admin`, `Kasir`, `Anggota` mewarisi `User`;
  setiap dashboard peran mewarisi `BaseDashboard`.
- **Polimorfisme (Polymorphism):** method abstrak `getDashboardTitle()` yang
  di-override tiap peran, dan pemilihan dashboard saat login lewat `instanceof`.
- **Enkapsulasi (Encapsulation):** seluruh atribut bersifat `private` dan
  diakses melalui getter/setter.
- **Konstruktor (Constructor):** setiap kelas memiliki konstruktor; subclass
  memanggil `super(...)` untuk mewariskan data ke induk.
- **Abstraksi (Abstraction):** kelas abstrak `User`, `InMemoryRepository`,
  dan `BaseDashboard`.
- **Generics:** `InMemoryRepository<T>` sebagai dasar semua repository.
- **Komposisi (Composition):** `Pinjaman` memiliki `List<Angsuran>`.
- **Enum:** `Role`, `JenisSimpanan`, `StatusPinjaman`.
- **Arsitektur berlapis (clean code):** pemisahan `model` → `repository` →
  `service` → `ui`, dengan `AppContext` sebagai perakit dependensi.

## Tangkapan Layar

Lihat folder `docs/screenshots/`:
`login.png`, `admin.png`, `kasir.png`, `anggota.png`.
