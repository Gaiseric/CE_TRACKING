package com.cositos.cetracking.datos.graph;

import java.util.ArrayList;

import com.cositos.cetracking.Grafos.GraphWeighted;
import com.cositos.cetracking.datos.info.Distributions;

public class graphgenerator {
    ArrayList<String> List= new ArrayList<>();
    ArrayList<Distributions> Distributions = new ArrayList<>();
    public void Inicio() {
        String[] provincias = new String[]{"San Jose", "Alajuela", "Heredia", "Cartago", "Puntarenas", "Guanacaste", "Limon"};
        GraphWeighted<String> grafo = new GraphWeighted<>();
        int cantidad= (int) (Math.random() * 7) + 3;
        if (cantidad>7){
            while(cantidad!=7){
                cantidad--;
            }
        }
        for (int j = 0; j < cantidad; j++) {
            System.out.println(cantidad);
            Distributions dis = new Distributions();
            if(j+1 == cantidad){
                String Connected= provincias[0];
                int cost= (int) (Math.random() * 500) + 20;
                dis.setdistribution(provincias[j]);
                dis.setconnectedto(Connected);
                dis.setcost(cost);
                grafo.addEdge(provincias[j], Connected, cost);
            } else{
                String Connected= provincias[j+1];
                int cost= (int) (Math.random() * 500) + 20;
                dis.setdistribution(provincias[j]);
                dis.setconnectedto(Connected);
                dis.setcost(cost);
                grafo.addEdge(provincias[j], Connected, cost);
            }
            Distributions.add(dis);
            List.add(provincias[j]);
        }
    }

    public ArrayList<String> getList() {
        return List;
    }
    
    public ArrayList<Distributions> getDistributions() {
        return Distributions;
    }
}
