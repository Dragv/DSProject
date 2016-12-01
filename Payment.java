package Project;

public class Payment<K extends Comparable<K>, V extends Comparable<V>> extends HashAVL<K, V> {
	
	public Payment(int size, int carga) {
		super();
	}

	public Payment() {
		this(INIT_CAP, 48);
	}
	
	public AVLTree getTree(K key) {
		if (key == null) {
			throw new NullPointerException("Invalid key. Key is null");
		}
		int pos = hash(key);
		return this.tabla[pos];
	}
}
