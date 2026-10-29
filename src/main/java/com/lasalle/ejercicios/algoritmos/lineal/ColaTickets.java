/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lasalle.ejercicios.algoritmos.lineal;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
/**
 *
 * @author Usuario
 */
public class ColaTickets {
    private final Queue<Ticket> cola = new LinkedList<>();
    private long ultimasComparaciones = 0;
 
    public void encolar(Ticket ticket) {
        cola.offer(ticket);
    }
 
    public Ticket desencolar() {
        return cola.poll();
    }
 
    public int tamano() {
        return cola.size();
    }
 
    public boolean estaVacia() {
        return cola.isEmpty();
    }
 
    public Ticket primerCritico() {
        ultimasComparaciones = 0;
        for (Ticket t : cola) {
            ultimasComparaciones++;
            if (t.esCritico()) {
                return t;
            }
        }
        return null;
    }
 
    public long getUltimasComparaciones() {
        return ultimasComparaciones;
    }
 
    
    public int[] idsEnOrden() {
        int[] ids = new int[cola.size()];
        int i = 0;
        for (Ticket t : cola) {
            ids[i++] = t.getId();
        }
        return ids;
    }
 
    public List<Ticket> comoLista() {
        return new java.util.ArrayList<>(cola);
    }
}
