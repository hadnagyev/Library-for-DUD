package main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Year;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

import rs.dud.model.Book;

public class Application {
	ArrayList<String> readFromFile = new ArrayList<String>();
	ArrayList<Book> library = new ArrayList<Book>();
	String file = "POPIS KNJIGA najnoviji.txt";

	public static void main(String[] args) throws IOException, UnsupportedEncodingException {
		Application app = new Application();
		app.readFromFile();
		app.fillBooksToLibrary(app.findFirstEntry());

		// temporary for testing
		app.showMenu();
		Scanner sc = new Scanner(System.in);
		int menuID = sc.nextInt();
		while (menuID != 0) {
			
			if (menuID == 1) {
				for (Book book : app.library) {
					System.out.println(book.toString());
					
				}
			}else if(menuID==2){
				System.out.println("Enter book id");
				int bookIndex = sc.nextInt();
				System.out.println(app.library.get(bookIndex-1));
			}
			app.showMenu();
			menuID = sc.nextInt();
		}
		sc.close();
		// end of temporary for testing
	}

	// temporary menu
	private void showMenu() {
		System.out.println("0 - exit");
		System.out.println("1 - list all books");
		System.out.println("2 - list book on id (redni broj)");
	}

	// parsing temporary arraylist to arraylist with book model
	private void fillBooksToLibrary(int indexOfFirstEntry) {
		while (indexOfFirstEntry != readFromFile.size()) {
			String idInput = readFromFile.get(indexOfFirstEntry++);
			String idNumber = idInput.substring(0, idInput.length() - 2);

			Integer id = Integer.valueOf(idNumber);
			String inventoryNumberInput = readFromFile.get(indexOfFirstEntry++);
			String inventoryNumberValue;
			if (inventoryNumberInput.equals("")) {
				inventoryNumberValue = "0";
			} else {
				inventoryNumberValue = inventoryNumberInput.substring(0, inventoryNumberInput.length() - 2);
			}

			int inventoryNumber = Integer.parseInt(inventoryNumberValue);
			String publisherName = readFromFile.get(indexOfFirstEntry++);

			Year yearOfPublishing;
			if (readFromFile.get(indexOfFirstEntry).equals("")) {
				yearOfPublishing = null;
				indexOfFirstEntry++;
			} else {
				yearOfPublishing = Year.parse(readFromFile.get(indexOfFirstEntry++));
			}

			String edition = readFromFile.get(indexOfFirstEntry++);
			String nameOfWriterOriginal = readFromFile.get(indexOfFirstEntry++);
			String writer = readFromFile.get(indexOfFirstEntry++);
			String originalTitle = readFromFile.get(indexOfFirstEntry++);
			String title = readFromFile.get(indexOfFirstEntry++);
			String language = readFromFile.get(indexOfFirstEntry++);
			String writingSystem = readFromFile.get(indexOfFirstEntry++);
			String genre = readFromFile.get(indexOfFirstEntry++);
			String bookCondition = readFromFile.get(indexOfFirstEntry++);
			String bookOrigin = readFromFile.get(indexOfFirstEntry++);
			String bookLocation = readFromFile.get(indexOfFirstEntry++);
			if (readFromFile.get(indexOfFirstEntry).equals("")) {
				indexOfFirstEntry++;
			}

			Book book = new Book(id, inventoryNumber, publisherName, yearOfPublishing, edition, nameOfWriterOriginal,
					writer, originalTitle, title, language, writingSystem, genre, bookCondition, bookOrigin,
					bookLocation);
			library.add(book);
			System.out.println(book.toString());
			if (readFromFile.get(indexOfFirstEntry).equals("R.br.")) {
				break;
			}
		}
	}

	// populate arraylist from txt file
	private void readFromFile() {
		try (Stream<String> stream = Files.lines(Paths.get(file))) {
			stream.forEach(readFromFile::add);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unsuported encoding");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// finding first entry in the txt file (starts with "1. " when exporting
	// from .doc to .txt using openoffice
	private int findFirstEntry() {
		int indexOfFirstEntry = 0;
		String line;
		outerloop: while (indexOfFirstEntry != readFromFile.size()) {
			line = readFromFile.get(indexOfFirstEntry);
			System.out.println(indexOfFirstEntry);
			if (line.equals("1. ")) {
				break outerloop;
			}
			indexOfFirstEntry++;
		}
		System.out.println(indexOfFirstEntry);
		return indexOfFirstEntry;
	}
}
