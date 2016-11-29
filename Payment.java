package Project;

public class Payment<Name extends Comparable<Name>, Payment extends Comparable <Payment>, Invoice> extends HashAVL<Name, Invoice> {
	
	private AVLTree[] AVLtabla;
	
	public Payment(int size, int carga) {
		this.size = size;
		this.elements = 0;
		this.AVLtabla = (AVLTree<Payment, Invoice>[]) new AVLTree[size];
	}

	public Payment() {
		this(INIT_CAP, 48);
	}
	
	public void add(Name key, AVLTree.Node toAdd){
		if (key == null) {
			throw new IllegalArgumentException();
		}
		int pos = hash(key);
		if (this.AVLtabla[pos] == null){
			this.AVLtabla[pos] = new AVLTree();
		}
		this.AVLtabla[pos].insert(toAdd.getKey(), toAdd);
		this.elements++;
	}
	
	public AVLTree.Node<Name, Payment> get(Name key, Invoice value) {
		if (key == null) {
			throw new NullPointerException("Invalid key. Key is null");
		}
		int pos = hash(key);
		return this.AVLtabla[pos].get((Comparable) value);
	}
	
	public AVLTree getTree(Name key) {
		if (key == null) {
			throw new NullPointerException("Invalid key. Key is null");
		}
		int pos = hash(key);
		return this.AVLtabla[pos];
	}
}
