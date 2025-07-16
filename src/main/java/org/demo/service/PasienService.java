package org.demo.service;

import org.demo.model.Pasien;
import org.demo.repository.PasienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        // Cek duplikasi untuk pasien umum berdasarkan nama dan tanggal lahir
        if (pasienRepository.findByNamaAndTanggalLahir(pasien.getNama(), pasien.getTanggalLahir()).isPresent()) {
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

    public Optional<Pasien> findPasienByNamaAndTanggalLahir(String nama, LocalDate tanggalLahir) {
        return pasienRepository.findByNamaAndTanggalLahir(nama, tanggalLahir);
    }

    public List<Pasien> getAllPasien() {
        return pasienRepository.findAll();
    }
}