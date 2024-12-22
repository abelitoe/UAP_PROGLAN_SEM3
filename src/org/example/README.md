# Sistem Pemesanan Hotel

## Gambaran Umum
*Sistem Pemesanan Hotel* adalah aplikasi berbasis Java yang dirancang untuk mengelola pemesanan hotel, termasuk informasi tamu, detail kamar, dan reservasi. Aplikasi ini memiliki antarmuka grafis (GUI) yang dibangun dengan pustaka Swing untuk interaksi pengguna.

## Fitur
1. *Sistem Login*
    - Menyediakan layar login untuk akses yang diotorisasi.
    - Kredensial default:
        - Username: admin
        - Password: password

2. *Menu Utama*
    - Akses berbagai submenu, termasuk pengelolaan tamu, kamar, dan reservasi.
    - Melakukan operasi seperti menambahkan, mengedit, dan menghapus data.

3. *Manajemen Tamu*
    - Menambahkan, mengedit, dan menghapus data tamu.
    - Validasi input tamu (misalnya nomor kartu kredit, nomor telepon, jenis kelamin).

4. *Manajemen Kamar*
    - Mengelola detail kamar, termasuk tipe kamar, harga, dan gambar.
    - Validasi informasi kamar (misalnya kode kamar, nomor lantai).

5. *Manajemen Reservasi*
    - Mengelola pemesanan kamar dengan validasi untuk entri duplikat dan format input yang benar.
    - Termasuk bidang untuk kode pemesanan, nama tamu, dan detail check-in/out.

6. *Pengelolaan File*
    - Menyimpan dan memuat data ke/dari hotel_data.txt.
    - Memuat data yang ada secara otomatis saat aplikasi dimulai.

7. *Fitur Tambahan*
    - Menghitung total harga semua kamar.


## Cara Penggunaan
### Login
1. Jalankan aplikasi.
2. Masukkan Username dan Password
3. Klik tombol *Login*.

### Mengelola Data
#### Manajemen Tamu
1. Pilih tombol "Menu Tamu" di menu utama.
2. Gunakan antarmuka untuk menambahkan, mengedit, atau menghapus data tamu.
3. Validasi input, seperti:
    - Nama: Hanya huruf.
    - Kartu Kredit: Tepat 12 digit.
    - Nomor Telepon: 10-12 digit.

#### Manajemen Kamar
1. Pilih tombol "Menu Kamar" di menu utama.
2. Tambahkan atau edit informasi kamar, seperti:
    - Kode Kamar: 3 digit angka (101-410).
    - Lantai: 1-4.
    - Nomor: 2 digit (01-10).
3. Unggah gambar untuk kamar (opsional).

#### Manajemen Reservasi
1. Pilih tombol "Menu Pemesanan" di menu utama.
2. Tambahkan, edit, atau hapus reservasi dengan bidang-bidang berikut:
    - Kode Pemesanan: Format XXXX XXXX XXXX XXXX.
    - Nama Tamu dan Nomor Kamar.
    - Detail Check-in dan Check-out.

#### Menghitung Total Harga Kamar
1. Klik tombol "Hitung Total Kamar" di menu utama.
2. Lihat total harga semua kamar.

## Struktur File
- hotel_data.txt: Menyimpan data kamar, tamu, dan reservasi.
- HotelBookingSystem.java: File program utama yang berisi logika aplikasi.

## Aturan Validasi
- *Tamu*:
    - Nama: Hanya huruf.
    - Kartu Kredit: 12 digit angka.
    - Jenis Kelamin: "Laki-Laki" atau "Perempuan".
- *Kamar*:
    - Kode Kamar: 3 digit (101-410).
    - Lantai: 1-4.
    - Nomor: 2 digit (01-10).
- *Reservasi*:
    - Kode Pemesanan: Format XXXX XXXX XXXX XXXX.
    - Check-in/Check-out: Validasi tanggal dan waktu.

## Kontributor
- Dikembangkan oleh: 
  - Muhammad Abel Putra Mayuri (20231037311157)
  - Ahmad Habibi (202310370311161)
  - Reyhan Alfin Poko (202310370311167)
  - Andika Candra Kurniawan (202310370311173)
  - Heru Ismawan O (202310370311177)

