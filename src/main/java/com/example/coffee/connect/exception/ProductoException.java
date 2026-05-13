package com.example.coffee.connect.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.coffee.connect.DTO.ApiResponse;

import jakarta.persistence.EntityNotFoundException;
@RestControllerAdvice
public class ProductoException {


    // 🔴 VALIDACIONES (Maneja los errores de @NotBlank, @Min, etc. en el DTO)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));

        return ResponseEntity.badRequest().body(
                ApiResponse.<Object>builder()
                        .respuesta(false)
                        .mensaje("Validación fallida")
                        .data(errores) // Usamos 'data' para enviar el mapa de errores
                        .build()
        );
    }

    // 🔎 404 - Maneja errores de búsqueda por ID
    @ExceptionHandler({RuntimeException.class, EntityNotFoundException.class})
    public ResponseEntity<ApiResponse<Object>> handleNotFound(Exception ex) {
        return ResponseEntity.status(404).body(
                ApiResponse.<Object>builder()
                        .respuesta(false)
                        .mensaje(ex.getMessage())
                        .build()
        );
    }

    // 💥 500 - Maneja cualquier otro error inesperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneral(Exception ex) {
        return ResponseEntity.status(500).body(
                ApiResponse.<Object>builder()
                        .respuesta(false)
                        .mensaje("Error interno del servidor: " + ex.getMessage())
                        .build()
        );
    }
}

