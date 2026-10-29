/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lasalle.ejercicios.algotimos.interpolacion;
import java.util.Random;
/**
 *
 * @author Usuario
 */
public class Main {
    private static final String[] SENSORES = {
        "Temperatura", "Humedad", "Presion", "Luz", "Viento", "CO2"
    };
 
    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 4: BUSQUEDA POR INTERPOLACION (ids uniformes y repetidos) ===\n");
 
        
        System.out.println("--- 1) Ejemplo pequeno ---");
        int[] ids = {10, 20, 20, 20, 30, 40, 40, 50, 60, 70, 70, 80};
        Lectura[] pequena = new Lectura[ids.length];
        Random r0 = new Random(1);
        for (int i = 0; i < ids.length; i++) {
            pequena[i] = new Lectura(ids[i], SENSORES[r0.nextInt(SENSORES.length)],
                    redondear(r0.nextDouble() * 100), r0.nextInt(86400));
        }
        SerieLecturas serieP = new SerieLecturas(pequena);
        System.out.print("ids: ");
        for (Lectura l : pequena) {
            System.out.print(l.getId() + " ");
        }
        System.out.println();
        int[] consultasP = {20, 40, 70, 10, 80, 50, 25, 5, 99};
        for (int c : consultasP) {
            System.out.println("buscar(" + c + ") -> " + serieP.buscar(c));
        }
 
        
        System.out.println("\n--- 2) Casos limite ---");
        SerieLecturas vacia = new SerieLecturas(new Lectura[0]);
        System.out.println("Serie vacia:                 " + vacia.buscar(5));
 
        SerieLecturas una = new SerieLecturas(new Lectura[]{new Lectura(7, "Luz", 1.5, 100)});
        System.out.println("Un elemento, existe (7):     " + una.buscar(7));
        System.out.println("Un elemento, menor (3):      " + una.buscar(3));
        System.out.println("Un elemento, mayor (9):      " + una.buscar(9));
 
        Lectura[] iguales = new Lectura[100_000];
        for (int i = 0; i < iguales.length; i++) {
            iguales[i] = new Lectura(500, "Luz", i, i);
        }
        SerieLecturas todasIguales = new SerieLecturas(iguales);
        System.out.println("100.000 ids iguales (500):   " + todasIguales.buscar(500));
        System.out.println("100.000 ids iguales (501):   " + todasIguales.buscar(501));
 
        Lectura[] bordes = new Lectura[1000];
        for (int i = 0; i < bordes.length; i++) {
            int id = (i < 300) ? 1 : (i >= 700 ? 9 : 5);
            bordes[i] = new Lectura(id, "Luz", i, i);
        }
        SerieLecturas serieBordes = new SerieLecturas(bordes);
        System.out.println("Repetidos al inicio (id 1):  " + serieBordes.buscar(1) + " (esperado 0 a 299)");
        System.out.println("Repetidos al final (id 9):   " + serieBordes.buscar(9) + " (esperado 700 a 999)");
        System.out.println("Id inexistente entre (7):    " + serieBordes.buscar(7));
 
        
        System.out.println("\n--- 3) Ids UNIFORMES con repetidos (5.000 consultas: mitad existen, mitad no) ---");
        int[] tamanos = {10_000, 100_000, 1_000_000, 2_000_000};
        for (int n : tamanos) {
            Lectura[] datos = generarUniforme(n, new Random(42));
            probar(new SerieLecturas(datos), datos, 5_000, "uniforme n = " + String.format("%,d", n));
        }
 
        
        System.out.println("\n--- 4) Ids NO uniformes (crecen exponencialmente): la interpolacion se degrada ---");
        for (int n : new int[]{10_000, 100_000, 1_000_000}) {
            Lectura[] datos = generarExponencial(n, new Random(42));
            probar(new SerieLecturas(datos), datos, 5_000, "no uniforme n = " + String.format("%,d", n));
        }
    }
 
    
    private static void probar(SerieLecturas serie, Lectura[] datos, int consultas, String etiqueta) {
        Random rnd = new Random(123);
        int n = datos.length;
        int minId = datos[0].getId();
        int maxId = datos[n - 1].getId();
        int[] objetivos = new int[consultas];
        for (int i = 0; i < consultas; i++) {
            if (i % 2 == 0) {
                objetivos[i] = datos[rnd.nextInt(n)].getId();         // existe
            } else {
                objetivos[i] = minId + rnd.nextInt(Math.max(1, maxId - minId)); // al azar
            }
        }
 
        for (int i = 0; i < Math.min(consultas, 500); i++) { // calentamiento
            serie.buscar(objetivos[i]);
        }
 
        ResultadoInterpolacion[] res = new ResultadoInterpolacion[consultas];
        long inicio = System.nanoTime();
        for (int i = 0; i < consultas; i++) {
            res[i] = serie.buscar(objetivos[i]);
        }
        double nsPorConsulta = (System.nanoTime() - inicio) / (double) consultas;
 
        long totalSondas = 0;
        long totalBinaria = 0;
        int errores = 0;
        int[] conteoBinaria = {0};
        for (int i = 0; i < consultas; i++) {
            totalSondas += res[i].getSondas();
            conteoBinaria[0] = 0;
            int primera = lowerBoundBinaria(datos, objetivos[i], conteoBinaria);
            totalBinaria += conteoBinaria[0];
            boolean existe = primera < n && datos[primera].getId() == objetivos[i];
            if (existe != res[i].isEncontrado()) {
                errores++;
            } else if (existe) {
                int ultimaEsperada = primera;
                while (ultimaEsperada + 1 < n && datos[ultimaEsperada + 1].getId() == objetivos[i]) {
                    ultimaEsperada++;
                }
                if (res[i].getPrimera() != primera || res[i].getUltima() != ultimaEsperada) {
                    errores++;
                }
            }
        }
        System.out.printf("%-26s | sondas interp. prom: %8.1f | sondas binaria prom: %5.1f | tiempo/consulta: %9.0f ns | errores: %d%n",
                etiqueta, totalSondas / (double) consultas, totalBinaria / (double) consultas,
                nsPorConsulta, errores);
    }
 
    
    private static int lowerBoundBinaria(Lectura[] datos, int id, int[] sondas) {
        int izq = 0;
        int der = datos.length;
        while (izq < der) {
            int medio = izq + (der - izq) / 2;
            sondas[0]++;
            if (datos[medio].getId() < id) {
                izq = medio + 1;
            } else {
                der = medio;
            }
        }
        return izq;
    }
 
    
    private static Lectura[] generarUniforme(int n, Random rnd) {
        Lectura[] datos = new Lectura[n];
        int id = 1000;
        for (int i = 0; i < n; i++) {
            id += rnd.nextInt(5);
            datos[i] = nueva(id, rnd);
        }
        return datos;
    }
 
    
    private static Lectura[] generarExponencial(int n, Random rnd) {
        Lectura[] datos = new Lectura[n];
        double factor = Math.log(1_000_000_000.0) / n;
        for (int i = 0; i < n; i++) {
            datos[i] = nueva((int) Math.exp(i * factor), rnd);
        }
        return datos;
    }
 
    private static Lectura nueva(int id, Random rnd) {
        return new Lectura(id, SENSORES[rnd.nextInt(SENSORES.length)],
                redondear(rnd.nextDouble() * 100), rnd.nextInt(86400));
    }
 
    private static double redondear(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
