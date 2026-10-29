/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lasalle.ejercicios.algoritmos.saltos;

/**
 *
 * @author Usuario
 */
public class IndiceSaltos {
    private final Articulo[] datos; 
    private final int paso;
 
    
    public IndiceSaltos(Articulo[] datosOrdenados) {
        this(datosOrdenados, (int) Math.max(1, Math.sqrt(datosOrdenados.length)));
    }
 
    
    public IndiceSaltos(Articulo[] datosOrdenados, int paso) {
        if (paso < 1) {
            throw new IllegalArgumentException("El paso debe ser >= 1");
        }
        this.datos = datosOrdenados;
        this.paso = paso;
    }
 
    public int getPaso() {
        return paso;
    }
 
    public int tamano() {
        return datos.length;
    }
 
    public boolean estaOrdenado() {
        for (int i = 1; i < datos.length; i++) {
            if (datos[i - 1].getCodigo().compareTo(datos[i].getCodigo()) > 0) {
                return false;
            }
        }
        return true;
    }
 
    public Articulo get(int i) {
        return datos[i];
    }
 
    
    public ResultadoBusqueda buscar(String codigo) {
        int n = datos.length;
        int saltos = 0;
        int lineales = 0;
 
        if (n == 0) {
            return new ResultadoBusqueda(false, -1, null, 0, 0, 0, 0);
        }
 
        int inicio = 0;
        int fin = Math.min(paso, n); 
 
        
        while (true) {
            saltos++;
            if (datos[fin - 1].getCodigo().compareTo(codigo) >= 0) {
                break; 
            }
            inicio = fin;
            if (inicio >= n) {
                
                return new ResultadoBusqueda(false, -1, null, saltos, lineales, n, n);
            }
            fin = Math.min(fin + paso, n);
        }
 
        
        for (int i = inicio; i < fin; i++) {
            lineales++;
            int cmp = datos[i].getCodigo().compareTo(codigo);
            if (cmp == 0) {
                return new ResultadoBusqueda(true, i, datos[i], saltos, lineales, inicio, fin);
            }
            if (cmp > 0) {
                break; 
            }
        }
        return new ResultadoBusqueda(false, -1, null, saltos, lineales, inicio, fin);
    }
}
