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

	public ArrayList<Book> getBookByTitle(ArrayList<Book> booksByTitle, String title){
		title = title.toLowerCase();
		for(Book book:books){
			if(book.getTitle().toLowerCase().contains(title)){
				booksByTitle.add(book);
			}
		}
		return booksByTitle;
		
	}
	public ArrayList<Book> getBookByOriginalTitle(ArrayList<Book> booksByParameter, String title){
		title = title.toLowerCase();
		for(Book book:books){
			if(book.getOriginalTitle().toLowerCase().contains(title)){
				booksByParameter.add(book);
			}
		}
		return booksByParameter;
		
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

	public ArrayList<Book> getBookByPublisherName(ArrayList<Book> booksFound, String text) {
		text = text.toLowerCase();
		for(Book book:books){
			if(book.getOriginalTitle().toLowerCase().contains(text)){
				booksFound.add(book);
			}
		}
		return booksFound;

	}

	public ArrayList<Book> getBookByEdition(ArrayList<Book> booksFound, String text) {
		text = text.toLowerCase();
		for(Book book:books){
			if(book.getEdition().toLowerCase().contains(text)){
				booksFound.add(book);
			}
		}
		return booksFound;
	}

	public ArrayList<Book> getBookByWriter(ArrayList<Book> booksFound, String text) {
		text = text.toLowerCase();
		for(Book book:books){
			if(book.getWriter().toLowerCase().contains(text)){
				booksFound.add(book);
			}
		}
		return booksFound;
	}

	public ArrayList<Book> getBookBygenre(ArrayList<Book> booksFound, String text) {
		text = text.toLowerCase();
		for(Book book:books){
			if(book.getGenre().toLowerCase().contains(text)){
				booksFound.add(book);
			}
		}
		return booksFound;
	}

	public ArrayList<Book> getBookBybookCondition(ArrayList<Book> booksFound, String text) {
		text = text.toLowerCase();
		for(Book book:books){
			if(book.getBookCondition().toLowerCase().contains(text)){
				booksFound.add(book);
			}
		}
		return booksFound;
	}

	public ArrayList<Book> getBookBylanguage(ArrayList<Book> booksFound, String text) {
		text = text.toLowerCase();
		for(Book book:books){
			if(book.getLanguage().toLowerCase().contains(text)){
				booksFound.add(book);
			}
		}
		return booksFound;
	}

	public ArrayList<Book> getBookBybookOrigin(ArrayList<Book> booksFound, String text) {
		text = text.toLowerCase();
		for(Book book:books){
			if(book.getBookOrigin().toLowerCase().contains(text)){
				booksFound.add(book);
			}
		}
		return booksFound;
	}

	public ArrayList<Book> getBookBybookLocation(ArrayList<Book> booksFound, String text) {
		text = text.toLowerCase();
		for(Book book:books){
			if(book.getBookLocation().toLowerCase().contains(text)){
				booksFound.add(book);
			}
		}
		return booksFound;
	}

	public ArrayList<Book> getBookByNameOfWriterOriginal(ArrayList<Book> booksFound, String text) {
		text = text.toLowerCase();
		for(Book book:books){
			if(book.getNameOfWriterOriginal().toLowerCase().contains(text)){
				booksFound.add(book);
			}
		}
		return booksFound;
	}



}
