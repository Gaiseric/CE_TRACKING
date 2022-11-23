package com.cositos.cetracking.Grafos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * The class GraphWeighted is a generic class that represents a weighted graph
 */
public class GraphWeighted<T> {
	ArrayList<Object> rutes = new ArrayList<>();
	Map<T, LinkedList<Edge<T>>> adj = new HashMap<>() ;
	boolean directed;
	// No. of vertices in graph
    private int v;
	
	
	// A constructor.
	public GraphWeighted () {
		directed = false; //default, Undirected graph
	}
	
	// A constructor that takes a boolean as a parameter.
	public GraphWeighted(boolean d) {
		directed = d;
	}

	/**
	 * If the graph doesn't contain the nodes a and b, add them. Then add an edge from a to b with weight
	 * w. If the graph is undirected, add an edge from b to a with weight w
	 * 
	 * @param a the first node
	 * @param b the node to connect to
	 * @param w weight of the edge
	 */
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
 
	/**
	 * > Find the edge that connects two vertices
	 * 
	 * @param a the first vertex
	 * @param b the vertex to be connected to a
	 * @return The edge between two vertices.
	 */
	private Edge<T> findEdgeByVetex(T a, T b) {
		LinkedList<Edge<T>> ne = adj.get(a);
		if(ne==null){
			return null;
		}
		for (Edge<T> edge: ne) {
			if (edge.connectedVetex.equals(b)) {
				return edge;
			}
		}
		return null;
	}
	
	/**
	 * It removes an edge from the graph.
	 * 
	 * @param a the first vertex
	 * @param b the vertex that is the destination of the edge
	 */
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
	
	/**
	 * If the graph is undirected, remove all edges that connect to the node to be removed. If the graph
	 * is directed, remove all edges that point to the node to be removed
	 * 
	 * @param v the vertex to be removed
	 */
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

	/**
	 * This function returns true if the graph contains a node with the given key, and false otherwise
	 * 
	 * @param key The key of the node to check for.
	 * @return A boolean value.
	 */
	public boolean hasNode(T key) {
		return adj.containsKey(key);
	}
	
	/**
	 * If the graph is directed, then return true if there is an edge from a to b. If the graph is
	 * undirected, then return true if there is an edge from a to b or from b to a
	 * 
	 * @param a the first vertex
	 * @param b the vertex that the edge is connected to
	 */
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

	/**
	 * For each key in the adjacency list, print the key and the value associated with that key
	 */
	public void print() {
		for (T key: adj.keySet()) {
			System.out.println(key + "," + adj.get(key));
		}
	}

	/**
	 * We start at the source node, mark it as visited, and then recursively call the helper function on
	 * all of its unvisited neighbors
	 * 
	 * @param src The source node from which we want to start the traversal.
	 */
	public void dfsTraversal(T src) {
		HashMap<T, Boolean> visited = new HashMap<>();
	    helper(src, visited);
	    System.out.println();
	}
	 
	/**
	 * A helper function for the DFS function. It is a recursive function that traverses the graph.
	 * 
	 * @param v the vertex we are currently visiting
	 * @param visited a hashmap that keeps track of the visited vertices
	 */
	private void helper(T v, HashMap<T, Boolean> visited) {
	    visited.put(v, true);
	    System.out.print(v.toString() + " ");
	    for (Edge<T> edge : adj.get(v)) {
	    	T u = edge.connectedVetex;
	        if (visited.get(u) == null)
	            helper(u, visited);
	    }
	}

    /**
	 * It prints all the paths from source to destination.
	 * 
	 * @param s source node
	 * @param d destination
	 */
	public void printAllPaths(T s, T d) {
        HashMap<T, Boolean> isVisited = new HashMap<>();
        ArrayList<String> pathList = new ArrayList<>();
		
        // add source to path[]
        pathList.add(""+s);
        // Call recursive utility
		rutes.clear();
        printAllPathsUtil(s, d, isVisited, pathList, 0);
    }
 
    /**
	 * We start from the source vertex and explore all paths from the source to the destination vertex.
	 * 
	 * 
	 * The function printAllPathsUtil() does the actual work. It takes the current vertex u, the
	 * destination vertex d, a boolean array isVisited[] to keep track of vertices in current path, a
	 * list localPathList to store the current path, and an integer pathCost to store the cost of the
	 * current path. 
	 * 
	 * If the current vertex is equal to the destination vertex, then we print the current path and
	 * return. 
	 * 
	 * Otherwise, we mark the current vertex as visited and recur for all the vertices adjacent to the
	 * current vertex. 
	 * 
	 * When we are done exploring all paths from the current vertex, we mark the current vertex as
	 * unvisited to make it usable in other paths. 
	 * 
	 * The function printAllPaths() simply initializes the data structures and calls
	 * printAllPathsUtil().
	 * 
	 * @param u The current node
	 * @param d destination vertex
	 * @param isVisited A HashMap that keeps track of the vertices that have been visited.
	 * @param localPathList This is the list of vertices that are in the current path.
	 * @param pathCost The cost of the path from the source to the current vertex.
	 */
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

	/**
	 * This function returns an ArrayList of Objects
	 * 
	 * @return An ArrayList of Objects.
	 */
	public ArrayList<Object> getrutes() {
		return rutes;
	}

	/**
	 * It checks if there is a path from src to dest.
	 * 
	 * @param src The source node
	 * @param dest The destination node
	 * @return A boolean value.
	 */
	public boolean hasPathDFS(T src, T dest) {
		HashMap<T, Boolean> visited = new HashMap<>();
	    return dfsHelper(src, dest, visited);
	}
	
	/**
	 * If the current vertex is the destination, return true. Otherwise, mark the current vertex as
	 * visited and recursively call the function on all of its neighbors
	 * 
	 * @param v the current vertex
	 * @param dest the destination vertex
	 * @param visited a hashmap that keeps track of the visited vertices
	 * @return The return value is a boolean value.
	 */
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

	/**
	 * We start from the source vertex and visit all the vertices connected to it. We keep on visiting the
	 * vertices connected to the vertices which are already visited. We keep on doing this until we reach
	 * the destination vertex or we have visited all the vertices
	 * 
	 * @param src The source vertex
	 * @param dest The destination node
	 * @return The method returns true if there is a path from src to dest, and false otherwise.
	 */
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
