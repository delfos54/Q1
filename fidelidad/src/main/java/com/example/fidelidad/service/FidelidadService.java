package com.example.fidelidad.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.example.fidelidad.model.Fidelidad;
import com.example.fidelidad.repository.FidelidadRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FidelidadService {


    private final FidelidadRepository  fidelidadRepo;
    //sistema de puntos por compra del usuario
    public void  agregarPuntos(String usuario, BigDecimal montoCompra){
        int puntosNuevos = montoCompra.multiply(new BigDecimal(1000)).intValue(); 
    
        Fidelidad fidelidad = fidelidadRepo.findByUsuario(usuario)
            .orElse(new Fidelidad());
            if (fidelidad.getUsuario()==null){
                fidelidad.setUsuario(usuario);
                fidelidad.setPuntosTotales(0);
            } 
          fidelidad.setPuntosTotales(fidelidad.getPuntosTotales() + puntosNuevos);
          fidelidadRepo.save(fidelidad);  
    }
}
