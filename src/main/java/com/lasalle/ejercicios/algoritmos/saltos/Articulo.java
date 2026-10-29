/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lasalle.ejercicios.algoritmos.saltos;

/**
 *
 * @author Usuario
 */
public class Articulo {
    private final String codigo;
    private final String descripcion;
    private final String categoria;
    private final int stock;
 
    public Articulo(String codigo, String descripcion, String categoria, int stock) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.stock = stock;
    }
 
    public String getCodigo() {
        return codigo;
    }
 
    public String getDescripcion() {
        return descripcion;
    }
 
    public String getCategoria() {
        return categoria;
    }
 
    public int getStock() {
        return stock;
    }
 
    @Override
    public String toString() {
        return "Articulo{codigo='" + codigo + "', descripcion='" + descripcion
                + "', categoria='" + categoria + "', stock=" + stock + "}";
    }
}
