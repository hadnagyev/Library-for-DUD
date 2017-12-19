package rs.dud.model;

import java.util.ArrayList;

public class Library {
	ArrayList<Book> Books = new ArrayList<Book>();

	String libraryOwner;
	String libraryAddresses;

	public Library(String libraryOwner, String libraryAddresses) {
		super();
		this.libraryOwner = libraryOwner;
		this.libraryAddresses = libraryAddresses;
	}

	public ArrayList<Book> getBooks() {
		return Books;
	}

	public void setBooks(ArrayList<Book> books) {
		Books = books;
	}

	public String getLibraryOwner() {
		return libraryOwner;
	}

	public void setLibraryOwner(String libraryOwner) {
		this.libraryOwner = libraryOwner;
	}

	public String getLibraryAddresses() {
		return libraryAddresses;
	}

	public void setLibraryAddresses(String libraryAddresses) {
		this.libraryAddresses = libraryAddresses;
	}

	@Override
	public String toString() {
		return "Library [Books=" + Books + ", libraryOwner=" + libraryOwner + ", libraryAddresses=" + libraryAddresses
				+ "]";
	}

}
