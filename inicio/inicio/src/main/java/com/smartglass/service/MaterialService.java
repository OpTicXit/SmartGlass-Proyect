package com.smartglass.service;

import com.smartglass.model.Material;

import java.util.HashMap;
import java.util.Map;

public class MaterialService {
    private static final MaterialService instancia = new MaterialService();
    private final Map<Integer, Material> materiales = new HashMap<>();

    private MaterialService() {}

    public static MaterialService getInstance() {
        return instancia;
    }

    public Map<Integer, Material> getMateriales() {
        return materiales;
    }

    public void registrarMaterial(int codigo, String nombre, String unidad, int precio, double co2PorUnidad) {
        materiales.put(codigo, new Material(nombre, unidad, precio, co2PorUnidad));
    }

    public void eliminarMaterial(int codigo) {
        materiales.remove(codigo);
    }

    public void modificarMaterial(int codigo, String nuevoNombre) {
        Material material = materiales.get(codigo);
        if (material != null && nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            material.setNombre(nuevoNombre);
        }
    }

    public boolean existeMaterial(int codigo) {
        return materiales.containsKey(codigo);
    }
}
