package com.cositos.cetracking.datos.graph;

import java.util.ArrayList;

import com.cositos.cetracking.Grafos.GraphWeighted;

public class graphgenerator {
    ArrayList<String> List= new ArrayList<>();
    public void Inicio() {
        String[] provincias = new String[]{"San Jose", "Alajuela", "Heredia", "Cartago", "Puntarenas", "Guanacaste", "Limon"};
        GraphWeighted<String> grafo = new GraphWeighted<>();
        int cantidad= (int) (Math.random() * 7) + 3;
        for (int j = 0; j < cantidad; j++) {
            if(j+1 == cantidad){
                grafo.addEdge(provincias[j], provincias[0], (int) (Math.random() * 500) + 20);
            } else{
                grafo.addEdge(provincias[j], provincias[j+1], (int) (Math.random() * 500) + 20);
            }
            List.add(provincias[j]);
        }
    }

    public ArrayList<String> getList() {
        return List;
    }
    
}
