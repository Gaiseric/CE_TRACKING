package com.cositos.cetracking.Grafos;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class GraphWeighted<T> {
	Map<T, LinkedList<Edge<T>>> adj = new HashMap<>() ;
	boolean directed;
	
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
			System.out.println(key + "," + adj.get(key));
		}
	}
}
