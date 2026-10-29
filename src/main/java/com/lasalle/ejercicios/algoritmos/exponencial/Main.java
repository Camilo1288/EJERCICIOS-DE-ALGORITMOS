/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lasalle.ejercicios.algoritmos.exponencial;
import java.util.Random;
/**
 *
 * @author Usuario
 */
public class Main {
    private static final String[] NOMBRES = {
        "Concierto", "Conferencia", "Feria", "Taller", "Partido", "Obra de teatro", "Seminario", "Exposicion"
    };
    private static final String[] CATEGORIAS = {"Musica", "Academico", "Deporte", "Cultura"};
    private static final String[] UBICACIONES = {"Bogota", "Medellin", "Cali", "Barranquilla", "Cartagena"};
 
    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 5: BUSQUEDA EXPONENCIAL (lista que crece durante la carga) ===\n");
 
        // 1) Ejemplo pequeno
        System.out.println("--- 1) Ejemplo pequeno (20 eventos) ---");
        ListaEventos pequena = new ListaEventos();
        UltimoId ultimo = new UltimoId();
        Random r1 = new Random(3);
        for (int i = 0; i < 20; i++) {
            pequena.agregar(nuevo(ultimo, r1, 5));
        }
        System.out.print("ids: ");
        for (int i = 0; i < pequena.tamano(); i++) {
            System.out.print(pequena.obtener(i).getId() + " ");
        }
        System.out.println();
        int primerId = pequena.obtener(0).getId();
        int[] consultas = {
            primerId, pequena.obtener(3).getId(), pequena.obtener(10).getId(),
            pequena.obtener(19).getId(), pequena.obtener(5).getId() + 1, 1, 999_999
        };
        for (int c : consultas) {
            System.out.println("buscar(" + c + ") -> " + BuscadorExponencial.buscar(pequena, c));
        }
 
        // 2) Casos limite
        System.out.println("\n--- 2) Casos limite ---");
        ListaEventos vacia = new ListaEventos();
        System.out.println("Lista vacia:               " + BuscadorExponencial.buscar(vacia, 5));
 
        ListaEventos una = new ListaEventos();
        una.agregar(new Evento(50, "Unico", "Demo", "Bogota"));
        System.out.println("Un elemento, existe (50):  " + BuscadorExponencial.buscar(una, 50));
        System.out.println("Un elemento, menor (10):   " + BuscadorExponencial.buscar(una, 10));
        System.out.println("Un elemento, mayor (90):   " + BuscadorExponencial.buscar(una, 90));
 
        // Prueba exhaustiva: para tamanos de 1 a 300 busca TODOS los ids y los que no existen
        int erroresExhaustivo = 0;
        for (int n = 1; n <= 300; n++) {
            ListaEventos l = new ListaEventos();
            for (int i = 0; i < n; i++) {
                l.agregar(new Evento(i * 2 + 10, "E", "C", "U")); // ids pares desde 10
            }
            for (int i = 0; i < n; i++) {
                ResultadoExponencial r = BuscadorExponencial.buscar(l, i * 2 + 10);
                if (!r.isEncontrado() || r.getPosicion() != i) {
                    erroresExhaustivo++;
                }
            }
            for (int id = 9; id <= n * 2 + 11; id += 2) { // impares: no existen
                if (BuscadorExponencial.buscar(l, id).isEncontrado()) {
                    erroresExhaustivo++;
                }
            }
        }
        System.out.println("Prueba exhaustiva (tamanos 1 a 300, todos los ids): errores = " + erroresExhaustivo);
 
        // 3) Carga progresiva: la lista crece y se busca entre cargas
        System.out.println("\n--- 3) Carga progresiva: se buscan eventos mientras la lista crece ---");
        ListaEventos enCarga = new ListaEventos();
        UltimoId u3 = new UltimoId();
        Random r3 = new Random(11);
        int[] lotes = {5, 20, 100, 1_000, 10_000, 100_000, 500_000};
        int idFuturo = -1;
        for (int lote : lotes) {
            // si habia un id "futuro" pendiente, ahora deberia aparecer o no segun el lote
            for (int i = 0; i < lote; i++) {
                enCarga.agregar(nuevo(u3, r3, 5));
            }
            int n = enCarga.tamano();
            Evento ultimoCargado = enCarga.obtener(n - 1);
            Evento medio = enCarga.obtener(n / 2);
            ResultadoExponencial rUlt = BuscadorExponencial.buscar(enCarga, ultimoCargado.getId());
            ResultadoExponencial rMed = BuscadorExponencial.buscar(enCarga, medio.getId());
            ResultadoExponencial rFut = BuscadorExponencial.buscar(enCarga, ultimoCargado.getId() + 1);
            System.out.printf("Tamano actual %,8d | ultimo: %-12s (%2d comp.) | medio: %-12s (%2d comp.) | aun no cargado: %-14s (%2d comp.)%n",
                    n,
                    rUlt.isEncontrado() ? "pos " + rUlt.getPosicion() : "FALLO", rUlt.getComparacionesTotales(),
                    rMed.isEncontrado() ? "pos " + rMed.getPosicion() : "FALLO", rMed.getComparacionesTotales(),
                    rFut.isEncontrado() ? "ENCONTRADO?!" : "no existe aun", rFut.getComparacionesTotales());
            idFuturo = ultimoCargado.getId() + 1;
        }
        // Se carga justo el id que antes "no existia": ahora debe aparecer
        enCarga.agregar(new Evento(idFuturo, "Evento tardio", "Demo", "Bogota"));
        System.out.println("Se carga el evento con id " + idFuturo + " -> " + BuscadorExponencial.buscar(enCarga, idFuturo));
 
