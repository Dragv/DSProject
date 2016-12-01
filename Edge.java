package Project;

public class Edge {
	private Vertex destination;
	private double cost;
	
	public Edge(Vertex destination, double cost){
		this.destination = destination;
		this.cost = cost;	
	}
	
	public Vertex getDestiny(){
		return this.destination;
	}
	public double getCost(){
		return this.cost;
	}
}
