package Project;

import java.util.Stack;

import Project.AVLTree.Node;

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
		AVLTree temp = this.tableOfPayments.getTree(name);
		
		Stack<Invoice> is = temp.inOrderNode();
		Stack<Invoice> es; 
		
		while(!is.isEmpty()){
			es = this.tableOfExpenses.getTree(is.pop().getInvoiceNum()).inOrderNode();
			while(!es.isEmpty()){
				total += es.pop().getAmount();
			}
		}
		return total;
	}
	
	public int getTotalPayments(String name){
		if (!this.tableOfNames.contains(name)){
			throw new IllegalArgumentException("Name is not in the table");
		}
		
		int total = 0;
		AVLTree temp = this.tableOfPayments.getTree(name);
		
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
	
}
