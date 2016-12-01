package Project;

public class Expenses<K extends Comparable<K>, V extends Comparable <V>> {
	
	private AVLTreeEx<V>[] tabla;
	protected int size; 		// Table size
	protected int elements; 	// Number of elements
	protected final static int INIT_CAP = 101;

	@SuppressWarnings("unchecked")
	public Expenses(int size, int carga) {
		this.size = size;
		this.elements = 0;
		this.tabla = (AVLTreeEx<V>[]) new AVLTreeEx[size];
	}

	public Expenses() {
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
		return (V) tabla[pos].get(value);
	}

	public boolean contains(K key) {
		if (key == null) {
			throw new NullPointerException("Invalid key. Key is null");
		}
		int pos = hash(key);
		return tabla[pos] != null;
	}

	public void add(K key, V value) {
		if (key == null || value == null) {
			throw new IllegalArgumentException();
		}
		int pos = hash(key);
		if (tabla[pos] == null){
			tabla[pos] = new AVLTreeEx<V>(value);
		}
		tabla[pos].insert(value);
		this.elements++;
	}

	public V remove(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
		int pos = hash(key);
		return (V) tabla[pos].remove(value).getElement();
	}
	
	public AVLTreeEx<V> getTree(K key) {
		if (key == null) {
			throw new NullPointerException("Invalid key. Key is null");
		}
		int pos = hash(key);
		return this.tabla[pos];
	}
}
