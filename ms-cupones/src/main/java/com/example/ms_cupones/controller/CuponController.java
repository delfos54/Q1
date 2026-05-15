package com.example.ms_cupones.controller;

import com.example.ms_cupones.dto.CuponRequestDTO;
import com.example.ms_cupones.model.Cupon;
import com.example.ms_cupones.service.CuponService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cupones")
public class CuponController {

    private final CuponService service;

    public CuponController(CuponService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cupon> listar() {
        return service.listar();
    }

    @PostMapping
    public Cupon crear(@RequestBody CuponRequestDTO dto) {
        return service.crear(dto);
    }

    @GetMapping("/validar/{codigo}")
    public Cupon validar(@PathVariable String codigo) {
        return service.validar(codigo);
    }

    @PutMapping("/usar/{codigo}")
    public void usar(@PathVariable String codigo) {
        service.usarCupon(codigo);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}