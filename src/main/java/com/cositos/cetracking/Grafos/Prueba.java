package com.cositos.cetracking.Grafos;

import java.util.Random;

public class Prueba {
    public  static void main(String[] args) {
    /*GraphWeighted<String> grafo = new GraphWeighted<>();
    grafo.addEdge("San Jose", "Alajuela", 1000);
    grafo.addEdge("Alajuela", "Heredia", 1200);
    grafo.addEdge("Heredia", "Cartago", 300);
    grafo.addEdge("Cartago", "San Jose", 400);
    grafo.printAllPaths("Alajuela", "Heredia");
    //grafo.print();*/

    
    for (int i = 0; i < 20; i++) {
        System.out.println(randomint(7,3));
    }
    
    }


    public static int bug(int max, int min) {
        Random r= new Random();
        return r.nextInt((max-min)+1) + min ;
    }

    private static int randomint(int max, int min){
        if (max<=min){
            throw new IllegalArgumentException("El maximo tiene que ser un numero mayor al minomo");
        } 
        Random r= new Random();
        return r.nextInt((max-min)+1) + min ;
        
    }
}
