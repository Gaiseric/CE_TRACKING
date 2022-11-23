package com.cositos.cetracking.datos.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.cositos.cetracking.Grafos.GraphWeighted;
import com.cositos.cetracking.ListaEnlazadas.Linked_List;
import com.cositos.cetracking.datos.info.Distributions;

public class graphgenerator {
    static ArrayList<String> List= new ArrayList<>();
    static ArrayList<Distributions> Distributions = new ArrayList<>();
    static GraphWeighted<String> grafo = new GraphWeighted<>();
    static Linked_List registrator= new Linked_List();

    public static void Inicio() {
        ArrayList<String> provincias = new ArrayList<>(Arrays.asList("San Jose", "Alajuela", "Heredia", "Cartago", "Puntarenas", "Guanacaste", "Limon"));
        ArrayList<String> paises= new ArrayList<>(Arrays.asList("US", "Mexico", "Dubai", "Monaco", "Francia", "Alemania", "Canada"));
        ArrayList<String> provinciascopy = new ArrayList<>(provincias);
        ArrayList<String> paisescopy= new ArrayList<>(paises);
        int cantidad= randomint(7, 3);
        List.clear();
        grafo = new GraphWeighted<>();
        Random rand = new Random(); 
        for (int j = 0; j < cantidad; j++) {
            Distributions distribution1 = new Distributions();
            int dis1= rand.nextInt(provincias.size());
            int con1= rand.nextInt(paisescopy.size());
            String Distribution1= provincias.get(dis1);
            String Connected1= paisescopy.get(con1);
            int time1= randomint(10, 3);
            distribution1.setdistribution(Distribution1);
            distribution1.setconnectedto(Connected1);
            distribution1.setcost(time1);
            grafo.addEdge(Distribution1, Connected1, time1);
            Distributions.add(distribution1);
            verificator(registrator, Distribution1);
            verificator(registrator, Connected1);
            provincias.remove(dis1);

            Distributions distribution2 = new Distributions();
            int dis2= rand.nextInt(paises.size());
            int con2= rand.nextInt(provinciascopy.size());
            String Distribution2= paises.get(dis2);
            String Connected2= provinciascopy.get(con2);
            int time2= randomint(10, 3);
            distribution2.setdistribution(Distribution2);
            distribution2.setconnectedto(Connected2);
            distribution2.setcost(time2);
            grafo.addEdge(Distribution2, Connected2, time2);
            Distributions.add(distribution2);
            verificator(registrator, Distribution2);
            verificator(registrator, Connected2);
            paises.remove(dis2);
            
        }
    }

    private static void verificator(Linked_List registrator, String data){
        if(registrator.InsertLastUnique(data)){
            List.add(data);
        }
    }

    private static int randomint(int max, int min){
        if (max<=min){
            throw new IllegalArgumentException("El maximo tiene que ser un numero mayor al minomo");
        } 
        Random r= new Random();
        return r.nextInt((max-min)+1) + min ;
        
    }

    public ArrayList<String> getList() {
        return List;
    }
    
    public ArrayList<Distributions> getDistributions() {
        return Distributions;
    }

    public Linked_List getLinked(){
        registrator.displayList();
        return registrator;
    }

    public static void Modify(String dis, String con, int w) {
        grafo.removeEdge(dis, con);
        grafo.addEdge(dis, con, w);
    }

    public static void EliminateEdge(String dis, String con) {
        grafo.removeEdge(dis, con);
    }

    public static ArrayList<Object> getrutes(String src, String dst){
        grafo.printAllPaths(src, dst);
        ArrayList<Object> rutes = grafo.getrutes();
        if(rutes.size()>1){
            System.out.println(rutes);
            startQuickStart(0, rutes.size()-1, rutes);
            return rutes;
        } else{
            return grafo.getrutes();
        }
    }

 
    private static void startQuickStart(int start,int end, ArrayList<Object> rutes){
        int q;
        if(start<end){
            q = partition(start, end, rutes);
            startQuickStart(start, q, rutes);
            startQuickStart(q+1, end, rutes);
        }
    }

    @SuppressWarnings("unchecked")
    private static int partition(int start,int end, ArrayList<Object> inputArray){
        int init = start;
        int length = end;
        
        Random r = new Random();
        int pivotIndex = nextIntInRange(start,end,r);
        int pivot = (int) ((ArrayList<Object>) inputArray.get(pivotIndex)).get(1);

        while(true){
            while((int) ((ArrayList<Object>) inputArray.get(length)).get(1)>pivot && length>start){
                length--;
            }

            while((int) ((ArrayList<Object>) inputArray.get(init)).get(1)<pivot && init<end){
                init++;
            }

            if(init<length){
                ArrayList<Object> temp;
                temp = ((ArrayList<Object>) inputArray.get(init));
                inputArray.set(init, inputArray.get(length));
                inputArray.set(length,temp);
                System.out.println(inputArray);
                length--;
                init++;
            }else{
                return length;
            }
        }
        
    }
    
    // Below method is to just find random integer from given range
    private static int nextIntInRange(int min, int max, Random rng) {
        if (min > max) {
           throw new IllegalArgumentException("Cannot draw random int from invalid range [" + min + ", " + max + "].");
        }
        int diff = max - min;
        if (diff >= 0 && diff != Integer.MAX_VALUE) {
           return (min + rng.nextInt(diff + 1));
        }
        int i;
        do {
           i = rng.nextInt();
        } while (i < min || i > max);
        return i;
    }
}
