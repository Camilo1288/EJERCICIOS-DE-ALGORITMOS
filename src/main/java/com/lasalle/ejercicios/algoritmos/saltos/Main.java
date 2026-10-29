/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lasalle.ejercicios.algoritmos.saltos;
import java.util.Arrays;
import java.util.Random;

public class Main {
    
    private static final String[] DESCRIPCIONES = {
        "Tornillo", "Tuerca", "Cable", "Bombillo", "Cinta", "Pintura", "Martillo", "Taladro"
    };
    private static final String[] CATEGORIAS = {"Ferreteria", "Electrico", "Pintura", "Herramientas"};
 
    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 3: BUSQUEDA POR SALTOS (Jump Search) ===\n");
 
        
        System.out.println("--- 1) Ejemplo pequeno (16 codigos, paso = 4) ---");
        Articulo[] pequeno = generarIndice(16, new Random(5), 3);
        IndiceSaltos indicePequeno = new IndiceSaltos(pequeno);
        for (int i = 0; i < pequeno.length; i++) {
            if (i % indicePequeno.getPaso() == 0) {
                System.out.print("\n  Bloque " + (i / indicePequeno.getPaso()) + ": ");
            }
            System.out.print(pequeno[i].getCodigo() + " ");
        }
        System.out.println("\n");
        String[] consultasPequenas = {
            pequeno[0].getCodigo(), pequeno[6].getCodigo(), pequeno[15].getCodigo(),
            "COD-0000000", "COD-9999999"
        };
        for (String c : consultasPequenas) {
            System.out.println("buscar(" + c + ") -> " + indicePequeno.buscar(c));
        }
 
        
        System.out.println("\n--- 2) Casos limite ---");
        IndiceSaltos vacio = new IndiceSaltos(new Articulo[0]);
        System.out.println("Indice vacio: " + vacio.buscar("COD-0000001"));
 
        Articulo[] unSolo = {new Articulo("COD-0000050", "Unico", "Demo", 1)};
        IndiceSaltos indiceUno = new IndiceSaltos(unSolo);
        System.out.println("Un elemento, existe:   " + indiceUno.buscar("COD-0000050"));
        System.out.println("Un elemento, menor:    " + indiceUno.buscar("COD-0000001"));
        System.out.println("Un elemento, mayor:    " + indiceUno.buscar("COD-0000099"));
 
        
        Articulo[] raro = generarIndice(10, new Random(8), 3);
        IndiceSaltos indiceRaro = new IndiceSaltos(raro);
        System.out.println("n = 10 (paso " + indiceRaro.getPaso() + ", ultimo bloque incompleto):");
        System.out.println("  Ultimo codigo: " + indiceRaro.buscar(raro[9].getCodigo()));
        System.out.println("  Primer codigo: " + indiceRaro.buscar(raro[0].getCodigo()));
 
        
        System.out.println("\n--- 3) Datos aleatorios (5.000 consultas: mitad existen, mitad no) ---");
        int[] tamanos = {10_000, 100_000, 500_000, 1_000_000};
        for (int n : tamanos) {
            Articulo[] datos = generarIndice(n, new Random(42), 10);
            IndiceSaltos indice = new IndiceSaltos(datos);
            probar(indice, datos, 5_000, "n = " + String.format("%,d", n));
        }
 
        
        System.out.println("\n--- 4) Efecto del tamano de bloque (n = 1.000.000) ---");
        Articulo[] grande = generarIndice(1_000_000, new Random(42), 10);
        int[] pasos = {2, 10, 100, 1000, 10_000, 100_000};
        for (int p : pasos) {
            probar(new IndiceSaltos(grande, p), grande, 2_000, "paso = " + String.format("%,d", p));
        }
        System.out.println("(el paso optimo es raiz de n = 1.000; ahi se minimizan las comparaciones totales)");
    }
 
    
    private static void probar(IndiceSaltos indice, Articulo[] datos, int consultas, String etiqueta) {
        Random rnd = new Random(123);
        int n = datos.length;
        String[] objetivos = new String[consultas];
        boolean[] deberiaExistir = new boolean[consultas];
        for (int i = 0; i < consultas; i++) {
            if (i % 2 == 0) {
                objetivos[i] = datos[rnd.nextInt(n)].getCodigo();
                deberiaExistir[i] = true;
            } else {
                
                objetivos[i] = codigo(rnd.nextInt(Math.max(1, n * 10)) * 10 + 1000000000L);
                deberiaExistir[i] = Arrays.binarySearch(datos, new Articulo(objetivos[i], "", "", 0),
                        (a, b) -> a.getCodigo().compareTo(b.getCodigo())) >= 0;
            }
        }
 
        
        for (int i = 0; i < Math.min(consultas, 500); i++) {
            indice.buscar(objetivos[i]);
        }
 
        long totalComp = 0;
        int maxComp = 0;
        int errores = 0;
        long inicio = System.nanoTime();
        ResultadoBusqueda[] resultados = new ResultadoBusqueda[consultas];
        for (int i = 0; i < consultas; i++) {
            resultados[i] = indice.buscar(objetivos[i]);
        }
        long tiempo = System.nanoTime() - inicio;
 
        for (int i = 0; i < consultas; i++) {
            ResultadoBusqueda r = resultados[i];
            totalComp += r.getComparacionesTotales();
            maxComp = Math.max(maxComp, r.getComparacionesTotales());
            if (r.isEncontrado() != deberiaExistir[i]) {
                errores++;
            }
        }
        System.out.printf("%-18s | paso: %,7d | comp. promedio: %,9.1f | comp. maxima: %,7d | tiempo/consulta: %8.0f ns | errores: %d%n",
                etiqueta, indice.getPaso(), totalComp / (double) consultas, maxComp,
                tiempo / (double) consultas, errores);
    }
 
    
    private static Articulo[] generarIndice(int n, Random rnd, int saltoMaximo) {
        Articulo[] datos = new Articulo[n];
        long numero = 1_000_000_000L;
        for (int i = 0; i < n; i++) {
            numero += 1 + rnd.nextInt(saltoMaximo);
            datos[i] = new Articulo(codigo(numero),
                    DESCRIPCIONES[rnd.nextInt(DESCRIPCIONES.length)],
                    CATEGORIAS[rnd.nextInt(CATEGORIAS.length)],
                    rnd.nextInt(500));
        }
        return datos;
    }
 
    
    private static String codigo(long numero) {
        return "COD-" + String.format("%010d", numero);
    }
}
