/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lasalle.ejercicios.algoritmos.binaria;

/**
 *
 * @author Usuario
 */
public class Producto {
    
    private final int id;
    private final String nombre;
    private final String categoria;
    private final double precio;
 
    public Producto(int id, String nombre, String categoria, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
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
 
    public double getPrecio() {
        return precio;
    }
 
    @Override
    public String toString() {
        return "Producto{id=" + id + ", nombre='" + nombre + "', categoria='" + categoria
                + "', precio=" + precio + "}";
    }
}
