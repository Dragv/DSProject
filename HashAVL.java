package Project;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class HashAVL<K extends Comparable<K>, V extends Comparable<V>> {
	protected int size; 		// Table size
	protected int elements; 	// Number of elements
	protected AVLTree<V>[] tabla;
	protected final static int INIT_CAP = 101;

	@SuppressWarnings("unchecked")
	public HashAVL(int size, int carga) {
		this.size = size;
		this.elements = 0;
		this.tabla = (AVLTree<V>[]) new AVLTree[size];
	}

	public HashAVL() {
		this(INIT_CAP, 48);
	}

	public int hash(K key) {
		return key.hashCode() & 0x7FFFFFF % this.size;
	}

	public int hash(K key, int size) {
		return key.hashCode() & 0x7FFFFFF % size;
	}

	public int size() {
		return elements;
	}

	public boolean isEmpty() {
		return this.elements == 0;
	}

	public V get(K key, V value) {
		if (key == null) {
			throw new NullPointerException("Invalid key. Key is null");
		}
		int pos = hash(key);
		return tabla[pos].get(value);
	}

	public boolean contains(K key, V value) {
		return get(key, value) == null;
	}

	public void add(K key, V value) {
		if (key == null || value == null) {
			throw new IllegalArgumentException();
		}
		int pos = hash(key);
		if (tabla[pos] == null){
			tabla[pos] = new AVLTree(value);
		}
		tabla[pos].insert(value);
		this.elements++;
	}

	public V remove(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		int pos = hash(key);
		return tabla[pos].remove(value).getElement();
	}
	
	/*public static void main(String[] args) {
		HashAVL<String, Integer> arbol = new HashAVL<String, Integer>();
		arbol.add("D", 84);
		arbol.add("D", 10);
		arbol.add("D", 8);
		arbol.add("D", 92);
		arbol.add("D", 66);
	}*/
}
