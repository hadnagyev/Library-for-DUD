package rs.dud.library.model;

import java.util.ArrayList;

public class Library {
	ArrayList<Book> books = new ArrayList<Book>();
	String libraryOwner;

	public Library(ArrayList<Book> books, String libraryOwner) {
		super();
		this.books = books;
		this.libraryOwner = libraryOwner;
	}

	public ArrayList<Book> getBooks() {
		return books;
	}
	public ArrayList<Book> getBooks(int id) {
		return books;
	}

	public void setBooks(ArrayList<Book> books) {
		this.books = books;
	}

	public String getLibraryOwner() {
		return libraryOwner;
	}

	public void setLibraryOwner(String libraryOwner) {
		this.libraryOwner = libraryOwner;
	}

	@Override
	public String toString() {
		return "Library [books=" + books + ", libraryOwner=" + libraryOwner + "]";
	}



}