        // 4) Datos aleatorios grandes
        System.out.println("\n--- 4) Listas grandes (5.000 consultas: mitad existen, mitad no) ---");
        int[] tamanos = {10_000, 100_000, 1_000_000, 2_000_000};
        for (int n : tamanos) {
            ListaEventos l = new ListaEventos();
            UltimoId u = new UltimoId();
            Random r = new Random(42);
            for (int i = 0; i < n; i++) {
                l.agregar(nuevo(u, r, 5));
            }
            probar(l, 5_000, "n = " + String.format("%,d", n));
        }
 
        // 5) El costo depende de la POSICION, no del tamano total
        System.out.println("\n--- 5) Costo segun la posicion del evento (lista de 2.000.000) ---");
        ListaEventos grande = new ListaEventos();
        UltimoId ug = new UltimoId();
        Random rg = new Random(42);
        for (int i = 0; i < 2_000_000; i++) {
            grande.agregar(nuevo(ug, rg, 5));
        }
        int[] posiciones = {0, 10, 1_000, 100_000, 1_000_000, 1_999_999};
        for (int pos : posiciones) {
            int id = grande.obtener(pos).getId();
            ResultadoExponencial re = BuscadorExponencial.buscar(grande, id);
            int cb = comparacionesBinaria(grande, id);
            System.out.printf("posicion %,9d | exponencial: %3d comparaciones | binaria clasica: %3d comparaciones%n",
                    pos, re.getComparacionesTotales(), cb);
        }
    }
 
    /** Ejecuta consultas, verifica contra la binaria clasica y reporta comparaciones y tiempo. */
    private static void probar(ListaEventos lista, int consultas, String etiqueta) {
        Random rnd = new Random(123);
        int n = lista.tamano();
        int maxId = lista.obtener(n - 1).getId();
        int[] objetivos = new int[consultas];
        for (int i = 0; i < consultas; i++) {
            objetivos[i] = (i % 2 == 0) ? lista.obtener(rnd.nextInt(n)).getId() : rnd.nextInt(maxId + 10);
        }
 
        for (int i = 0; i < Math.min(consultas, 500); i++) { // calentamiento
            BuscadorExponencial.buscar(lista, objetivos[i]);
        }
 
        ResultadoExponencial[] res = new ResultadoExponencial[consultas];
        long inicio = System.nanoTime();
        for (int i = 0; i < consultas; i++) {
            res[i] = BuscadorExponencial.buscar(lista, objetivos[i]);
        }
        double nsPorConsulta = (System.nanoTime() - inicio) / (double) consultas;
 
        long totalExp = 0;
        long totalBin = 0;
        int errores = 0;
        for (int i = 0; i < consultas; i++) {
            totalExp += res[i].getComparacionesTotales();
            totalBin += comparacionesBinaria(lista, objetivos[i]);
            int pos = posicionBinaria(lista, objetivos[i]);
            boolean existe = pos >= 0;
            if (existe != res[i].isEncontrado() || (existe && res[i].getPosicion() != pos)) {
                errores++;
            }
        }
        System.out.printf("%-18s | comp. exponencial prom: %5.1f | comp. binaria prom: %5.1f | tiempo/consulta: %7.0f ns | errores: %d%n",
                etiqueta, totalExp / (double) consultas, totalBin / (double) consultas,
                nsPorConsulta, errores);
    }
 
    /** Busqueda binaria clasica (necesita conocer el tamano). Devuelve la posicion o -1. */
    private static int posicionBinaria(ListaEventos lista, int id) {
        int izq = 0;
        int der = lista.tamano() - 1;
        while (izq <= der) {
            int medio = izq + (der - izq) / 2;
            int idMedio = lista.obtener(medio).getId();
            if (idMedio == id) {
                return medio;
            }
            if (idMedio < id) {
                izq = medio + 1;
            } else {
                der = medio - 1;
            }
        }
        return -1;
    }
 
    /** Cuenta las comparaciones que haria la binaria clasica. */
    private static int comparacionesBinaria(ListaEventos lista, int id) {
        int izq = 0;
        int der = lista.tamano() - 1;
        int comparaciones = 0;
        while (izq <= der) {
            int medio = izq + (der - izq) / 2;
            int idMedio = lista.obtener(medio).getId();
            comparaciones++;
            if (idMedio == id) {
                return comparaciones;
            }
            if (idMedio < id) {
                izq = medio + 1;
            } else {
                der = medio - 1;
            }
        }
        return comparaciones;
    }
 
    /** Guarda el ultimo id generado para que los ids siempre sean crecientes. */
    private static class UltimoId {
        int valor = 1000;
    }
 
    private static Evento nuevo(UltimoId ultimo, Random rnd, int saltoMaximo) {
        ultimo.valor += 1 + rnd.nextInt(saltoMaximo);
        return new Evento(ultimo.valor,
                NOMBRES[rnd.nextInt(NOMBRES.length)],
                CATEGORIAS[rnd.nextInt(CATEGORIAS.length)],
                UBICACIONES[rnd.nextInt(UBICACIONES.length)]);
    }
}
