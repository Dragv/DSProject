package Project;

import java.util.Iterator;

import Project.AVLTree.Node;

@SuppressWarnings("hiding")
public class Name<Names extends Comparable<Names>, Invoice extends Comparable<Invoice>, Address, Pay extends Comparable<Pay>> extends MyHashTable<Names, Address>{
	private int size; 		// Table size
	private int elements; 	// Number of elements
	private Payment payments;
	private Expenses expenses;
	private Node<Names, Address>[] tabla;
	private final static int INIT_CAP = 101;
	
	public Name() {
		super();
		this.payments = new Payment();
	}
	
	public void addUser(Names key, Address value) {
		super.add(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public void addInvoice(Names key, Invoice inNum, Pay pay){
		AVLTree.Node toAdd = new AVLTree.Node(pay, inNum);
		this.payments.add(key, toAdd);
	}
	
	public void addExpenses(Names key, int expense, int amount){
		
	}
	
	public AVLTree.Node getInvoice (Names key, Invoice inNum){
		return (Project.AVLTree.Node) this.payments.get(key, inNum).getElement();
	}
	
	public AVLTree getInvoices (Names key){
		return this.payments.getTree(key);
		
	}
	
	public void printInvoices (Names key){
		this.getInvoices(key).inOrder();
		
	}
	
	public void removeUser(Names key) {
		super.remove(key);
	}
	
	public void removeInvoice(Names key, int invoice){
		
	}
	
	public void removeExpenses(Names key, int expense){
		
	}
	
	public void printTable(){
		Iterator<Names> it = super.getIteratorKey();
		Iterator<Address> it2 = super.getIteratorValue();
		while (it.hasNext()){
			System.out.print(it.next() + " ");
			System.out.println(it2.next());
		}
	}
	
	public static void main(String[] args) {
		Name<String, Integer, String, Integer> Something = new Name<String, Integer, String, Integer>();
		Something.add("Jack", "011001");
		Something.add("Jimmy", "0110");
		Something.add("Noah", "01fds1001");
		Something.addInvoice("Jack", 1, 1539143);
		Something.addInvoice("Ted", 1, 1539143);
		Something.addInvoice("Jack", 2, 15391433);
		System.out.println(Something.getInvoice("Jack", 1));
		System.out.println(Something.getInvoice("Jack", 2));
		System.out.println(Something.getInvoice("Ted", 1));
		Something.printInvoices("Jack");
		//Something.printTable();
	}
}
