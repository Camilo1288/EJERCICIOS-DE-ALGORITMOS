/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lasalle.ejercicios.algotimos.interpolacion;

/**
 *
 * @author Usuario
 */
public class ResultadoInterpolacion {
    private final boolean encontrado;
    private final int primera;   
    private final int ultima;    
    private final int sondas;
 
    public ResultadoInterpolacion(boolean encontrado, int primera, int ultima, int sondas) {
        this.encontrado = encontrado;
        this.primera = primera;
        this.ultima = ultima;
        this.sondas = sondas;
    }
 
    public boolean isEncontrado() {
        return encontrado;
    }
 
    public int getPrimera() {
        return primera;
    }
 
    public int getUltima() {
        return ultima;
    }
 
    public int getCantidad() {
        return encontrado ? ultima - primera + 1 : 0;
    }
 
    public int getSondas() {
        return sondas;
    }
 
    @Override
    public String toString() {
        if (!encontrado) {
            return "NO ENCONTRADO | sondas: " + sondas;
        }
        return "ENCONTRADO | primera: " + primera + " | ultima: " + ultima
                + " | repetidos: " + getCantidad() + " | sondas: " + sondas;
    }
}
