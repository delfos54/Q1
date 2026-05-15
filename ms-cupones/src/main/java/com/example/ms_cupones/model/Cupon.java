package com.example.ms_cupones.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cupones")
public class Cupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String codigo;

    private Double descuento;

    private boolean activo;

    private int usosDisponibles;

    public Integer getId() { return id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Double getDescuento() { return descuento; }
    public void setDescuento(Double descuento) { this.descuento = descuento; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public int getUsosDisponibles() { return usosDisponibles; }
    public void setUsosDisponibles(int usosDisponibles) { this.usosDisponibles = usosDisponibles; }
}
