package com.sanedge.perpustakaan_buku.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String authenticationToken;
    private String message;
    private Integer statusCode;
    private String expiresAt;
    private String username;
}
