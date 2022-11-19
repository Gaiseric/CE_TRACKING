package com.cositos.cetracking.Grafos;

public class Prueba {
    public static void main(String[] args) {
    GraphWeighted<String> grafo = new GraphWeighted<>();
    grafo.addEdge("San Jose", "Alajuela", 1000);
    grafo.addEdge("Alajuela", "Heredia", 1200);
    grafo.addEdge("Heredia", "Cartago", 300);
    grafo.addEdge("Cartago", "San Jose", 400);
    grafo.printAllPaths("Alajuela", "Heredia");
    //grafo.print();
    }
}
