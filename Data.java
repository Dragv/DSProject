package Project;

import java.util.Stack;

public class Data {
	private Name<String, Names> tableOfNames;
	private Payment<String, Invoice> tableOfPayments;
	private Expenses<Integer, Expense> tableOfExpenses;
	
	public Data() {
		this.tableOfNames = new Name<String, Names>();
		this.tableOfPayments = new Payment<String, Invoice>();
		this.tableOfExpenses = new Expenses<Integer, Expense> ();
	}
	
	public void addName(String name, String address){
		Names temp = new Names(name, address);
		this.tableOfNames.add(temp.getName(), temp);
	}
	
	public void addInvoice(String name, int inNum, int amount){
		if (!this.tableOfNames.contains(name)){
			throw new IllegalArgumentException("Name is not in the table");
		}
		Invoice temp = new Invoice(name, inNum, amount);
		this.tableOfPayments.add(name, temp);
	}
	
	public void addExpense(String name, String item, int inNum, int cost){
		if (!this.tableOfNames.contains(name)){
			throw new IllegalArgumentException("Name is not in the table");
		}
		Expense temp = new Expense(name, item, inNum, cost);
		this.tableOfExpenses.add(inNum, temp);
	}
	
	public Invoice getInvoice(String name, int inNum){
		if (!this.tableOfNames.contains(name)){
			throw new IllegalArgumentException("Name is not in the table");
		}
		return this.tableOfPayments.get(name, new Invoice(name, inNum));
	}
	
	public Invoice removeInvoice(String name, int inNum){
		if (!this.tableOfNames.contains(name)){
			throw new IllegalArgumentException("Name is not in the table");
		}
		return this.tableOfPayments.remove(name, new Invoice(name, inNum));
	}
	
	public Expense removeExpense(String name, String item, int inNum){
		if (!this.tableOfNames.contains(name)){
			throw new IllegalArgumentException("Name is not in the table");
		}
		return this.tableOfExpenses.remove(inNum, new Expense(name, item, inNum));
	}
	
	public int getTotalExpenses(String name){
		if (!this.tableOfNames.contains(name)){
			throw new IllegalArgumentException("Name is not in the table");
		}
		
		int total = 0;
		AVLTree<Invoice> temp = this.tableOfPayments.getTree(name);
		
		Stack<Invoice> is = temp.inOrderNode();
		Stack<Expense> es; 
		
		while(!is.isEmpty()){
			int pos = is.pop().getInvoiceNum();
			if(this.tableOfExpenses.contains(pos)){
				es = this.tableOfExpenses.getTree(pos).inOrderNode();
				while(!es.isEmpty()){
					total += es.pop().getCost();
				}
			}
		}
		return total;
	}
	
	public int getTotalPayments(String name){
		if (!this.tableOfNames.contains(name)){
			throw new IllegalArgumentException("Name is not in the table");
		}
		
		int total = 0;
		AVLTree<Invoice> temp = this.tableOfPayments.getTree(name);
		
		Stack<Invoice> is = temp.inOrderNode();
		while(!is.isEmpty()){
			total += is.pop().getAmount();
		}
		return total;
	}
	
	public int differenceBetweenExpenses(String name1, String name2){
		if (!this.tableOfNames.contains(name1) || !this.tableOfNames.contains(name2)){
			throw new IllegalArgumentException("Name is not in the table");
		}
		return Math.abs(this.getTotalExpenses(name1) - this.getTotalExpenses(name2));
	}
	
	public static void main(String[] args) {
		Data database = new Data();
		database.addName("Alejandro", "Somewhere");
		
		database.addInvoice("Alejandro", 1, 1000);
		database.addInvoice("Alejandro", 2, 2000);
		database.addInvoice("Alejandro", 3, 4000);
		database.addInvoice("Alejandro", 4, 5000);
		
		database.addExpense("Alejandro", "Beer", 2, 100);
		database.addExpense("Alejandro", "A ball", 2, 100);
		database.addExpense("Alejandro", "Something else", 2, 100);
		
		System.out.println(database.getInvoice("Alejandro", 1));
		System.out.println(database.getInvoice("Alejandro", 2));
		System.out.println(database.getInvoice("Alejandro", 3));
		System.out.println(database.getInvoice("Alejandro", 4));
		System.out.println(database.getTotalPayments("Alejandro"));
		System.out.println(database.getTotalExpenses("Alejandro"));
	}
	
}
