package com.example.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {


    @NotBlank(message = "El refreshToken es obligatorio")
    private String refreshToken;
}
