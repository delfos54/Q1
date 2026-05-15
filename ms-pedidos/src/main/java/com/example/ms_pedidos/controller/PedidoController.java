package com.example.ms_pedidos.controller;

import com.example.ms_pedidos.dto.ApiResponse;
import com.example.ms_pedidos.dto.PedidoRequestDTO;
import com.example.ms_pedidos.model.Pedido;
import com.example.ms_pedidos.service.PedidoService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // LISTAR TODOS LOS PEDIDOS

    @GetMapping
    public ResponseEntity<ApiResponse<List<Pedido>>> listarPedidos() {

        List<Pedido> pedidos = pedidoService.listarPedidos();

        return ResponseEntity.ok(
                ApiResponse.<List<Pedido>>builder()
                        .success(true)
                        .message("Pedidos listados correctamente")
                        .data(pedidos)
                        .build()
        );
    }

    // OBTENER PEDIDO POR ID

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Pedido>> obtenerPedido(@PathVariable Integer id) {

        Pedido pedido = pedidoService.obtenerPedido(id);

        return ResponseEntity.ok(
                ApiResponse.<Pedido>builder()
                        .success(true)
                        .message("Pedido encontrado")
                        .data(pedido)
                        .build()
        );
    }

    // CREAR PEDIDO

    @PostMapping
    public ResponseEntity<ApiResponse<Pedido>> crearPedido(
            @Valid @RequestBody PedidoRequestDTO dto
    ) {

        Pedido pedidoCreado = pedidoService.crearPedido(dto);

        return ResponseEntity.ok(
                ApiResponse.<Pedido>builder()
                        .success(true)
                        .message("Pedido creado correctamente")
                        .data(pedidoCreado)
                        .build()
        );
    }

    // ACTUALIZAR ESTADO

    @PutMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<Pedido>> actualizarEstado(
            @PathVariable Integer id,
            @RequestParam String estado
    ) {

        Pedido pedidoActualizado = pedidoService.actualizarEstado(id, estado);

        return ResponseEntity.ok(
                ApiResponse.<Pedido>builder()
                        .success(true)
                        .message("Estado actualizado correctamente")
                        .data(pedidoActualizado)
                        .build()
        );
    }

    // ELIMINAR PEDIDO

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> eliminarPedido(
            @PathVariable Integer id
    ) {

        pedidoService.eliminarPedido(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Pedido eliminado correctamente")
                        .build()
        );
    }
}