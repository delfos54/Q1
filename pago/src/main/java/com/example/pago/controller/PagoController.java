package com.example.pago.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.pago.dto.PagoRequest;
import com.example.pago.model.pago;
import com.example.pago.security.JwtUtil;
import com.example.pago.service.PagoService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;
    private final RestTemplate restTemplate; // Usamos la herramienta nativa
    private final JwtUtil JwtUtil;
    @PostMapping("/realizar_pago")
    public ResponseEntity<pago> pagar(
        @RequestBody PagoRequest req, 
        @RequestHeader("Authorization") String token
    ) {
        
        // 1. Obtener el usuario
        String usuarioReal = JwtUtil.obtenerUsuario(token.replace("Bearer ", ""));    
        
        // 2. Obtener el precio usando RestTemplate
        // Armamos la URL exacta con el puerto 8083 y el ID del producto que viene en la petición
        String urlProducto = "http://localhost:8083/api/productos/" + req.getProductoId() + "/precio";
        
        // Hacemos la llamada HTTP GET y le decimos que esperamos un BigDecimal de vuelta
        BigDecimal precioReal = restTemplate.getForObject(urlProducto, BigDecimal.class);

        // 3. Procesar el pago
        return ResponseEntity.ok(pagoService.procesopagar(req, usuarioReal, precioReal));
    }
}


