// src/main/java/org/demo/model/LoginRequest.java (atau org.demo.dto/LoginRequest.java)
package org.demo.model; // Sesuaikan dengan lokasi Anda

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String nim;
    private String tanggalLahir; // Akan diterima sebagai String
}