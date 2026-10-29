/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lasalle.ejercicios.algoritmos.lineal;

/**
 *
 * @author Usuario
 */
public class Ticket {
    private final int id;
    private final String titulo;
    private final Prioridad prioridad;
    private final int minutosEspera;
 
    public Ticket(int id, String titulo, Prioridad prioridad, int minutosEspera) {
        this.id = id;
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.minutosEspera = minutosEspera;
    }
 
    public int getId() {
        return id;
    }
 
    public String getTitulo() {
        return titulo;
    }
 
    public Prioridad getPrioridad() {
        return prioridad;
    }
 
    public int getMinutosEspera() {
        return minutosEspera;
    }
 
    public boolean esCritico() {
        return prioridad == Prioridad.CRITICA;
    }
 
    @Override
    public String toString() {
        return "Ticket{id=" + id + ", titulo='" + titulo + "', prioridad=" + prioridad
                + ", minutosEspera=" + minutosEspera + "}";
    }
}
