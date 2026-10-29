/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lasalle.ejercicios.algoritmos.binaria;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
/**
 *
 * @author Usuario
 */
public class Main {
    private static final String[] NOMBRES = {
        "Arroz", "Leche", "Pan", "Aceite", "Cafe", "Azucar", "Huevos", "Queso", "Pasta", "Jabon"
    };
    private static final String[] CATEGORIAS = {"Alimentos", "Aseo", "Bebidas", "Lacteos"};
 
    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 2: BUSQUEDA BINARIA (lowerBound con duplicados) ===\n");
 
        
        System.out.println("--- 1) Ejemplo pequeno ---");
        ListaPrecios pequena = new ListaPrecios();
        double[] precios = {5000, 2000, 8000, 2000, 5000, 2000, 1000};
        int id = 1;
        for (double p : precios) {
            int pos = pequena.insertar(new Producto(id, "Item" + id, "Demo", p));
            System.out.println("Insertado precio " + p + " -> posicion " + pos);
            id++;
        }
        System.out.print("Lista final de precios: ");
        for (int i = 0; i < pequena.tamano(); i++) {
            System.out.print(pequena.get(i).getPrecio() + " ");
        }
        System.out.println();
        System.out.println("lowerBound(2000) = " + pequena.lowerBound(2000)
                + " | upperBound(2000) = " + pequena.upperBound(2000)
                + " | duplicados de 2000 = " + pequena.contarDuplicados(2000));
        System.out.println("lowerBound(3000) = " + pequena.lowerBound(3000) + " (no existe, es donde se insertaria)");
 
        
        System.out.println("\n--- 2) Casos limite ---");
        ListaPrecios vacia = new ListaPrecios();
        System.out.println("Lista vacia: lowerBound(100) = " + vacia.lowerBound(100) + " (esperado 0)");
 
        ListaPrecios uno = new ListaPrecios();
        uno.insertar(new Producto(1, "Unico", "Demo", 500));
        System.out.println("Un elemento (500): lowerBound(100) = " + uno.lowerBound(100) + " (esperado 0), "
                + "lowerBound(500) = " + uno.lowerBound(500) + " (esperado 0), "
                + "lowerBound(900) = " + uno.lowerBound(900) + " (esperado 1)");
 
        ListaPrecios iguales = new ListaPrecios();
        for (int i = 0; i < 1000; i++) {
            iguales.insertar(new Producto(i, "Igual", "Demo", 700));
        }
        System.out.println("1000 precios iguales (700): lowerBound(700) = " + iguales.lowerBound(700)
                + " (esperado 0), upperBound(700) = " + iguales.upperBound(700)
                + " (esperado 1000), duplicados = " + iguales.contarDuplicados(700));
        System.out.println("Menor que todos: lowerBound(1) = " + iguales.lowerBound(1)
                + " | Mayor que todos: lowerBound(99999) = " + iguales.lowerBound(99999));
        System.out.println("buscarPrimero(12345) en lista sin ese precio: " + iguales.buscarPrimero(12345));
 
        
        System.out.println("\n--- 3) Insercion de datos aleatorios (con muchos duplicados) ---");
        int[] tamanos = {10_000, 50_000, 100_000};
        for (int n : tamanos) {
            Random rnd = new Random(42);
            ListaPrecios lista = new ListaPrecios();
            long totalComparaciones = 0;
            long inicio = System.nanoTime();
            for (int i = 1; i <= n; i++) {
                lista.insertar(generarProducto(i, rnd));
                totalComparaciones += lista.getUltimasComparaciones();
            }
            double ms = (System.nanoTime() - inicio) / 1_000_000.0;
            System.out.printf("n = %,8d | ordenada: %b | tiempo total: %9.2f ms | comparaciones prom por insercion: %5.1f | log2(n) = %4.1f%n",
                    n, lista.estaOrdenada(), ms, totalComparaciones / (double) n,
                    Math.log(n) / Math.log(2));
        }
 
        
        System.out.println("\n--- 4) Verificacion contra fuerza bruta (10.000 consultas aleatorias) ---");
        Random rnd = new Random(7);
        ListaPrecios lista = new ListaPrecios();
        for (int i = 1; i <= 20_000; i++) {
            lista.insertar(generarProducto(i, rnd));
        }
        int errores = 0;
        for (int q = 0; q < 10_000; q++) {
            double precio = (rnd.nextInt(1100)) * 50.0;
            int esperado = lowerBoundLineal(lista, precio);
            if (lista.lowerBound(precio) != esperado) {
                errores++;
            }
        }
        System.out.println("Diferencias encontradas: " + errores + " (debe ser 0)");
 
        
        System.out.println("\n--- 5) Tiempo de lowerBound (binaria) vs recorrido lineal ---");
        int[] grandes = {100_000, 1_000_000, 5_000_000};
        for (int n : grandes) {
            List<Producto> datos = new ArrayList<>(n);
            Random r = new Random(1);
            for (int i = 1; i <= n; i++) {
                datos.add(generarProducto(i, r));
            }
            datos.sort(Comparator.comparingDouble(Producto::getPrecio));
            ListaPrecios grande = new ListaPrecios();
            grande.cargarOrdenada(datos);
 
            int consultas = 100;
            double[] objetivos = new double[consultas];
            for (int i = 0; i < consultas; i++) {
                objetivos[i] = r.nextInt(1100) * 50.0;
            }
 
            long t0 = System.nanoTime();
            long sumaComp = 0;
            long checksum1 = 0;
            for (double objetivo : objetivos) {
                checksum1 += grande.lowerBound(objetivo);
                sumaComp += grande.getUltimasComparaciones();
            }
            double msBinaria = (System.nanoTime() - t0) / 1_000_000.0;
 
            t0 = System.nanoTime();
            long checksum2 = 0;
            for (double objetivo : objetivos) {
                checksum2 += lowerBoundLineal(grande, objetivo);
            }
            double msLineal = (System.nanoTime() - t0) / 1_000_000.0;
 
            System.out.printf("n = %,9d | %d consultas | binaria: %9.3f ms (%.1f comp/consulta) | lineal: %10.3f ms | mismos resultados: %b%n",
                    n, consultas, msBinaria, sumaComp / (double) consultas, msLineal, checksum1 == checksum2);
        }
    }
 
    
    private static Producto generarProducto(int id, Random rnd) {
        double precio = rnd.nextInt(1100) * 50.0;
        return new Producto(id, NOMBRES[rnd.nextInt(NOMBRES.length)],
                CATEGORIAS[rnd.nextInt(CATEGORIAS.length)], precio);
    }
 
    
    private static int lowerBoundLineal(ListaPrecios lista, double precio) {
        for (int i = 0; i < lista.tamano(); i++) {
            if (lista.get(i).getPrecio() >= precio) {
                return i;
            }
        }
        return lista.tamano();
    }
}
