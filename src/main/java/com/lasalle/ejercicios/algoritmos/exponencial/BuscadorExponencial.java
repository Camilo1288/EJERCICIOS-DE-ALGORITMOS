/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lasalle.ejercicios.algoritmos.exponencial;

/**
 *
 * @author Usuario
 */
public class BuscadorExponencial {
    public static ResultadoExponencial buscar(ListaEventos lista, int id) {
        int cExpansion = 0;
        int cBinaria = 0;
 
        Evento primero = lista.obtener(0);
        if (primero == null) {
            return new ResultadoExponencial(false, -1, null, 0, 0, 0, 0); // lista vacia
        }
        cExpansion++;
        if (primero.getId() == id) {
            return new ResultadoExponencial(true, 0, primero, cExpansion, 0, 0, 0);
        }
        if (primero.getId() > id) {
            return new ResultadoExponencial(false, -1, null, cExpansion, 0, 0, 0);
        }
 
        // FASE 1: expansion 1, 2, 4, 8, ...
        long limite = 1;
        while (limite < (1L << 30)) {
            Evento e = lista.obtener((int) limite);
            if (e == null) {
                break; // nos salimos del final de la lista
            }
            cExpansion++;
            if (e.getId() >= id) {
                break; // ya nos pasamos (o lo encontramos)
            }
            limite *= 2;
        }
 
        // FASE 2: binaria entre limite/2 y limite
        int izq = (int) (limite / 2);
        int der = (int) Math.min(limite, Integer.MAX_VALUE);
        int rangoIzq = izq;
        int rangoDer = der;
        while (izq <= der) {
            int medio = izq + (der - izq) / 2;
            Evento e = lista.obtener(medio);
            if (e == null) {
                der = medio - 1; // posicion que aun no existe: se trata como mayor
                continue;
            }
            cBinaria++;
            if (e.getId() == id) {
                return new ResultadoExponencial(true, medio, e, cExpansion, cBinaria, rangoIzq, rangoDer);
            }
            if (e.getId() < id) {
                izq = medio + 1;
            } else {
                der = medio - 1;
            }
        }
        return new ResultadoExponencial(false, -1, null, cExpansion, cBinaria, rangoIzq, rangoDer);
    }
}
