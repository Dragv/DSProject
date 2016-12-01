package Project;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class MyHashTable<K, V> {
	private int size; 		// Table size
	private int elements; 	// Number of elements
	private Node<K, V>[] tabla;
	private final static int INIT_CAP = 101;

	@SuppressWarnings("unchecked")
	public MyHashTable(int size, int carga) {
		this.size = size;
		this.elements = 0;
		this.tabla = (Node<K, V>[]) new Node[size];
	}

	public MyHashTable() {
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

	public Node<K, V> get(K key) {
		if (key == null) {
			throw new NullPointerException("Invalid key. Key is null");
		}
		int pos = hash(key);
		for (Node<K, V> x = tabla[pos]; x != null; x = x.getNext()) {
			if (x.getKeg().equals(key)) {
				return x;
			}
		}
		return null;
	}

	public boolean contains(K key) {
		return get(key) != null;
	}

	public void add(K key, V value) {
		if (key == null || value == null) {
			throw new IllegalArgumentException();
		}
		int pos = hash(key);
		for (Node<K, V> x = tabla[pos]; x != null; x = x.getNext()) {
			if (x.getKeg().equals(key)) {
				x.setValue(value);
				return;
			}
		}
		this.elements++;
		tabla[pos] = new Node(tabla[pos], key, value);
	}

	public V remove(K key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		int pos = hash(key);
		V toReturn = null;
		Node<K,V> first = this.tabla[pos];
		if(first.keg.equals(key)){
			V saved = first.value;
			this.tabla[pos] = first.next;
			this.size--;
			return saved;
		}
		for (Node<K, V> x = tabla[pos]; x != null; x = x.getNext()) {
			if (x.getKeg().equals(key)) {
				toReturn = x.getValue();
				x.next = x.next.next;
				this.size--;
				return toReturn;
			}
		}
		return toReturn;

	}
	
	public Iterator<K> getIteratorKey(){
		return new KeyIterator();
	}
	
	public Iterator<V> getIteratorValue(){
		return new ValueIterator();
	}
	
	public Iterable<K> keys() {
		Queue<K> queue = new LinkedList<K>();
		for (int i = 0; i < size; i++) {
			for (Node<K, V> x = tabla[i]; x != null; x = x.getNext()) {
				queue.add((K) x.getKeg());
			}
		}
		return queue;
	}

	private abstract class HashIterator<E> implements Iterator<E> {
		Node<K, V> next;
		int index;

		public HashIterator() {
			this.index = 0;
			if (!isEmpty()) {
				while (index < size && tabla[index] == null) {
					this.index++;
				}
				this.next = tabla[index];
			}
		}

		public boolean hasNext() {
			return next != null;
		}
		
		public Node<K, V> nextNode(){
			Node<K, V> elem = next;
			if (elem == null){
				throw new NoSuchElementException();
			}
			this.next = elem.getNext();
			if (this.next == null){
				this.index++;
				while (index < size && tabla[index] == null) {
					this.index++;
				}
				if (this.index < size){
					this.next = tabla[index];
				}
			} 
			return elem;
		}
	}
	
	private class ValueIterator extends HashIterator<V>{
		public V next(){
			return nextNode().getValue();
		}
	}
	
	private class KeyIterator extends HashIterator<K>{
		public K next(){
			return nextNode().getKeg();
		}
	}

	public static class Node<K, V> {
		private K keg;
		private V value;
		private Node<K, V> next;

		public Node(Node<K, V> next, K keg, V value) {
			this.setKeg(keg);
			this.setValue(value);
			this.setNext(next);
		}

		public K getKeg() {
			return keg;
		}

		public void setKeg(K keg) {
			this.keg = keg;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public Node<K, V> getNext() {
			return next;
		}

		public void setNext(Node<K, V> next) {
			this.next = next;
		}
		@Override
		public String toString() {
			return "[" + keg + " - " + value + "]";
		}
	}
}
