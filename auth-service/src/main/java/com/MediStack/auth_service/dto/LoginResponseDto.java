package com.MediStack.auth_service.dto;

public class LoginResponseDto {

    private final String token;

    public String getToken() {
        return token;
    }

    public LoginResponseDto(String token) {
        this.token = token;
    }
}
