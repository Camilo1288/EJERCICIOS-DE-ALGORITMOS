/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lasalle.ejercicios.algoritmos.exponencial;

/**
 *
 * @author Usuario
 */
public class Evento {
    private final int id;
    private final String nombre;
    private final String categoria;
    private final String ubicacion;
 
    public Evento(int id, String nombre, String categoria, String ubicacion) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.ubicacion = ubicacion;
    }
 
    public int getId() {
        return id;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public String getCategoria() {
        return categoria;
    }
 
    public String getUbicacion() {
        return ubicacion;
    }
 
    @Override
    public String toString() {
        return "Evento{id=" + id + ", nombre='" + nombre + "', categoria='" + categoria
                + "', ubicacion='" + ubicacion + "'}";
    }
}
