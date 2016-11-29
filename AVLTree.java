package Project;

import java.util.EmptyStackException;


public class AVLTree<K extends Comparable<K>, V> {

	private Node<K, V> root;
	private static final int ALLOWED_IMBALANCE = 1;

	public AVLTree() {
		this.root = null;
	}
	
	public AVLTree(K key, V value) {
		this.root = new Node<K, V>(value, key, null, null);
	}

	public AVLTree(Node<K, V> toAdd) {
		this.root = toAdd;
	}

	public void insert(K key, V element) {
		if (this.root == null) {
			this.root = new Node<K, V>(element, key, null, null);
		} else {
			this.root = insert(element, key, this.root);
		}
	}

	private Node<K, V> insert(V element, K key, Node<K, V> n) {
		if (n == null)
			return new Node<K, V>(element, key, null, null);
		int cmp = key.compareTo(n.key);
		if (cmp < 0) {
			n.left = insert(element, key, n.left);
		} else if (cmp > 0) {
			n.right = insert(element, key, n.right);
		}
		return balance(n);
	}
	
	public Node<K, V> get(K key){
		if (this.isEmpty()){
			throw new IllegalArgumentException("Tree is empty");
		}
		return get(key, this.root);
	}
	
	private Node<K, V> get(K key, Node<K, V> n){
		int cmp = key.compareTo(n.key);
		if (cmp < 0){
			return get(key, n.left);
		} else if (cmp > 0) {
			return get(key, n.right);
		}
		return n;
	}
	
	public Node<K, V> remove(K key){
		if (this.isEmpty()){
			throw new IllegalArgumentException("Tree is empty");
		}
		Node<K, V> x = this.root;
		Node<K, V> temp;
		while(x != null){
			int cmp = key.compareTo(x.key);
			if(cmp == 0)
				break;
			else if(cmp > 0)
				x = x.right;
			else if(cmp < 0)
				x = x.left;
		}
		if(x == null)
			return null;
		else{
			temp = x;
			treeDelete(x);
		}
		this.balance(x);
		return temp;
	}
	private void treeDelete(Node<K, V> n){
		if(n.left == null) transplant(n,n.right);
		else if(n.right == null) transplant(n,n.left);
		else{
			Node<K, V> max = maximum(n.left);
			if(!findParent(max).equals(n)){
				transplant(max,max.right);
				max.right = n.right;
			}
			transplant(n,max);
			Node<K, V> replace = null;
			if(max.left != null) replace = max.left;
			max.left = n.left;
			while(n.left != null){
				n = n.left;
			}
			n.right = replace;
		}
	}
	
	private void transplant(Node<K, V> u, Node<K, V> v){
		Node<K, V> uParent = findParent(u);
		
		if(uParent == null){
			this.root = v;
		}
		else if(u == uParent.left){
			uParent.left = v;
		}
		else{
			uParent.right = v;
		}
	}
	private Node<K, V> findParent(Node<K, V> n){
		if(n.equals(this.root)){
			return null;
		}
		Node<K, V> temp = this.root;
		Node<K, V> parent = temp;
		while(temp != null){
			int cmp = n.key.compareTo(temp.key);
			if(cmp == 0)
				break;
			else if(cmp > 0){
				parent = temp;
				temp = temp.right;
			}
			else if(cmp < 0){
				parent = temp;
				temp = temp.left;
			}
		}
		return parent;
	}
	
	private Node<K, V> maximum(Node<K, V> n){
		if(n == null)
			return null;
		else if(n.right == null)
			return n;
		while(n.right != null)
			n = n.right;
		return n;
	}
	
	public boolean isEmpty(){
		return this.root == null;
	}

	public String inOrder() {
		if (this.root != null) {
			return inOrder(this.root);
		} else {
			return "";
		}
	}

	private String inOrder(Node<K, V> nodo) {
		String salida = "";
		if (nodo.left != null) {
			salida += inOrder(nodo.left);
		}
		salida += nodo.toString();
		if (nodo.right != null) {
			salida += inOrder(nodo.right);
		}
		return salida;
	}

	private Node<K, V> balance(Node<K, V> n) {
		if (n == null)
			return n;

		if (height(n.left) - height(n.right) > ALLOWED_IMBALANCE) {
			if (height(n.left.left) >= height(n.left.right)) {
				n = rotateWithLeftChild(n);
			} else {
				n = doubleWithLeftChild(n);
			}
		} else {
			if (height(n.right) - height(n.left) > ALLOWED_IMBALANCE) {
				if (height(n.right.right) >= height(n.right.left)) {
					n = rotateWithRightChild(n);
				} else {
					n = doubleWithRightChild(n);
				}
			}
		}
		n.height = Math.max(height(n.left), height(n.right)) + 1;
		return n;
	}

	private int height(Node<K, V> n) {
		return n == null ? 0 : n.height;
	}

	private Node<K, V> doubleWithLeftChild(Node<K, V> x) {
		x.left = rotateWithRightChild(x.left);
		return rotateWithLeftChild(x);
	}

	private Node<K, V> rotateWithLeftChild(Node<K, V> a) {
		Node<K, V> b = a.left;
		a.left = b.right;
		b.right = a;
		a.height = Math.max(height(a.left), height(a.right)) + 1;
		b.height = Math.max(height(b.left), height(b.right)) + 1;
		return b;
	}

	private Node<K, V> doubleWithRightChild(Node<K, V> x) {
		x.right = rotateWithLeftChild(x.right);
		return rotateWithRightChild(x);
	}

	private Node<K, V> rotateWithRightChild(Node<K, V> a) {
		Node<K, V> b = a.right;
		a.right = b.left;
		b.left = a;
		a.height = Math.max(height(a.left), height(a.right)) + 1;
		b.height = Math.max(height(b.left), height(b.right)) + 1;
		return b;
	}

	public static class Node<K extends Comparable<K>, V> {
		V Element;
		K key;
		int height;
		Node<K, V> left, right;

		public Node(V element, K key, Node<K, V> left, Node<K, V> right) {
			this.Element = element;
			this.key = key;
			this.left = left;
			this.right = right;
			this.height = 1;
		}
		
		public Node(V element, K key) {
			this.Element = element;
			this.key = key;
			this.height = 1;
		}

		public K getKey() {
			return key;
		}
		
		public V getElement() {
			return Element;
		}

		public String toString() {
			return "[" + key + "-" + Element + "]";
		}
	}

	/*public static void main(String[] args) {
		AVLTree<String, Integer> arbol = new AVLTree<String, Integer>();
		arbol.insert("D", 84);
		arbol.insert("DA", 10);
		arbol.insert("DD", 8);
		arbol.insert("DF", 92);
		arbol.insert("DG", 66);
		System.out.println(arbol.remove("DD"));


		System.out.println(arbol.inOrder());
	}*/
}
