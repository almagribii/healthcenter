package org.demo.service;

import org.demo.model.Pasien;
import org.demo.model.RekamMedis;
import org.demo.repository.PasienRepository;
import org.demo.repository.RekamMedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RekamMedisService {

    @Autowired
    private RekamMedisRepository rekamMedisRepository;

    @Autowired
    private PasienRepository pasienRepository; // Untuk memastikan Pasien ada

    @Transactional
    public RekamMedis createRekamMedis(Long pasienId, RekamMedis rekamMedis) {
        Optional<Pasien> pasienOptional = pasienRepository.findById(pasienId);
        if (pasienOptional.isEmpty()) {
            throw new RuntimeException("Pasien dengan ID " + pasienId + " tidak ditemukan.");
        }
        rekamMedis.setPasien(pasienOptional.get());
        return rekamMedisRepository.save(rekamMedis);
    }

    public Optional<RekamMedis> getRekamMedisById(Long id) {
        return rekamMedisRepository.findById(id);
    }

    public List<RekamMedis> getRekamMedisByPasienId(Long pasienId) {
        return rekamMedisRepository.findByPasienId(pasienId);
    }

    @Transactional
    public RekamMedis updateRekamMedis(Long id, RekamMedis updatedRekamMedis) {
        return rekamMedisRepository.findById(id)
                .map(existingRekamMedis -> {
                    existingRekamMedis.setKeluhan(updatedRekamMedis.getKeluhan());
                    existingRekamMedis.setDiagnosa(updatedRekamMedis.getDiagnosa());
                    existingRekamMedis.setTindakan(updatedRekamMedis.getTindakan());
                    existingRekamMedis.setResep(updatedRekamMedis.getResep());
                    // Tanggal konsultasi bisa diupdate atau tidak, tergantung kebutuhan
                    if (updatedRekamMedis.getTanggalKonsultasi() != null) {
                        existingRekamMedis.setTanggalKonsultasi(updatedRekamMedis.getTanggalKonsultasi());
                    }
                    return rekamMedisRepository.save(existingRekamMedis);
                }).orElseThrow(() -> new RuntimeException("Rekam Medis dengan ID " + id + " tidak ditemukan."));
    }
}