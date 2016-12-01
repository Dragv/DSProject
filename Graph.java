package Project;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Graph {
	private Map<String, Vertex> graph;
	public static final double INFINITE = Double.MAX_VALUE;

	public Graph() {
		this.graph = new HashMap<String, Vertex>();
	}

	private Vertex getVertex(String name) {
		if (this.graph.containsKey(name)) {
			return this.graph.get(name);
		} else {
			Vertex newVertex = new Vertex(name);
			this.graph.put(name, newVertex);
			return newVertex;
		}
	}

	public void addEdge(String origin, String destiny, double cost) {
		Vertex v = this.getVertex(origin);
		Vertex w = this.getVertex(destiny);
		v.adjacency.add(new Edge(w, cost));
	}

	public void restartAll() {
		for (Vertex x : this.graph.values()) {
			x.restart();
		}
	}

	public String breathFirstSearch(String origin) {
		this.restartAll();
		Queue<Vertex> queue = new ConcurrentLinkedQueue<Vertex>();
		Vertex s = this.getVertex(origin);
		String path = "-";
		s.marked = 1;
		s.distance = 0;
		s.previous = null;
		queue.add(s);
		while (!queue.isEmpty()) {
			Vertex u = queue.remove();
			for (Edge e : u.adjacency) {
				Vertex v = e.getDestiny();
				if (v.marked == 0) {
					v.marked = 1;
					v.distance = u.distance + 1;
					v.previous = u;
					queue.add(v);
				}
			}
			u.marked = 2;
			path += u.name + "-";
		}
		return path;
	}

	public String depthFirstSearch(String origin) {
		this.restartAll();
		int time = 0;
		StringBuilder path = new StringBuilder();
		this.depthFirstSearchVisit(time, this.getVertex(origin), path);
		return path.toString();
	}

	public int depthFirstSearchVisit(int time, Vertex u, StringBuilder path) {
		time += 1;
		u.distance = time;
		u.marked = 1;
		for (Edge e : u.adjacency) {
			Vertex v = e.getDestiny();
			if (v.marked == 0) {
				v.previous = u;
				time = this.depthFirstSearchVisit(time, v, path);
			}
		}
		u.marked = 2;
		time = time + 1;
		path.append(u.name + "-");
		return time;
	}

	public static void main(String[] args) {
		Graph grafo = new Graph();
		grafo.addEdge("s", "a", 1);
		grafo.addEdge("a", "s", 1);

		grafo.addEdge("s", "b", 1);
		grafo.addEdge("b", "s", 1);

		grafo.addEdge("s", "c", 1);
		grafo.addEdge("c", "s", 1);

		grafo.addEdge("a", "d", 1);
		grafo.addEdge("d", "a", 1);

		grafo.addEdge("b", "d", 1);
		grafo.addEdge("d", "b", 1);

		grafo.addEdge("c", "d", 1);
		grafo.addEdge("d", "c", 1);

		System.out.println(grafo.breathFirstSearch("s"));
		System.out.println(grafo.depthFirstSearch("s"));
	}
}
