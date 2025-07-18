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
        if (Boolean.TRUE.equals(pasien.getIsMahasiswaUnida()) && pasien.getNim() != null && !pasien.getNim().isEmpty()) {
            if (pasienRepository.findByNim(pasien.getNim()).isPresent()) {
                throw new RuntimeException("Pasien dengan NIM " + pasien.getNim() + " sudah terdaftar.");
            }
        }
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

    public List<Pasien> findPasienByNamaAndTanggalLahir(String nama, String tanggalLahir) {
        return pasienRepository.findByNamaAndTanggalLahir(nama, tanggalLahir);
    }

    public List<Pasien> getAllPasien() {
        return pasienRepository.findAll();
    }


    public Pasien loginPasien(String nim, String tanggalLahirString) {
        Optional<Pasien> pasienOptional = pasienRepository.findByNim(nim);

        if (pasienOptional.isPresent()) {
            Pasien pasien = pasienOptional.get();
            String storedTanggalLahirString = pasien.getTanggalLahir();

            if (storedTanggalLahirString.equals(tanggalLahirString)) {
                return pasien;
            }
        }
        return null;
    }
}