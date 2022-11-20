package com.cositos.cetracking.datos.graph;

import java.util.ArrayList;

import com.cositos.cetracking.Grafos.GraphWeighted;
import com.cositos.cetracking.datos.info.Distributions;

public class graphgenerator {
    static ArrayList<String> List= new ArrayList<>();
    static ArrayList<Distributions> Distributions = new ArrayList<>();
    static GraphWeighted<String> grafo = new GraphWeighted<>();
    public static void Inicio() {
        String[] provincias = new String[]{"San Jose", "Alajuela", "Heredia", "Cartago", "Puntarenas", "Guanacaste", "Limon"};
        int cantidad= (int) (Math.random() * 7) + 3;
        List.clear();
        if (cantidad>7){
            while(cantidad!=6){
                cantidad--;
            }
        }
        grafo = new GraphWeighted<>();
        for (int j = 0; j < cantidad; j++) {
            Distributions dis = new Distributions();
            if(j+1 == cantidad){
                String Connected= provincias[0];
                int cost= (int) (Math.random() * 11) + 1;
                dis.setdistribution(provincias[j]);
                dis.setconnectedto(Connected);
                dis.setcost(cost);
                grafo.addEdge(provincias[j], Connected, cost);
            } else{
                String Connected= provincias[j+1];
                int cost= (int) (Math.random() * 11) + 1;
                dis.setdistribution(provincias[j]);
                dis.setconnectedto(Connected);
                dis.setcost(cost);
                grafo.addEdge(provincias[j], Connected, cost);
            }
            Distributions.add(dis);
            List.add(provincias[j]);
        }
    }

    public static ArrayList<String> getList() {
        return List;
    }
    
    public ArrayList<Distributions> getDistributions() {
        return Distributions;
    }

    public static ArrayList<Object> getrutes(String src, String dst){
        grafo.printAllPaths(src, dst);
        return grafo.getrutes();
    }
}
