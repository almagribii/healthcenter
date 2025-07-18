package org.demo.controller;

import org.demo.model.Obat;
import org.demo.service.ObatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/obat")
public class ObatController {

    @Autowired
    private ObatService obatService;

    @PostMapping
    public ResponseEntity<Obat> addObat(@RequestBody Obat obat) {
        Obat newObat = obatService.addObat(obat);
        return new ResponseEntity<>(newObat, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Obat> getAllObat() {
        return obatService.getAllObat();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Obat> getObatById(@PathVariable Long id) {
        return obatService.getObatById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}/add-stok")
    public ResponseEntity<?> addStokObat(@PathVariable Long id, @RequestParam Integer quantity) {
        try {
            Obat updatedObat = obatService.updateStokObat(id, quantity);
            return ResponseEntity.ok(updatedObat);
        } catch (RuntimeException e) {

            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/dispense")
    public ResponseEntity<?> dispenseObat(@PathVariable Long id, @RequestParam Integer quantity) {
        try {
            Obat dispensedObat = obatService.dispenseObat(id, quantity);
            return ResponseEntity.ok(dispensedObat);
        } catch (IllegalArgumentException e) { // Tangkap IllegalArgumentException secara spesifik terlebih dahulu
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) { // Kemudian tangkap RuntimeException yang lebih umum
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND); // Bisa jadi Obat tidak ditemukan
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Obat>> addMultipleObat(@RequestBody List<Obat> obatList) {
        List<Obat> newObatList = obatService.addMultipleObat(obatList);
        return new ResponseEntity<>(newObatList, HttpStatus.CREATED);
    }
}