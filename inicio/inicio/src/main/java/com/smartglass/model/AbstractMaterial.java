package com.smartglass.model;

public abstract class AbstractMaterial {
    protected final String nombre;
    protected final String unidad;
    protected final int precio;
    protected int cantidad;
    protected final double co2PorUnidad;

    public AbstractMaterial(String nombre, String unidad, int precio, double co2PorUnidad) {
        this.nombre = nombre;
        this.unidad = unidad;
        this.precio = precio;
        this.cantidad = 0;
        this.co2PorUnidad = co2PorUnidad;
    }

    public abstract double calcularCostoTotal(double area);
    public abstract double calcularCO2Total(double area);

    public String getNombre() {
        return nombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public int getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getCo2PorUnidad() {
        return co2PorUnidad;
    }
}
