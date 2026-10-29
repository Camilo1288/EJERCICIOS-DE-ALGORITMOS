/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lasalle.ejercicios.algotimos.interpolacion;

/**
 *
 * @author Usuario
 */
public class Lectura {
    private final int id;
    private final String sensor;
    private final double valor;
    private final int hora; 
 
    public Lectura(int id, String sensor, double valor, int hora) {
        this.id = id;
        this.sensor = sensor;
        this.valor = valor;
        this.hora = hora;
    }
 
    public int getId() {
        return id;
    }
 
    public String getSensor() {
        return sensor;
    }
 
    public double getValor() {
        return valor;
    }
 
    public int getHora() {
        return hora;
    }
 
    @Override
    public String toString() {
        return "Lectura{id=" + id + ", sensor='" + sensor + "', valor=" + valor + ", hora=" + hora + "}";
    }
}
