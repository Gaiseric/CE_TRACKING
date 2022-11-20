package com.cositos.cetracking.Grafos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class GraphWeighted<T> {
	ArrayList<Object> rutes = new ArrayList<>();
	Map<T, LinkedList<Edge<T>>> adj = new HashMap<>() ;
	boolean directed;
	// No. of vertices in graph
    private int v;
	
	//Constructor, Time O(1) Space O(1)
	public GraphWeighted () {
		directed = false; //default, Undirected graph
	}
	
	//Constructor, Time O(1) Space O(1)
	public GraphWeighted(boolean d) {
		directed = d;
	}

    //Add edges including adding nodes, Time O(1) Space O(1)
	public void addEdge(T a, T b, int w) {
		adj.putIfAbsent(a, new LinkedList<>()); //add node
		adj.putIfAbsent(b, new LinkedList<>()); //add node
		Edge<T> edge1 = new Edge<>(b, w);
		adj.get(a).add(edge1); //add edge
		this.v++;
		if (!directed) { //undirected
			Edge<T> edge2 = new Edge<>(a, w);
			adj.get(b).add(edge2);
		}			
	}

    //Find the edge between two nodes, Time O(n) Space O(1), n is number of neighbors 
	private Edge<T> findEdgeByVetex(T a, T b) {
		LinkedList<Edge<T>> ne = adj.get(a);
		for (Edge<T> edge: ne) {
			if (edge.connectedVetex.equals(b)) {
				return edge;
			}
		}
		return null;
	}
	
	//Remove direct connection between a and b, Time O(n) Space O(1)
	public void removeEdge(T a, T b) {
		LinkedList<Edge<T>> ne1 = adj.get(a);
		LinkedList<Edge<T>> ne2 = adj.get(b); 
		if (ne1 == null || ne2 == null)
			return;
		Edge<T> edge1 = findEdgeByVetex(a, b);
		ne1.remove(edge1);
		this.v--;
		if (!directed)  {//undirected
			Edge<T> edge2 = findEdgeByVetex(b, a);
			ne2.remove(edge2);
		}
	}
	
	//Remove a node including all its edges, Time O(V) Space O(1), V is number of vertics in graph
	public void removeNode(T v) {		
		if (!directed) { //undirected
			LinkedList<Edge<T>> ne1 = adj.get(v);
			for (Edge<T> edge: ne1) {	
				Edge<T> edge1 = findEdgeByVetex(edge.connectedVetex, v);
				adj.get(edge.connectedVetex).remove(edge1);
			}
		} else { //directed
			for (T key: adj.keySet()) {
				Edge<T> edge2 = findEdgeByVetex(key, v);
				if (edge2 != null)
					adj.get(key).remove(edge2);
			}
		}
		adj.remove(v);
	}

    //Check whether there is node by its key, Time O(1) Space O(1)
	public boolean hasNode(T key) {
		return adj.containsKey(key);
	}
	
	//Check whether there is direct connection between two nodes, Time O(n), Space O(1)
	public boolean hasEdge(T a, T b) {
		Edge<T> edge1 = findEdgeByVetex(a, b);
		if (directed) {//directed
			return edge1 != null;
		}
		else { //undirected or bi-directed
			Edge<T> edge2 = findEdgeByVetex(b, a);
			return edge1 != null && edge2!= null;
		}
	}

    //Print graph as hashmap, Time O(V+E), Space O(1)
	public void print() {
		for (T key: adj.keySet()) {
			//System.out.println(adj);
			LinkedList<Edge<T>> lista =  adj.get(key);
			int cantdatos= lista.size();
			if (cantdatos>=1) {
				Edge<T> actual;
				int dato= 0;
				System.out.println("Jeje:"+key+"\n");
				while(cantdatos>0) {
					actual= lista.get(dato);
					System.out.println(actual.connectedVetex);
					//System.out.println(actual.getWeight());
					dato++;
					cantdatos--;
				}
				System.out.println("\n");
			}
			//System.out.println(key + "," + adj.get(key));
		}
	}
	

	//Traversal starting from src, DFS, Time O(V+E), Space O(V)
	public void dfsTraversal(T src) {
		HashMap<T, Boolean> visited = new HashMap<>();
	    helper(src, visited);
	    System.out.println();
	}
	
	//DFS helper, Time O(V+E), Space O(V) 
	private void helper(T v, HashMap<T, Boolean> visited) {
	    visited.put(v, true);
	    System.out.print(v.toString() + " ");
	    for (Edge<T> edge : adj.get(v)) {
	    	T u = edge.connectedVetex;
	        if (visited.get(u) == null)
	            helper(u, visited);
	    }
	}


	// Prints all paths from
    // 's' to 'd'
    public void printAllPaths(T s, T d)
    {
        HashMap<T, Boolean> isVisited = new HashMap<>();
        ArrayList<String> pathList = new ArrayList<>();
		
        // add source to path[]
        pathList.add(""+s);
        // Call recursive utility
		rutes.clear();
        printAllPathsUtil(s, d, isVisited, pathList, 0);
    }
 
    // A recursive function to print
    // all paths from 'u' to 'd'.
    // isVisited[] keeps track of
    // vertices in current path.
    // localPathList<> stores actual
    // vertices in the current path
    private void printAllPathsUtil(T u, T d, HashMap<T, Boolean> isVisited, List<String> localPathList, int pathCost){
 
        if (u.equals(d)) {
			ArrayList<Object> newrute= new ArrayList<>();
			newrute.add(localPathList + "con un costo en colones de: " + (pathCost+300) + " con una duracion en segundos de: ");
			newrute.add(pathCost);
			rutes.add(newrute);
            System.out.println(localPathList+ " con un costo de: " + pathCost + "\n");
            // if match found then no need to traverse more till depth
            return;
        }
 
        // Mark the current node
        isVisited.put(u, true);
 
        // Recur for all the vertices
        // adjacent to current vertex
        for (Edge<T> i : adj.get(u)) {
			T cv = i.connectedVetex;
            if (isVisited.get(cv) == null) {
                // store current node
                // in path[]
                localPathList.add(""+cv);
				pathCost= pathCost + i.getWeight();
                printAllPathsUtil(cv, d, isVisited, localPathList, pathCost);
                // remove current node
                // in path[]
				pathCost= pathCost - i.getWeight();
                localPathList.remove(cv);
            }
        }
 
        // Mark the current node
        isVisited.remove(u, true);
    }

	public ArrayList<Object> getrutes() {
		return rutes;
	}

	//Check there is path from src and dest
	//DFS, Time O(V+E), Space O(V)
	public boolean hasPathDFS(T src, T dest) {
		HashMap<T, Boolean> visited = new HashMap<>();
	    return dfsHelper(src, dest, visited);
	}
	
	//DFS helper, Time O(V+E), Space O(V) 
	private boolean dfsHelper(T v, T dest, HashMap<T, Boolean> visited) {
		if (v == dest)
			return true;
	    visited.put(v, true);
	    for (Edge<T> edge : adj.get(v)) {
	    	T u = edge.connectedVetex;
	        if (visited.get(u) == null)
	            return dfsHelper(u, dest, visited);
	    }
	    return false;
	}

	//Check there is path from src and dest
	//BFS, Time O(V+E), Space O(V), V is number of vertices, E is number of edges
	public boolean hasPathBFS(T src, T dest) {
		if (!hasNode(src) || !hasNode(dest))
			return false;
		HashMap<T, Boolean> visited = new HashMap<>(); 
		Queue<T> q = new LinkedList<>();
		visited.put(src, true);
        q.offer(src);
        while (!q.isEmpty()) {
            T v = q.poll();
            if (v == dest) {
                return true;
            }
            for (Edge<T> edge: adj.get(v)) {
	           T u = edge.connectedVetex;		           
	           if (visited.get(u) ==null) {
	        	   visited.put(u, true);
                   q.offer(u);
               }	            
           }
        }     
        return false;
	}

}
