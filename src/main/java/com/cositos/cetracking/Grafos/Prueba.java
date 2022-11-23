package com.cositos.cetracking.Grafos;

import java.util.Random;

public class Prueba {
    
    /** 
     * @param args
     */
    public  static void main(String[] args) {
    GraphWeighted<String> grafo = new GraphWeighted<>();
    grafo.addEdge("San Jose", "Alajuela", 1000);
    grafo.addEdge("Alajuela", "Heredia", 1200);

    grafo.addEdge("Heredia", "Cartago", 300);
    grafo.addEdge("Cartago", "San Jose", 400);
    System.out.println(grafo.hasEdge("Cartago", "San Jose"));
    System.out.println(grafo.hasEdge("San Jose", "Cartago"));

    randomint(7, 3);

    
    }


    
    /** 
     * @param max
     * @param min
     * @return int
     */
    public static int bug(int max, int min) {
        Random r= new Random();
        return r.nextInt((max-min)+1) + min ;
    }

    
    /** 
     * @param max
     * @param min
     * @return int
     */
    private static int randomint(int max, int min){
        if (max<=min){
            throw new IllegalArgumentException("El maximo tiene que ser un numero mayor al minomo");
        } 
        Random r= new Random();
        return r.nextInt((max-min)+1) + min ;
        
    }
}
