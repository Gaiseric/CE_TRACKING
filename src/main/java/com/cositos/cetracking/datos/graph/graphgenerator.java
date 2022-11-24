package com.cositos.cetracking.datos.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.cositos.cetracking.Grafos.GraphWeighted;
import com.cositos.cetracking.ListaEnlazadas.Linked_List;
import com.cositos.cetracking.datos.info.Distributions;

/**
 * It generates a random graph with random weights and random nodes.
 */
public class graphgenerator {
    static ArrayList<String> List= new ArrayList<>();
    static ArrayList<Distributions> Distributions = new ArrayList<>();
    static GraphWeighted<String> grafo = new GraphWeighted<>();
    static Linked_List registrator= new Linked_List();

    /**
     * It creates a graph with random edges and vertices
     */
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
            if(!contains(Distribution1, Connected1)){
                distribution1.setdistribution(Distribution1);
                distribution1.setconnectedto(Connected1);
                distribution1.setcost(time1);
                grafo.addEdge(Distribution1, Connected1, time1);
                Distributions.add(distribution1);
                verificator(registrator, Distribution1);
                verificator(registrator, Connected1);
                provincias.remove(dis1);
            }
            Distributions distribution2 = new Distributions();
            int dis2= rand.nextInt(paises.size());
            int con2= rand.nextInt(provinciascopy.size());
            String Distribution2= paises.get(dis2);
            String Connected2= provinciascopy.get(con2);
            int time2= randomint(10, 3);
            if(!contains(Distribution2, Connected2)){
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
    }

    /**
     * It returns true if the graph has an edge from the node named dis to the node named con
     * 
     * @param dis the name of the node that is the source of the edge
     * @param con the node that is being connected to
     * @return The method returns a boolean value.
     */
    public static boolean contains(String dis, String con){
        return grafo.hasEdge(dis, con);
    }

    /**
     * This function takes a linked list and a string as parameters, and if the string is not already
     * in the linked list, it adds it to the linked list and to a list
     * 
     * @param registrator A Linked_List object that is used to check if the data is unique.
     * @param data The data that is being inserted into the linked list.
     */
    private static void verificator(Linked_List registrator, String data){
        if(registrator.InsertLastUnique(data)){
            List.add(data);
        }
    }

    /**
     * It takes two integers, `max` and `min`, and returns a random integer between `min` and `max`
     * (inclusive)
     * 
     * @param max The maximum number that can be generated.
     * @param min The minimum number that can be generated.
     */
    private static int randomint(int max, int min){
        if (max<=min){
            throw new IllegalArgumentException("El maximo tiene que ser un numero mayor al minomo");
        } 
        Random r= new Random();
        return r.nextInt((max-min)+1) + min ;
        
    }

    /**
     * This function returns the list of strings
     * 
     * @return The ArrayList List is being returned.
     */
    public ArrayList<String> getList() {
        return List;
    }
    
    /**
     * This function returns an ArrayList of Distributions
     * 
     * @return The Distributions ArrayList is being returned.
     */
    public ArrayList<Distributions> getDistributions() {
        return Distributions;
    }

    /**
     * It returns a linked list of all the registered users
     * 
     * @return The registrator object is being returned.
     */
    public Linked_List getLinked(){
        registrator.displayList();
        return registrator;
    }

    /**
     * It removes an edge from the graph and then adds it again with the new weight
     * 
     * @param dis The name of the node that will be the source of the edge.
     * @param con is the name of the node that is connected to the node dis.
     * @param w weight of the edge
     */
    public static void Modify(String dis, String con, int w) {
        grafo.removeEdge(dis, con);
        grafo.addEdge(dis, con, w);
    }

    /**
     * It removes an edge from the graph
     * 
     * @param dis The name of the node that will be the source of the edge.
     * @param con is the name of the node that will be connected to the node dis.
     */
    public static void EliminateEdge(String dis, String con) {
        grafo.removeEdge(dis, con);
    }

    /**
     * It gets all the possible routes from a source to a destination, and if there's more than one
     * route, it sorts them by the number of stops
     * 
     * @param src source node
     * @param dst destination
     * @return An ArrayList of Objects.
     */
    public static ArrayList<Object> getrutes(String src, String dst){
        grafo.printAllPaths(src, dst);
        ArrayList<Object> rutes = grafo.getrutes();
        if(rutes.size()>1){
            startQuickStart(0, rutes.size()-1, rutes);
            return rutes;
        } else{
            return grafo.getrutes();
        }
    }

 
    /**
     * If the start is less than the end, then the function will partition the array and then call
     * itself with the new start and end values
     * 
     * @param start the start of the array
     * @param end the last index of the array
     * @param rutes ArrayList of objects
     */
    private static void startQuickStart(int start,int end, ArrayList<Object> rutes){
        int q;
        if(start<end){
            q = partition(start, end, rutes);
            startQuickStart(start, q, rutes);
            startQuickStart(q+1, end, rutes);
        }
    }

    /**
     * It takes an array of objects, and sorts them by the second element in each object
     * 
     * @param start the start index of the array
     * @param end the last index of the array
     * @param inputArray The array to be sorted
     * @return The index of the pivot element.
     */
    @SuppressWarnings("unchecked")
    private static int partition(int start,int end, ArrayList<Object> inputArray){
        int init = start;
        int length = end;
        
        Random r = new Random();
        int pivotIndex = nextIntInRange(start,end,r);
        int pivot = (int) ((ArrayList<Object>) inputArray.get(pivotIndex)).get(1);

        while(true){
            // Checking if the second element in the object is greater than the pivot, and if it is,
            // then it decreases the length by one.
            while((int) ((ArrayList<Object>) inputArray.get(length)).get(1)>pivot && length>start){
                length--;
            }

            // Checking if the second element in the object is less than the pivot, and if it is, then
            // it increases the length by one.
            while((int) ((ArrayList<Object>) inputArray.get(init)).get(1)<pivot && init<end){
                init++;
            }

            // Swapping the elements in the array.
            if(init<length){
                ArrayList<Object> temp;
                temp = ((ArrayList<Object>) inputArray.get(init));
                inputArray.set(init, inputArray.get(length));
                inputArray.set(length,temp);
                length--;
                init++;
            }else{
                return length;
            }
        }
        
    }
    
    // Below method is to just find random integer from given range
   /**
    * If the difference between the min and max is less than the maximum value of an integer, then
    * return a random number between min and max. Otherwise, keep generating random numbers until one
    * is found that is between min and max
    * 
    * @param min The minimum value that can be generated.
    * @param max The maximum value that can be generated.
    * @param rng The random number generator to use.
    * @return The method returns a random integer between the min and max values.
    */
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
