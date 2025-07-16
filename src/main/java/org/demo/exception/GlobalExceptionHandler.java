package org.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        // Ini akan menangkap RuntimeException yang dilemparkan dari service layer
        // atau controller yang tidak tertangkap di try-catch lokal.
        // Anda bisa menyesuaikan HttpStatus berdasarkan jenis RuntimeException.
        if (ex.getMessage().contains("tidak ditemukan")) { // Contoh kasar, lebih baik buat exception kustom
            return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.NOT_FOUND); // 404
        } else if (ex.getMessage().contains("sudah terdaftar")) {
            return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.CONFLICT); // 409
        }
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.BAD_REQUEST); // Default 400
    }

    // Anda bisa menambahkan @ExceptionHandler lain untuk jenis exception spesifik
    // @ExceptionHandler(PasienNotFoundException.class)
    // public ResponseEntity<Map<String, String>> handlePasienNotFound(PasienNotFoundException ex) {
    //     return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.NOT_FOUND);
    // }
}