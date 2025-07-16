package org.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RekamMedis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Hubungan banyak rekam medis ke satu pasien
    @JoinColumn(name = "pasien_id", nullable = false)
    private Pasien pasien;

    private String keluhan;
    private String diagnosa;
    private String tindakan;
    private String resep; // Resep akan disimpan sebagai teks sederhana untuk kesederhanaan

    private LocalDateTime tanggalKonsultasi;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (tanggalKonsultasi == null) {
            tanggalKonsultasi = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}