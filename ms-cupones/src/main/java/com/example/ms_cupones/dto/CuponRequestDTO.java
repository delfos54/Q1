package com.example.ms_cupones.dto;

public class CuponRequestDTO {

    private String codigo;
    private Double descuento;
    private int usosDisponibles;

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Double getDescuento() { return descuento; }
    public void setDescuento(Double descuento) { this.descuento = descuento; }

    public int getUsosDisponibles() { return usosDisponibles; }
    public void setUsosDisponibles(int usosDisponibles) { this.usosDisponibles = usosDisponibles; }
}
