package org.demo.controller;

import org.demo.model.Pasien;
import org.demo.service.PasienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pasien")
public class PasienController {

    @Autowired
    private PasienService pasienService;

    // POST: /api/pasien (Untuk Pendaftaran Pasien Baru)
    @PostMapping
    public ResponseEntity<?> registerPasien(@RequestBody Pasien pasien) {
        try {
            Pasien newPasien = pasienService.registerNewPasien(pasien);
            return new ResponseEntity<>(newPasien, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.CONFLICT); // 409 Conflict
        }
    }

    // GET: /api/pasien/search?nim=... OR /api/pasien/search?nama=...&tanggalLahir=... (Cari Data Pasien Lama)
    @GetMapping("/search")
    public ResponseEntity<?> searchPasien(
            @RequestParam(required = false) String nim,
            @RequestParam(required = false) String nama,
            @RequestParam(required = false) LocalDate tanggalLahir) {

        if (nim != null && !nim.isEmpty()) {
            Optional<Pasien> pasienOptional = pasienService.findPasienByNim(nim);
            if (pasienOptional.isPresent()) {
                return ResponseEntity.ok(pasienOptional.get()); // Return Pasien object directly
            } else {
                return new ResponseEntity<>(Map.of("message", "Pasien dengan NIM " + nim + " tidak ditemukan."), HttpStatus.NOT_FOUND);
            }
        } else if (nama != null && !nama.isEmpty() && tanggalLahir != null) {
            Optional<Pasien> pasienOptional = pasienService.findPasienByNamaAndTanggalLahir(nama, tanggalLahir);
            if (pasienOptional.isPresent()) {
                return ResponseEntity.ok(pasienOptional.get()); // Return Pasien object directly
            } else {
                return new ResponseEntity<>(Map.of("message", "Pasien dengan nama dan tanggal lahir tersebut tidak ditemukan."), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(Map.of("error", "Harap berikan NIM atau Nama dan Tanggal Lahir untuk mencari pasien."), HttpStatus.BAD_REQUEST);
        }
    }

    // GET: /api/pasien/{id} (Untuk mendapatkan detail pasien setelah pendaftaran/pencarian selesai)
    @GetMapping("/{id}")
    public ResponseEntity<Pasien> getPasienById(@PathVariable Long id) {
        return pasienService.findPasienById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET: /api/pasien (Dapatkan semua pasien)
    @GetMapping
    public List<Pasien> getAllPasien() {
        return pasienService.getAllPasien();
    }
}