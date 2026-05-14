package com.example.ms_pedidos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pedidoId;

    private Long productoId;

    private Integer cantidad;

    private Double precio;
}