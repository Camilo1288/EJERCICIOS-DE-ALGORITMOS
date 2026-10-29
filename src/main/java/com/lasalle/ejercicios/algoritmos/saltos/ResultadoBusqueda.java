/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lasalle.ejercicios.algoritmos.saltos;

/**
 *
 * @author Usuario
 */
public class ResultadoBusqueda {
    private final boolean encontrado;
    private final int posicion;          
    private final Articulo articulo;     
    private final int comparacionesSalto;
    private final int comparacionesLineales;
    private final int bloqueInicio;      
    private final int bloqueFin;         
 
    public ResultadoBusqueda(boolean encontrado, int posicion, Articulo articulo,
            int comparacionesSalto, int comparacionesLineales, int bloqueInicio, int bloqueFin) {
        this.encontrado = encontrado;
        this.posicion = posicion;
        this.articulo = articulo;
        this.comparacionesSalto = comparacionesSalto;
        this.comparacionesLineales = comparacionesLineales;
        this.bloqueInicio = bloqueInicio;
        this.bloqueFin = bloqueFin;
    }
 
    public boolean isEncontrado() {
        return encontrado;
    }
 
    public int getPosicion() {
        return posicion;
    }
 
    public Articulo getArticulo() {
        return articulo;
    }
 
    public int getComparacionesSalto() {
        return comparacionesSalto;
    }
 
    public int getComparacionesLineales() {
        return comparacionesLineales;
    }
 
    public int getComparacionesTotales() {
        return comparacionesSalto + comparacionesLineales;
    }
 
    public int getBloqueInicio() {
        return bloqueInicio;
    }
 
    public int getBloqueFin() {
        return bloqueFin;
    }
 
    @Override
    public String toString() {
        return (encontrado ? "ENCONTRADO en posicion " + posicion : "NO ENCONTRADO")
                + " | comparaciones: " + getComparacionesTotales()
                + " (saltos: " + comparacionesSalto + ", lineales: " + comparacionesLineales + ")"
                + " | bloque revisado: [" + bloqueInicio + ", " + bloqueFin + ")";
    }
}
