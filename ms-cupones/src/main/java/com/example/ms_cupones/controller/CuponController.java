package com.example.ms_cupones.controller;

import com.example.ms_cupones.dto.ApiResponse;
import com.example.ms_cupones.dto.AplicarCuponRequestDTO;
import com.example.ms_cupones.dto.AplicarCuponResponseDTO;
import com.example.ms_cupones.dto.CuponRequestDTO;
import com.example.ms_cupones.model.Cupon;
import com.example.ms_cupones.service.CuponService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cupones")
@RequiredArgsConstructor
public class CuponController {

    private final CuponService cuponService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Cupon>>> listarCupones() {

        List<Cupon> cupones = cuponService.listarCupones();

        return ResponseEntity.ok(
                ApiResponse.<List<Cupon>>builder()
                        .success(true)
                        .message("Cupones listados correctamente")
                        .data(cupones)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Cupon>> obtenerCupon(@PathVariable Integer id) {

        Cupon cupon = cuponService.obtenerCupon(id);

        return ResponseEntity.ok(
                ApiResponse.<Cupon>builder()
                        .success(true)
                        .message("Cupón encontrado")
                        .data(cupon)
                        .build()
        );
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<ApiResponse<Cupon>> obtenerPorCodigo(@PathVariable String codigo) {

        Cupon cupon = cuponService.obtenerPorCodigo(codigo);

        return ResponseEntity.ok(
                ApiResponse.<Cupon>builder()
                        .success(true)
                        .message("Cupón encontrado por código")
                        .data(cupon)
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Cupon>> crearCupon(
            @Valid @RequestBody CuponRequestDTO dto
    ) {

        Cupon cupon = cuponService.crearCupon(dto);

        return ResponseEntity.ok(
                ApiResponse.<Cupon>builder()
                        .success(true)
                        .message("Cupón creado correctamente")
                        .data(cupon)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Cupon>> actualizarCupon(
            @PathVariable Integer id,
            @Valid @RequestBody CuponRequestDTO dto
    ) {

        Cupon cupon = cuponService.actualizarCupon(id, dto);

        return ResponseEntity.ok(
                ApiResponse.<Cupon>builder()
                        .success(true)
                        .message("Cupón actualizado correctamente")
                        .data(cupon)
                        .build()
        );
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<Cupon>> cambiarEstado(
            @PathVariable Integer id,
            @RequestParam Boolean activo
    ) {

        Cupon cupon = cuponService.cambiarEstado(id, activo);

        return ResponseEntity.ok(
                ApiResponse.<Cupon>builder()
                        .success(true)
                        .message("Estado del cupón actualizado correctamente")
                        .data(cupon)
                        .build()
        );
    }

    @PostMapping("/validar")
    public ResponseEntity<ApiResponse<AplicarCuponResponseDTO>> validarCupon(
            @Valid @RequestBody AplicarCuponRequestDTO dto
    ) {

        AplicarCuponResponseDTO response = cuponService.validarCupon(dto);

        return ResponseEntity.ok(
                ApiResponse.<AplicarCuponResponseDTO>builder()
                        .success(true)
                        .message("Cupón validado correctamente")
                        .data(response)
                        .build()
        );
    }

    @PostMapping("/aplicar")
    public ResponseEntity<ApiResponse<AplicarCuponResponseDTO>> aplicarCupon(
            @Valid @RequestBody AplicarCuponRequestDTO dto
    ) {

        AplicarCuponResponseDTO response = cuponService.aplicarCupon(dto);

        return ResponseEntity.ok(
                ApiResponse.<AplicarCuponResponseDTO>builder()
                        .success(true)
                        .message("Cupón aplicado correctamente")
                        .data(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> eliminarCupon(@PathVariable Integer id) {

        cuponService.eliminarCupon(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Cupón eliminado correctamente")
                        .build()
        );
    }
}