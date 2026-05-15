package com.example.coffee.connect.ProductoController;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.coffee.connect.DTO.ApiResponse;
import com.example.coffee.connect.DTO.productosDTO;
import com.example.coffee.connect.model.productos;
import com.example.coffee.connect.service.ProductosService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductosController {

    private final ProductosService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<productos>> crear(@Valid @RequestBody productosDTO dto) {

        productos p = service.crear(dto);

        return ResponseEntity.status(201).body(
                ApiResponse.<productos>builder()
                        .respuesta(true)
                        .mensaje("Producto creado")
                        .data(p)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<productos>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<productos>>builder()
                        .respuesta(true)
                        .mensaje("Listado obtenido")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<productos>> obtener(@PathVariable Integer id) {

        return ResponseEntity.ok(
                ApiResponse.<productos>builder()
                        .respuesta(true)
                        .mensaje("Producto obtenido")
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<ApiResponse<productos>> actualizar(@PathVariable Integer id, @Valid @RequestBody productosDTO dto){
        productos p =service.actualizar(id, dto);
        return ResponseEntity.ok(
                ApiResponse.<productos>builder()
                        .respuesta(true)
                        .mensaje("Producto actualizado")
                        .data(p)
                        .build()
        );
   }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .respuesta(true)
                        .mensaje("Producto eliminado")
                        .build()
        );
    }

    @GetMapping("/{id}/precio")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public BigDecimal obtenerPrecioRápido(@PathVariable Integer id) {
        // Reutilizamos tu servicio existente para buscar el producto
        productos p = service.obtener(id);
        
        // Retornamos únicamente el valor del precio
        // (Asumo que tu modelo se llama getPrecio(), si se llama distinto, ajústalo)
        return p.getPrecio(); 
    }
}
    

