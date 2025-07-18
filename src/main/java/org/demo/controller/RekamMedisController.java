package org.demo.controller;

import org.demo.model.RekamMedis;
import org.demo.service.RekamMedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rekam-medis")
public class RekamMedisController {

    @Autowired
    private RekamMedisService rekamMedisService;

    @PostMapping("/{pasienId}")
    public ResponseEntity<?> createRekamMedis(@PathVariable Long pasienId, @RequestBody RekamMedis rekamMedis) {
        try {
            RekamMedis newRekamMedis = rekamMedisService.createRekamMedis(pasienId, rekamMedis);
            return new ResponseEntity<>(newRekamMedis, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST); // 400 Bad Request
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RekamMedis> getRekamMedisById(@PathVariable Long id) {
        return rekamMedisService.getRekamMedisById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/pasien/{pasienId}")
    public List<RekamMedis> getRekamMedisByPasienId(@PathVariable Long pasienId) {
        return rekamMedisService.getRekamMedisByPasienId(pasienId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRekamMedis(@PathVariable Long id, @RequestBody RekamMedis rekamMedis) {
        try {
            RekamMedis updated = rekamMedisService.updateRekamMedis(id, rekamMedis);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }
}