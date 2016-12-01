package Project;

import java.util.Stack;



public class AVLTree<V extends Comparable<V>> {

	private Node<V> root;
	private int size;
	private static final int ALLOWED_IMBALANCE = 1;

	public AVLTree() {
		this.root = null;
	}
	
	public AVLTree(V value) {
		this.root = new Node<V>(value, null, null);
	}

	public AVLTree(Node<V> toAdd) {
		this.root = toAdd;
	}

	public void insert(V element) {
		if (this.root == null) {
			this.root = new Node<V>(element, null, null);
		} else {
			this.root = insert(element, this.root);
		}
	}

	private Node<V> insert(V element, Node<V> n) {
		if (n == null)
			return new Node<V>(element, null, null);
		int cmp = element.compareTo(n.Element);
		if (cmp < 0) {
			n.left = insert(element, n.left);
		} else if (cmp > 0) {
			n.right = insert(element, n.right);
		}
		this.size++;
		return balance(n);
	}
	
	public V get(V element){
		if (this.isEmpty()){
			throw new IllegalArgumentException("Tree is empty");
		}
		return get(element, this.root);
	}
	
	private V get(V element, Node<V> n){
		int cmp = element.compareTo(n.Element);
		if (cmp < 0){
			return get(element, n.left);
		} else if (cmp > 0) {
			return get(element, n.right);
		}
		return n.getElement();
	}
	
	public Node<V> remove(V element){
		if (this.isEmpty()){
			throw new IllegalArgumentException("Tree is empty");
		}
		Node<V> x = this.root;
		Node<V> temp;
		while(x != null){
			int cmp = element.compareTo(x.Element);
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
		this.size--;
		this.balance(x);
		return temp;
	}
	private void treeDelete(Node<V> n){
		if(n.left == null) transplant(n,n.right);
		else if(n.right == null) transplant(n,n.left);
		else{
			Node<V> max = maximum(n.left);
			if(!findParent(max).equals(n)){
				transplant(max,max.right);
				max.right = n.right;
			}
			transplant(n,max);
			Node<V> replace = null;
			if(max.left != null) replace = max.left;
			max.left = n.left;
			while(n.left != null){
				n = n.left;
			}
			n.right = replace;
		}
	}
	
	private void transplant(Node<V> u, Node<V> v){
		Node<V> uParent = findParent(u);
		
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
	private Node<V> findParent(Node<V> n){
		if(n.equals(this.root)){
			return null;
		}
		Node<V> temp = this.root;
		Node<V> parent = temp;
		while(temp != null){
			int cmp = n.Element.compareTo(temp.Element);
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
	
	private Node<V> maximum(Node<V> n){
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

	private String inOrder(Node<V> nodo) {
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
	
	public Stack<V> inOrderNode() {
		if (this.root != null) {
			Stack<V> nodes = new Stack<V>();
			nodes.push(this.root.Element);
			return inOrderNode(this.root, nodes);
		} 
		return null;
	}

	private Stack<V> inOrderNode(Node<V> nodo, Stack<V> nodes) {
		if (nodo.left != null) {
			nodes.push(nodo.left.getElement());
			inOrderNode(nodo.left, nodes);
		}
		if (nodo.right != null) {
			nodes.push(nodo.right.getElement());
			inOrderNode(nodo.right, nodes);
		}
		return nodes;
	}
	
	private Node<V> balance(Node<V> n) {
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

	private int height(Node<V> n) {
		return n == null ? 0 : n.height;
	}

	private Node<V> doubleWithLeftChild(Node<V> x) {
		x.left = rotateWithRightChild(x.left);
		return rotateWithLeftChild(x);
	}

	private Node<V> rotateWithLeftChild(Node<V> a) {
		Node<V> b = a.left;
		a.left = b.right;
		b.right = a;
		a.height = Math.max(height(a.left), height(a.right)) + 1;
		b.height = Math.max(height(b.left), height(b.right)) + 1;
		return b;
	}

	private Node<V> doubleWithRightChild(Node<V> x) {
		x.right = rotateWithLeftChild(x.right);
		return rotateWithRightChild(x);
	}

	private Node<V> rotateWithRightChild(Node<V> a) {
		Node<V> b = a.right;
		a.right = b.left;
		b.left = a;
		a.height = Math.max(height(a.left), height(a.right)) + 1;
		b.height = Math.max(height(b.left), height(b.right)) + 1;
		return b;
	}
	
	public int size(){
		return this.size;
	}
	
	public Node<V> getRoot(){
		return this.root;
	}

	public static class Node<V extends Comparable<V>> {
		V Element;
		int height;
		Node<V> left, right;

		public Node(V element, Node<V> left, Node<V> right) {
			this.Element = element;;
			this.left = left;
			this.right = right;
			this.height = 1;
		}
		
		public Node(V element) {
			this.Element = element;
			this.height = 1;
		}
		
		public V getElement() {
			return Element;
		}

		public String toString() {
			return "[" + Element + "]";
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
