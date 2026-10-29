/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lasalle.ejercicios.algoritmos.lineal;
import java.util.Arrays;
import java.util.Random;
/**
 *
 * @author Usuario
 */
public class Main {
    private static final String[] TEMAS = {
        "Error de login", "Caida del servidor", "Fallo en pagos", "Lentitud en la app",
        "Correo no llega", "Reporte incorrecto", "Error en base de datos", "Impresora sin conexion"
    };
 
    private static final int REPETICIONES = 50;
 
    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 1: BUSQUEDA LINEAL (primer ticket critico) ===\n");
 
        
        for (int i = 0; i < 5; i++) {
            ColaTickets calentar = generarCola(10_000, 0.05, new Random(i));
            calentar.primerCritico();
        }
 
        
        System.out.println("--- 1) Datos aleatorios (5% de tickets criticos) ---");
        int[] tamanos = {1_000, 10_000, 100_000, 1_000_000, 5_000_000};
        for (int n : tamanos) {
            ColaTickets cola = generarCola(n, 0.05, new Random(42));
            probar("n = " + n, cola);
        }
 
        
        System.out.println("\n--- 2) Casos limite (n = 1.000.000) ---");
        int n = 1_000_000;
 
        ColaTickets vacia = new ColaTickets();
        probar("Cola vacia", vacia);
 
        ColaTickets unoCritico = new ColaTickets();
        unoCritico.encolar(new Ticket(1, "Unico", Prioridad.CRITICA, 5));
        probar("Un solo ticket (critico)", unoCritico);
 
        ColaTickets unoNormal = new ColaTickets();
        unoNormal.encolar(new Ticket(1, "Unico", Prioridad.BAJA, 5));
        probar("Un solo ticket (no critico)", unoNormal);
 
        probar("Critico al INICIO (mejor caso)", colaConCriticoEn(n, 0));
        probar("Critico en el MEDIO", colaConCriticoEn(n, n / 2));
        probar("Critico al FINAL (peor caso)", colaConCriticoEn(n, n - 1));
        probar("SIN criticos (peor caso)", colaConCriticoEn(n, -1));
 
        
        System.out.println("\n--- 3) Verificacion: la busqueda no reordena la cola ---");
        ColaTickets cola = generarCola(100_000, 0.05, new Random(7));
        int[] antes = cola.idsEnOrden();
        cola.primerCritico();
        cola.primerCritico();
        int[] despues = cola.idsEnOrden();
        System.out.println("Orden igual antes y despues: " + Arrays.equals(antes, despues));
        System.out.println("Tamano igual antes y despues: " + (antes.length == despues.length));
 
        
        System.out.println("\n--- 4) Ejemplo pequeno ---");
        ColaTickets pequena = generarCola(8, 0.3, new Random(3));
        for (Ticket t : pequena.comoLista()) {
            System.out.println(t);
        }
        System.out.println("Primer critico -> " + pequena.primerCritico()
                + " (comparaciones: " + pequena.getUltimasComparaciones() + ")");
    }
 
    
    private static void probar(String etiqueta, ColaTickets cola) {
        Ticket resultado = null;
        long total = 0;
        for (int i = 0; i < REPETICIONES; i++) {
            long inicio = System.nanoTime();
            resultado = cola.primerCritico();
            total += System.nanoTime() - inicio;
        }
        double promedioMs = (total / (double) REPETICIONES) / 1_000_000.0;
        System.out.printf("%-34s | tamano: %,9d | comparaciones: %,9d | tiempo prom: %10.4f ms | resultado: %s%n",
                etiqueta, cola.tamano(), cola.getUltimasComparaciones(), promedioMs,
                resultado == null ? "no hay critico" : "id " + resultado.getId());
    }
 
    
    private static ColaTickets generarCola(int n, double probCritico, Random rnd) {
        ColaTickets cola = new ColaTickets();
        Prioridad[] noCriticas = {Prioridad.BAJA, Prioridad.MEDIA, Prioridad.ALTA};
        for (int i = 1; i <= n; i++) {
            Prioridad p = rnd.nextDouble() < probCritico
                    ? Prioridad.CRITICA
                    : noCriticas[rnd.nextInt(noCriticas.length)];
            cola.encolar(new Ticket(i, TEMAS[rnd.nextInt(TEMAS.length)], p, rnd.nextInt(600)));
        }
        return cola;
    }
 
    
    private static ColaTickets colaConCriticoEn(int n, int posicion) {
        Random rnd = new Random(99);
        ColaTickets cola = new ColaTickets();
        Prioridad[] noCriticas = {Prioridad.BAJA, Prioridad.MEDIA, Prioridad.ALTA};
        for (int i = 0; i < n; i++) {
            Prioridad p = (i == posicion) ? Prioridad.CRITICA : noCriticas[rnd.nextInt(noCriticas.length)];
            cola.encolar(new Ticket(i + 1, TEMAS[rnd.nextInt(TEMAS.length)], p, rnd.nextInt(600)));
        }
        return cola;
    }
}
