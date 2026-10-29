/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lasalle.ejercicios.algoritmos.binaria;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Usuario
 */
public class ListaPrecios {
    private final List<Producto> productos = new ArrayList<>();
    private long ultimasComparaciones = 0;
 
    
    public int lowerBound(double precioBuscado) {
        ultimasComparaciones = 0;
        int izq = 0;
        int der = productos.size(); 
        while (izq < der) {
            int medio = izq + (der - izq) / 2;
            ultimasComparaciones++;
            if (productos.get(medio).getPrecio() < precioBuscado) {
                izq = medio + 1;   
            } else {
                der = medio;       
            }
        }
        return izq;
    }
 
    
    public int upperBound(double precioBuscado) {
        ultimasComparaciones = 0;
        int izq = 0;
        int der = productos.size();
        while (izq < der) {
            int medio = izq + (der - izq) / 2;
            ultimasComparaciones++;
            if (productos.get(medio).getPrecio() <= precioBuscado) {
                izq = medio + 1;
            } else {
                der = medio;
            }
        }
        return izq;
    }
 
    
    public int insertar(Producto producto) {
        int indice = lowerBound(producto.getPrecio());
        productos.add(indice, producto);
        return indice;
    }
 
    
    public int contarDuplicados(double precio) {
        int inicio = lowerBound(precio);
        int fin = upperBound(precio);
        return fin - inicio;
    }
 
    
    public Producto buscarPrimero(double precio) {
        int i = lowerBound(precio);
        if (i < productos.size() && productos.get(i).getPrecio() == precio) {
            return productos.get(i);
        }
        return null;
    }
 
    
    public void cargarOrdenada(List<Producto> yaOrdenados) {
        productos.clear();
        productos.addAll(yaOrdenados);
    }
 
    public boolean estaOrdenada() {
        for (int i = 1; i < productos.size(); i++) {
            if (productos.get(i - 1).getPrecio() > productos.get(i).getPrecio()) {
                return false;
            }
        }
        return true;
    }
 
    public Producto get(int indice) {
        return productos.get(indice);
    }
 
    public int tamano() {
        return productos.size();
    }
 
    public long getUltimasComparaciones() {
        return ultimasComparaciones;
    }
}
