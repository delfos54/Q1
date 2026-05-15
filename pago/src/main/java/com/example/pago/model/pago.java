package com.example.pago.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data

public class pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;    
    private String usuarioId;
    private long productoId;
    private Integer cantidad;
    private BigDecimal montoTotal;
    private String metodoPago;
    private String Estado;
    private LocalDateTime fechaPago;
    @PrePersist
    protected void onCreate(){
        this.fechaPago = LocalDateTime.now();
        this.Estado = "PENDIENTE";
    }




}
