package Project;

import java.util.Stack;

public class AVLTreeEx<V extends Comparable<V>> {

	private NodeEx<V> root;
	private static final int ALLOWED_IMBALANCE = 1;

	public AVLTreeEx() {
		this.root = null;
	}
	
	public AVLTreeEx(V value) {
		this.root = new NodeEx<V>(value, null, null);
	}

	public AVLTreeEx(NodeEx<V> toAdd) {
		this.root = toAdd;
	}

	public void insert(V element) {
		if (this.root == null) {
			this.root = new NodeEx<V>(element, null, null);
		} else {
			this.root = insert(element, this.root);
		}
	}

	private NodeEx<V> insert(V element, NodeEx<V> n) {
		if (n == null)
			return new NodeEx<V>(element, null, null);
		int cmp = element.compareTo(n.Element);
		if (cmp < 0) {
			n.left = insert(element, n.left);
		} else if (cmp > 0) {
			n.right = insert(element, n.right);
		}
		n.counter++;
		return balance(n);
	}
	
	public V get(V element){
		if (this.isEmpty()){
			throw new IllegalArgumentException("Tree is empty");
		}
		return get(element, this.root);
	}
	
	private V get(V element, NodeEx<V> n){
		int cmp = element.compareTo(n.Element);
		if (cmp < 0){
			return get(element, n.left);
		} else if (cmp > 0) {
			return get(element, n.right);
		}
		return n.getElement();
	}
	
	public NodeEx<V> remove(V element){
		if (this.isEmpty()){
			throw new IllegalArgumentException("Tree is empty");
		}
		NodeEx<V> x = this.root;
		NodeEx<V> temp;
		while(x != null){
			int cmp = element.compareTo(x.Element);
			if(cmp == 0){
				if (x.counter > 1){
					x.counter--;
					return x;
				}
				break;
			}else if(cmp > 0){
				x = x.right;
			}else if(cmp < 0){
				x = x.left;
			}
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
	private void treeDelete(NodeEx<V> n){
		if(n.left == null) transplant(n,n.right);
		else if(n.right == null) transplant(n,n.left);
		else{
			NodeEx<V> max = maximum(n.left);
			if(!findParent(max).equals(n)){
				transplant(max,max.right);
				max.right = n.right;
			}
			transplant(n,max);
			NodeEx<V> replace = null;
			if(max.left != null) replace = max.left;
			max.left = n.left;
			while(n.left != null){
				n = n.left;
			}
			n.right = replace;
		}
	}
	
	private void transplant(NodeEx<V> u, NodeEx<V> v){
		NodeEx<V> uParent = findParent(u);
		
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
	private NodeEx<V> findParent(NodeEx<V> n){
		if(n.equals(this.root)){
			return null;
		}
		NodeEx<V> temp = this.root;
		NodeEx<V> parent = temp;
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
	
	private NodeEx<V> maximum(NodeEx<V> n){
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

	private String inOrder(NodeEx<V> nodo) {
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

	private Stack<V> inOrderNode(NodeEx<V> nodo, Stack<V> nodes) {
		if (nodo.left != null) {
			while(nodo.left.counter > 1){
				nodes.push(nodo.left.getElement());
				nodo.left.counter--;
			}
			nodes.push(nodo.left.getElement());
			inOrderNode(nodo.left, nodes);
		}
		if (nodo.right != null) {
			while(nodo.right.counter > 1){
				nodes.push(nodo.right.getElement());
				nodo.right.counter--;
			}
			nodes.push(nodo.right.getElement());
			inOrderNode(nodo.right, nodes);
		}
		return nodes;
	}

	private NodeEx<V> balance(NodeEx<V> n) {
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

	private int height(NodeEx<V> n) {
		return n == null ? 0 : n.height;
	}

	private NodeEx<V> doubleWithLeftChild(NodeEx<V> x) {
		x.left = rotateWithRightChild(x.left);
		return rotateWithLeftChild(x);
	}

	private NodeEx<V> rotateWithLeftChild(NodeEx<V> a) {
		NodeEx<V> b = a.left;
		a.left = b.right;
		b.right = a;
		a.height = Math.max(height(a.left), height(a.right)) + 1;
		b.height = Math.max(height(b.left), height(b.right)) + 1;
		return b;
	}

	private NodeEx<V> doubleWithRightChild(NodeEx<V> x) {
		x.right = rotateWithLeftChild(x.right);
		return rotateWithRightChild(x);
	}

	private NodeEx<V> rotateWithRightChild(NodeEx<V> a) {
		NodeEx<V> b = a.right;
		a.right = b.left;
		b.left = a;
		a.height = Math.max(height(a.left), height(a.right)) + 1;
		b.height = Math.max(height(b.left), height(b.right)) + 1;
		return b;
	}

	public static class NodeEx<V extends Comparable<V>> {
		V Element;
		int height;
		int counter;
		NodeEx<V> left, right;

		public NodeEx(V element, NodeEx<V> left, NodeEx<V> right) {
			this.Element = element;;
			this.left = left;
			this.right = right;
			this.counter = 1;
			this.height = 1;
		}
		
		public NodeEx(V element) {
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
}

