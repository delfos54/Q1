package com.example.ms_cocina.Controller;

import com.example.ms_cocina.dto.ApiResponse;
import com.example.ms_cocina.dto.CocinaRequestDTO;
import com.example.ms_cocina.Model.TicketCocina;
import com.example.ms_cocina.Service.CocinaService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cocina")
@RequiredArgsConstructor
public class CocinaController {

    private final CocinaService cocinaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TicketCocina>>> listarTickets() {

        List<TicketCocina> tickets = cocinaService.listarTickets();

        return ResponseEntity.ok(
                ApiResponse.<List<TicketCocina>>builder()
                        .success(true)
                        .message("Tickets de cocina listados correctamente")
                        .data(tickets)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TicketCocina>> obtenerTicket(@PathVariable Integer id) {

        TicketCocina ticket = cocinaService.obtenerTicket(id);

        return ResponseEntity.ok(
                ApiResponse.<TicketCocina>builder()
                        .success(true)
                        .message("Ticket de cocina encontrado")
                        .data(ticket)
                        .build()
        );
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<ApiResponse<TicketCocina>> obtenerPorPedidoId(@PathVariable Integer pedidoId) {

        TicketCocina ticket = cocinaService.obtenerPorPedidoId(pedidoId);

        return ResponseEntity.ok(
                ApiResponse.<TicketCocina>builder()
                        .success(true)
                        .message("Ticket encontrado por pedido")
                        .data(ticket)
                        .build()
        );
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<ApiResponse<List<TicketCocina>>> listarPorEstado(@PathVariable String estado) {

        List<TicketCocina> tickets = cocinaService.listarPorEstado(estado);

        return ResponseEntity.ok(
                ApiResponse.<List<TicketCocina>>builder()
                        .success(true)
                        .message("Tickets filtrados por estado")
                        .data(tickets)
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TicketCocina>> crearTicket(
            @Valid @RequestBody CocinaRequestDTO dto
    ) {

        TicketCocina ticket = cocinaService.crearTicket(dto);

        return ResponseEntity.ok(
                ApiResponse.<TicketCocina>builder()
                        .success(true)
                        .message("Ticket de cocina creado correctamente")
                        .data(ticket)
                        .build()
        );
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<TicketCocina>> actualizarEstado(
            @PathVariable Integer id,
            @RequestParam String estado
    ) {

        TicketCocina ticket = cocinaService.actualizarEstado(id, estado);

        return ResponseEntity.ok(
                ApiResponse.<TicketCocina>builder()
                        .success(true)
                        .message("Estado actualizado correctamente")
                        .data(ticket)
                        .build()
        );
    }

    @PutMapping("/{id}/observacion")
    public ResponseEntity<ApiResponse<TicketCocina>> actualizarObservacion(
            @PathVariable Integer id,
            @RequestParam String observacion
    ) {

        TicketCocina ticket = cocinaService.actualizarObservacion(id, observacion);

        return ResponseEntity.ok(
                ApiResponse.<TicketCocina>builder()
                        .success(true)
                        .message("Observacion actualizada correctamente")
                        .data(ticket)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> eliminarTicket(@PathVariable Integer id) {

        cocinaService.eliminarTicket(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Ticket de cocina eliminado correctamente")
                        .build()
        );
    }
}