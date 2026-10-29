/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lasalle.ejercicios.algoritmos.exponencial;

/**
 *
 * @author Usuario
 */
public class ListaEventos {
    private Evento[] datos = new Evento[4];
    private int tamano = 0;
 
    
    public void agregar(Evento evento) {
        if (tamano > 0 && evento.getId() <= datos[tamano - 1].getId()) {
            throw new IllegalArgumentException("El id debe ser mayor que el ultimo cargado");
        }
        if (tamano == datos.length) {
            Evento[] nuevo = new Evento[datos.length * 2];
            System.arraycopy(datos, 0, nuevo, 0, tamano);
            datos = nuevo;
        }
        datos[tamano++] = evento;
    }
 
    
    public Evento obtener(int i) {
        if (i < 0 || i >= tamano) {
            return null;
        }
        return datos[i];
    }
 
    
    public int tamano() {
        return tamano;
    }
}
