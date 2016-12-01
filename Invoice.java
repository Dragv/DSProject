package Project;

public class Invoice implements Comparable<Invoice>{
	
	private String name;
	private int invoiceNum;
	private int amount;
	
	public Invoice(String name, int invoiceNum, int amount) {
		this.name = name;
		this.invoiceNum = invoiceNum;
		this.amount = amount;
	}
	
	public Invoice(String name, int invoiceNum) {
		this.name = name;
		this.invoiceNum = invoiceNum;
		this.amount = 0;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public int getInvoiceNum() {
		return invoiceNum;
	}
	
	public String getName() {
		return name;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
		
	public void setInvoiceNum(int invoiceNum) {
		this.invoiceNum = invoiceNum;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return ("Name - " + this.name + "\nInvoice Num - " + this.invoiceNum + "\nPayment - " + this.amount + "\n");
	}

	public int compareTo(Invoice a) {
		if (this.getInvoiceNum() > a.getInvoiceNum()){
			return 1;
		} else if (this.getInvoiceNum() < a.getInvoiceNum()){
			return -1;
		} else {
			return 0;
		}
	}
}
