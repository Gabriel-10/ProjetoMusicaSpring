package com.example.musica2.dto;

import lombok.Getter;

@Getter
public class LoginResponseDTO {
    private String token;

    public LoginResponseDTO(String token) {
        this.token = token;
    }

}