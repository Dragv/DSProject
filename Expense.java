package Project;

public class Expense implements Comparable<Expense> {
	
	private String name;
	private String item;
	private int invoiceNum;
	private int cost;
	
	public Expense(String name, String item, int invoiceNum ,int cost) {
		this.setName(name);
		this.setItem(item);
		this.setInvoiceNum(invoiceNum);
		this.setCost(cost);
	}
	
	public Expense(String name, String item, int invoiceNum) {
		this.setName(name);
		this.setItem(item);
		this.setInvoiceNum(invoiceNum);
		this.setCost(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(int invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public String toString() {
		return ("Name - " + this.name + "\nInvoice Num - " + this.invoiceNum + "\nItem - " + this.item + "\nCost - " + this.cost + "\n");
	}

	public int compareTo(Expense o) {
		return this.item.compareTo(o.item);	
	}
}
