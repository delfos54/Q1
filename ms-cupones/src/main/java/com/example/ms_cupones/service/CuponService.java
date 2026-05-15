package com.example.ms_cupones.service;

import com.example.ms_cupones.dto.CuponRequestDTO;
import com.example.ms_cupones.exception.ResourceNotFoundException;
import com.example.ms_cupones.model.Cupon;
import com.example.ms_cupones.repository.CuponRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuponService {

    private final CuponRepository cuponRepository;

    public CuponService(CuponRepository cuponRepository) {
        this.cuponRepository = cuponRepository;
    }

    public List<Cupon> listar() {
        return cuponRepository.findAll();
    }

    public Cupon crear(CuponRequestDTO dto) {

        Cupon c = new Cupon();
        c.setCodigo(dto.getCodigo());
        c.setDescuento(dto.getDescuento());
        c.setUsosDisponibles(dto.getUsosDisponibles());
        c.setActivo(true);

        return cuponRepository.save(c);
    }

    public Cupon validar(String codigo) {

        Cupon cupon = cuponRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Cupón no existe"));

        if (!cupon.isActivo() || cupon.getUsosDisponibles() <= 0) {
            throw new RuntimeException("Cupón inválido");
        }

        return cupon;
    }

    public void usarCupon(String codigo) {

        Cupon cupon = validar(codigo);

        cupon.setUsosDisponibles(cupon.getUsosDisponibles() - 1);

        if (cupon.getUsosDisponibles() <= 0) {
            cupon.setActivo(false);
        }

        cuponRepository.save(cupon);
    }

    public void eliminar(Integer id) {

        Cupon c = cuponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cupón no encontrado"));

        cuponRepository.delete(c);
    }
}
