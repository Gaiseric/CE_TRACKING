package com.cositos.cetracking.Grafos;

public class Prueba {
    public static void main(String[] args) {
    GraphWeighted<String> grafo = new GraphWeighted<>(true);
    grafo.addEdge("Cartago", "San Jose", 1000);
    grafo.addEdge("Cartago", "Limon", 1200);
    grafo.addEdge("San Jose", "Puntarenas", 300);
    grafo.addEdge("San Jose", "Guanacaste", 400);
    grafo.addEdge("Guanacaste", "Cartago", 800);
    grafo.addEdge("Puntarenas", "US", 10);
    grafo.addEdge("US", "Rusia", 8000);
    grafo.addEdge("Colombia", "Mexico", 250);
    grafo.addEdge("Cartago", "Heredia", 500);
    grafo.addEdge("San Jose", "Rusia", 980);
    grafo.addEdge("Heredia", "Rusia", 745);
    grafo.addEdge("Puntarenas", "Rusia", 520);
    grafo.addEdge("Cartago", "Rusia", 69);
   //grafo.allPath("Cartago", "Rusia");
    grafo.print();
    }
}
