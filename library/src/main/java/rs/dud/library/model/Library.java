package rs.dud.library.model;

import java.util.ArrayList;
import java.util.List;

public class Library {
	List<Book> books = new ArrayList<Book>();
	String libraryOwner;

	public Library(ArrayList<Book> books, String libraryOwner) {
		super();
		this.books = books;
		this.libraryOwner = libraryOwner;
	}

	public void addBook(Book book) {
		books.add(book);
	}

	public List<Book> getBooks() {
		return books;
	}

	public void deleteBook(Book book) {
		books.remove(book);

	}

	public Book getLastBook() {
		return books.get(books.size() - 1);
	}

	public Book getBookByID(int id) {
		return books.get(id);
	}

	public Book getBookByInventoryNumber(int invNumber) {
		for (Book book : books) {
			if (book.getInventoryNumber() == invNumber) {
				return book;
			}
		}
		return null;

	}

	public void setBooks(List<Book> books) {

		this.books = new ArrayList<>(books);
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

	public ArrayList<Book> getBookByTitle(ArrayList<Book> booksFound, String title) {
		title = title.toLowerCase();
		for (Book book : books) {
			if (book.getTitle().toLowerCase().contains(title)) {
				booksFound.add(book);
			}
		}
		return booksFound;

	}

	public ArrayList<Book> getBookByOriginalTitle(ArrayList<Book> booksFound, String title) {
		title = title.toLowerCase();
		for (Book book : books) {
			if (book.getOriginalTitle().toLowerCase().contains(title)) {
				booksFound.add(book);
			}
		}
		return booksFound;

	}

	public ArrayList<Book> getBookByPublisherName(ArrayList<Book> booksFound, String text) {
		text = text.toLowerCase();
		for (Book book : books) {
			if (book.getPublisherName().toLowerCase().contains(text)) {
				booksFound.add(book);
			}
		}
		return booksFound;

	}

	public ArrayList<Book> getBookByEdition(ArrayList<Book> booksFound, String text) {
		text = text.toLowerCase();
		for (Book book : books) {
			if (book.getEdition().toLowerCase().contains(text)) {
				booksFound.add(book);
			}
		}
		return booksFound;
	}

	public ArrayList<Book> getBookByWriter(ArrayList<Book> booksFound, String text) {
		text = text.toLowerCase();
		for (Book book : books) {
			if (book.getWriter().toLowerCase().contains(text)) {
				booksFound.add(book);
			}
		}
		return booksFound;
	}

	public ArrayList<Book> getBookByGenre(ArrayList<Book> booksFound, String text) {
		text = text.toLowerCase();
		for (Book book : books) {
			if (book.getGenre().toLowerCase().contains(text)) {
				booksFound.add(book);
			}
		}
		return booksFound;
	}

	public ArrayList<Book> getBookByBookCondition(ArrayList<Book> booksFound, String text) {
		text = text.toLowerCase();
		for (Book book : books) {
			if (book.getBookCondition().toLowerCase().contains(text)) {
				booksFound.add(book);
			}
		}
		return booksFound;
	}

	public ArrayList<Book> getBookByLanguage(ArrayList<Book> booksFound, String text) {
		text = text.toLowerCase();
		for (Book book : books) {
			if (book.getLanguage().toLowerCase().contains(text)) {
				booksFound.add(book);
			}
		}
		return booksFound;
	}

	public ArrayList<Book> getBookByBookOrigin(ArrayList<Book> booksFound, String text) {
		text = text.toLowerCase();
		for (Book book : books) {
			if (book.getBookOrigin().toLowerCase().contains(text)) {
				booksFound.add(book);
			}
		}
		return booksFound;
	}

	public ArrayList<Book> getBookByBookLocation(ArrayList<Book> booksFound, String text) {
		text = text.toLowerCase();
		for (Book book : books) {
			if (book.getBookLocation().toLowerCase().contains(text)) {
				booksFound.add(book);
			}
		}
		return booksFound;
	}

	public ArrayList<Book> getBookByNameOfWriterOriginal(ArrayList<Book> booksFound, String text) {
		text = text.toLowerCase();
		for (Book book : books) {
			if (book.getNameOfWriterOriginal().toLowerCase().contains(text)) {
				booksFound.add(book);
			}
		}
		return booksFound;
	}

}
