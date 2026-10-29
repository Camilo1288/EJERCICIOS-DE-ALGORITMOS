/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lasalle.ejercicios.algotimos.interpolacion;

/**
 *
 * @author Usuario
 */
public class SerieLecturas {
    private final Lectura[] datos; // ordenado por id (puede haber ids repetidos)
 
    public SerieLecturas(Lectura[] datosOrdenados) {
        this.datos = datosOrdenados;
    }
 
    public int tamano() {
        return datos.length;
    }
 
    public Lectura get(int i) {
        return datos[i];
    }
 
    public boolean estaOrdenada() {
        for (int i = 1; i < datos.length; i++) {
            if (datos[i - 1].getId() > datos[i].getId()) {
                return false;
            }
        }
        return true;
    }
 
    /** Busca el id y devuelve la primera y ultima posicion donde aparece. */
    public ResultadoInterpolacion buscar(int id) {
        int[] sondas = {0};
        int primera = limite(id, true, sondas);
        if (primera == -1) {
            return new ResultadoInterpolacion(false, -1, -1, sondas[0]);
        }
        int ultima = limite(id, false, sondas);
        return new ResultadoInterpolacion(true, primera, ultima, sondas[0]);
    }
 
    /**
     * Busqueda por interpolacion que devuelve la PRIMERA o la ULTIMA ocurrencia del id.
     * Cada vez que inspecciona una posicion suma 1 a sondas[0].
     */
    private int limite(int id, boolean buscarPrimera, int[] sondas) {
        int bajo = 0;
        int alto = datos.length - 1;
        int candidato = -1;
 
        while (bajo <= alto
                && id >= datos[bajo].getId()
                && id <= datos[alto].getId()) {
 
            // Todos los de este rango son iguales: evita dividir por cero
            if (datos[bajo].getId() == datos[alto].getId()) {
                sondas[0]++;
                if (datos[bajo].getId() == id) {
                    candidato = buscarPrimera ? bajo : alto;
                }
                break;
            }
 
            int pos;
            if (buscarPrimera && datos[alto].getId() == id) {
                // Hay una tanda de repetidos pegada al extremo derecho: la formula
                // devolveria siempre "alto" y avanzaria de uno en uno. Se biseca.
                pos = bajo + (alto - bajo) / 2;
            } else if (!buscarPrimera && datos[bajo].getId() == id) {
                // Igual, pero con los repetidos pegados al extremo izquierdo.
                pos = bajo + (alto - bajo + 1) / 2;
            } else {
                long rangoIds = (long) datos[alto].getId() - datos[bajo].getId();
                long desplazamiento = (long) id - datos[bajo].getId();
                pos = bajo + (int) (desplazamiento * (alto - bajo) / rangoIds);
            }
 
            sondas[0]++;
            int idPos = datos[pos].getId();
            if (idPos < id) {
                bajo = pos + 1;
            } else if (idPos > id) {
                alto = pos - 1;
            } else {
                // Lo encontro, pero puede haber repetidos: guarda y sigue buscando
                candidato = pos;
                if (buscarPrimera) {
                    alto = pos - 1;   // seguir hacia la izquierda
                } else {
                    bajo = pos + 1;   // seguir hacia la derecha
                }
            }
        }
        return candidato;
    }
}
