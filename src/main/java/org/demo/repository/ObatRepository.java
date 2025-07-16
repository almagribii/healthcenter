package org.demo.repository;


import org.demo.model.Obat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObatRepository extends JpaRepository<Obat, Long> {
    // Bisa tambahkan method khusus jika diperlukan, misal findByNamaObat
}