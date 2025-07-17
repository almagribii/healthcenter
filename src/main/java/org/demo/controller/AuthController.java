// src/main/java/org/demo/controller/AuthController.java
package org.demo.controller;

import org.demo.model.LoginRequest;
import org.demo.model.Pasien;
import org.demo.service.PasienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth") // Endpoint untuk autentikasi
public class AuthController {

    @Autowired
    private PasienService pasienService;

    // Endpoint untuk pendaftaran (menggunakan endpoint PasienController yang sudah ada)
    // Sebenarnya, registerPasien sudah ada di PasienController.
    // Jika Anda ingin mengkonsolidasi di AuthController, Anda bisa memindahkannya di sini.
    // Namun, untuk sementara, kita biarkan registerPasien tetap di PasienController.

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Panggil metode loginPasien dari PasienService
        Pasien loggedInPasien = pasienService.loginPasien(
                loginRequest.getNim(),
                loginRequest.getTanggalLahir() // Tanggal Lahir diterima sebagai String
        );

        if (loggedInPasien != null) {
            // Jika login berhasil, kembalikan detail pasien atau pesan sukses
            // CATATAN: Untuk keamanan, jangan kembalikan semua data sensitif
            // Anda mungkin ingin mengembalikan DTO yang lebih sederhana
            return ResponseEntity.ok(Map.of(
                    "message", "Login berhasil!",
                    "pasienId", loggedInPasien.getId(),
                    "nama", loggedInPasien.getNama(),
                    "nim", loggedInPasien.getNim()
            ));
        } else {
            // Jika login gagal
            return new ResponseEntity<>(Map.of("message", "NIM atau Tanggal Lahir salah."), HttpStatus.UNAUTHORIZED); // 401 Unauthorized
        }
    }
}