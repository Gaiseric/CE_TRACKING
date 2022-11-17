package com.cositos.cetracking.Grafos;

class Edge<T> { 
	T connectedVetex; //connected vertex
	int weight; //weight
	
	//Constructor, Time O(1) Space O(1)
	public Edge(T v, int w) {
		this.connectedVetex = v; 
		this.weight = w;
	}
	
	//Time O(1) Space O(1)
	@Override
	public String toString() {
		return "(" + connectedVetex + "," + weight + ")";
	}

	public int getWeight() {
		return this.weight;
	}
}

