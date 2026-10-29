/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lasalle.ejercicios.algoritmos.exponencial;

/**
 *
 * @author Usuario
 */
public class ResultadoExponencial {
    private final boolean encontrado;
    private final int posicion;          // -1 si no se encontro
    private final Evento evento;         // null si no se encontro
    private final int comparacionesExpansion;
    private final int comparacionesBinaria;
    private final int limiteInferior;    // rango donde se hizo la binaria
    private final int limiteSuperior;
 
    public ResultadoExponencial(boolean encontrado, int posicion, Evento evento,
            int comparacionesExpansion, int comparacionesBinaria,
            int limiteInferior, int limiteSuperior) {
        this.encontrado = encontrado;
        this.posicion = posicion;
        this.evento = evento;
        this.comparacionesExpansion = comparacionesExpansion;
        this.comparacionesBinaria = comparacionesBinaria;
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
    }
 
    public boolean isEncontrado() {
        return encontrado;
    }
 
    public int getPosicion() {
        return posicion;
    }
 
    public Evento getEvento() {
        return evento;
    }
 
    public int getComparacionesExpansion() {
        return comparacionesExpansion;
    }
 
    public int getComparacionesBinaria() {
        return comparacionesBinaria;
    }
 
    public int getComparacionesTotales() {
        return comparacionesExpansion + comparacionesBinaria;
    }
 
    @Override
    public String toString() {
        return (encontrado ? "ENCONTRADO en posicion " + posicion : "NO ENCONTRADO")
                + " | comparaciones: " + getComparacionesTotales()
                + " (expansion: " + comparacionesExpansion + ", binaria: " + comparacionesBinaria + ")"
                + " | rango binaria: [" + limiteInferior + ", " + limiteSuperior + "]";
    }
}
