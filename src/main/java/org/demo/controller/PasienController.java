// src/main/java/org/demo/controller/PasienController.java
package org.demo.controller;

import org.demo.model.Pasien;
import org.demo.service.PasienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Hapus import java.time.LocalDate jika tidak lagi digunakan
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pasien")
public class PasienController {

    @Autowired
    private PasienService pasienService;

    @PostMapping
    public ResponseEntity<?> registerPasien(@RequestBody Pasien pasien) {
        try {
            Pasien newPasien = pasienService.registerNewPasien(pasien);
            return new ResponseEntity<>(newPasien, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.CONFLICT); // 409 Conflict
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPasien(
            @RequestParam(required = false) String nim,
            @RequestParam(required = false) String nama,
            @RequestParam(required = false) String tanggalLahir) { // Ubah parameter ini menjadi String

        if (nim != null && !nim.isEmpty()) {
            Optional<Pasien> pasienOptional = pasienService.findPasienByNim(nim);
            if (pasienOptional.isPresent()) {
                return ResponseEntity.ok(pasienOptional.get());
            } else {
                return new ResponseEntity<>(Map.of("message", "Pasien dengan NIM " + nim + " tidak ditemukan."), HttpStatus.NOT_FOUND);
            }
        } else if (nama != null && !nama.isEmpty() && tanggalLahir != null) {
            List<Pasien> pasienList = pasienService.findPasienByNamaAndTanggalLahir(nama, tanggalLahir); // Panggil dengan String
            if (!pasienList.isEmpty()) {
                return ResponseEntity.ok(pasienList); // Mengembalikan List
            } else {
                return new ResponseEntity<>(Map.of("message", "Pasien dengan nama dan tanggal lahir tersebut tidak ditemukan."), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(Map.of("error", "Harap berikan NIM atau Nama dan Tanggal Lahir untuk mencari pasien."), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pasien> getPasienById(@PathVariable Long id) {
        return pasienService.findPasienById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<Pasien> getAllPasien() {
        return pasienService.getAllPasien();
    }
}