// src/main/java/org/demo/service/PasienService.java
package org.demo.service;

import org.demo.model.Pasien;
import org.demo.repository.PasienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Hapus import java.time.LocalDate jika tidak lagi digunakan
import java.util.List;
import java.util.Optional;

@Service
public class PasienService {

    @Autowired
    private PasienRepository pasienRepository;

    @Transactional
    public Pasien registerNewPasien(Pasien pasien) {
        // Cek duplikasi jika NIM ada dan mahasiswa unida
        if (Boolean.TRUE.equals(pasien.getIsMahasiswaUnida()) && pasien.getNim() != null && !pasien.getNim().isEmpty()) {
            if (pasienRepository.findByNim(pasien.getNim()).isPresent()) {
                throw new RuntimeException("Pasien dengan NIM " + pasien.getNim() + " sudah terdaftar.");
            }
        }
        // Cek duplikasi untuk pasien umum berdasarkan nama dan tanggal lahir (String)
        List<Pasien> existingPasien = pasienRepository.findByNamaAndTanggalLahir(pasien.getNama(), pasien.getTanggalLahir());
        if (!existingPasien.isEmpty()) {
            throw new RuntimeException("Pasien dengan nama dan tanggal lahir tersebut sudah terdaftar.");
        }

        return pasienRepository.save(pasien);
    }

    public Optional<Pasien> findPasienById(Long id) {
        return pasienRepository.findById(id);
    }

    public Optional<Pasien> findPasienByNim(String nim) {
        return pasienRepository.findByNim(nim);
    }

    // Ubah parameter tanggalLahir menjadi String
    public List<Pasien> findPasienByNamaAndTanggalLahir(String nama, String tanggalLahir) {
        return pasienRepository.findByNamaAndTanggalLahir(nama, tanggalLahir);
    }

    public List<Pasien> getAllPasien() {
        return pasienRepository.findAll();
    }

    /**
     * Metode untuk melakukan login pasien berdasarkan NIM (username) dan Tanggal Lahir (password).
     * @param nim NIM pasien (username)
     * @param tanggalLahirString Tanggal Lahir pasien dalam format String (password)
     * @return Objek Pasien jika kredensial benar, null jika salah.
     */
    public Pasien loginPasien(String nim, String tanggalLahirString) {
        Optional<Pasien> pasienOptional = pasienRepository.findByNim(nim);

        if (pasienOptional.isPresent()) {
            Pasien pasien = pasienOptional.get();
            // Perbandingan langsung sebagai String
            String storedTanggalLahirString = pasien.getTanggalLahir(); // Ini sudah String

            if (storedTanggalLahirString.equals(tanggalLahirString)) {
                return pasien; // Login berhasil
            }
        }
        return null; // Login gagal (NIM tidak ditemukan atau Tanggal Lahir salah)
    }
}