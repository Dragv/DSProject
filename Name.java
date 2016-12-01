package Project;

/*Main table extending a Hash table using an array to store names and their respective data in a AVL tree*/

@SuppressWarnings("hiding")
public class Name<Names extends Comparable<Names>, Address> extends MyHashTable<Names, Address>{ 	
	
	public Name() {
		super();
	}
	
	public void addUser(Names key, Address value) {
		super.add(key, value);
	}
	
	public void removeUser(Names key) {
		super.remove(key);
	}
}
