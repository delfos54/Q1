package com.example.ms_inventario.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Inventario {

    @Id
    private Long id;
    private Integer id_insumo;
    private String nombreProduto;
    private int stock_disponible;
}
