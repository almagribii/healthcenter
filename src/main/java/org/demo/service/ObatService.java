package org.demo.service;

import org.demo.model.Obat;
import org.demo.repository.ObatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ObatService {

    @Autowired
    private ObatRepository obatRepository;

    @Transactional
    public Obat addObat(Obat obat) {
        return obatRepository.save(obat);
    }

    public List<Obat> getAllObat() {
        return obatRepository.findAll();
    }

    public Optional<Obat> getObatById(Long id) {
        return obatRepository.findById(id);
    }

    @Transactional
    public Obat updateStokObat(Long id, Integer quantityChange) {
        return obatRepository.findById(id)
                .map(obat -> {
                    int newStok = obat.getStok() + quantityChange;
                    if (newStok < 0) {
                        throw new RuntimeException("Stok tidak mencukupi untuk pengurangan ini.");
                    }
                    obat.setStok(newStok);
                    return obatRepository.save(obat);
                }).orElseThrow(() -> new RuntimeException("Obat dengan ID " + id + " tidak ditemukan."));
    }

    // Metode untuk mengurangi stok saat obat diambil (Pemberian Obat)
    @Transactional
    public Obat dispenseObat(Long obatId, Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Jumlah obat yang akan diambil harus positif.");
        }
        return updateStokObat(obatId, -quantity); // Kurangi stok
    }
    @Transactional
    public List<Obat> addMultipleObat(List<Obat> obatList) {
        // Loop melalui setiap obat dalam daftar dan simpan
        return obatRepository.saveAll(obatList); // saveAll() adalah metode Spring Data JPA
    }
}