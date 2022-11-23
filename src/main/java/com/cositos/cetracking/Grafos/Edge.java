package com.cositos.cetracking.Grafos;

/**
 * Edge is a class that represents a connection between two vertices
 */
class Edge<T> { 
	T connectedVetex; //connected vertex
	int weight; //weight
	
	// A constructor.
	public Edge(T v, int w) {
		this.connectedVetex = v; 
		this.weight = w;
	}
	
	/**
	 * The toString() method returns a string representation of the object
	 * 
	 * @return The connected vertex and the weight of the edge.
	 */
	@Override
	public String toString() {
		return "(" + connectedVetex + "," + weight + ")";
	}

	/**
	 * This function returns the weight of the object
	 * 
	 * @return The weight of the object.
	 */
	public int getWeight() {
		return this.weight;
	}
}

