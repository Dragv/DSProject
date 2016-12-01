package Project;

import java.util.LinkedList;

public class Vertex {
	public String name;
	public LinkedList<Edge> adjacency;
	public int marked;
	public double distance;
	public Vertex previous;
	
	public Vertex(String name){
		this.name = name;
		this.adjacency = new LinkedList<Edge>();
		this.restart();
	}
	
	public void restart(){
		this.marked = 0;
		this.distance = Graph.INFINITE;
		this.previous = null;
	}
}
