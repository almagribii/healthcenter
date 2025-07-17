package org.demo.repository;

import org.demo.model.Pasien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PasienRepository extends JpaRepository<Pasien, Long> {
    Optional<Pasien> findByNim(String nim);
    Optional<Pasien> findByNamaAndTanggalLahir(String nama, String tanggalLahir);
}