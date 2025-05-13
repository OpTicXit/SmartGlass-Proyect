package com.smartglass.model;

public class Material {
    private String nombre;
    private String unidad;
    private int precio;
    private double co2PorUnidad;
    private int cantidad; // Se agregó la cantidad del material

    public Material(String nombre, String unidad, int precio, double co2PorUnidad) {
        this.nombre = nombre;
        this.unidad = unidad;
        this.precio = precio;
        this.co2PorUnidad = co2PorUnidad;
        this.cantidad = 0; // Inicializamos la cantidad en 0
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public int getPrecio() {
        return precio;
    }

    public double getCo2PorUnidad() {
        return co2PorUnidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double calcularCostoTotal() {
        return cantidad * precio;
    }

    public double calcularCO2Total() {
        return cantidad * co2PorUnidad;
    }
}
