package com.example.fidelidad.controller;


import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.fidelidad.dto.FidelidadRequestDTO;
import com.example.fidelidad.repository.FidelidadRepository;
import com.example.fidelidad.service.FidelidadService;
import lombok.RequiredArgsConstructor;



@Controller
@RequestMapping("/fidelidad")
@RequiredArgsConstructor
public class FidelidadController {
private final FidelidadService fidelidadService;
    private final FidelidadRepository repository;

    // Endpoint para que el microservicio de PAGOS registre puntos
    @PostMapping("/sumar-puntos")
    public ResponseEntity<?> sumarPuntos(@RequestBody FidelidadRequestDTO dto) {
        fidelidadService.agregarPuntos(dto.getUsuario(), dto.getMonto());
        return ResponseEntity.ok(Map.of("mensaje", "Puntos actualizados con éxito"));
    }

    // Endpoint para que el USUARIO vea sus puntos
    @GetMapping("/mis-puntos/{usuario}")
    public ResponseEntity<?> obtenerPuntos(@PathVariable String usuario) {
        return repository.findByUsuario(usuario)
                .map(f -> ResponseEntity.ok(f))
                .orElse(ResponseEntity.notFound().build());
    }
}
