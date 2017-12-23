package rs.dud.library.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Year;
import java.util.ArrayList;
import java.util.stream.Stream;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import rs.dud.library.model.Book;
import rs.dud.library.model.Library;

public class App extends Application {
	ArrayList<String> readFromFile = new ArrayList<String>();
	ArrayList<Book> tempLibrary = new ArrayList<Book>();
	String file = "POPIS KNJIGA najnoviji.txt";

	public static void main(String[] args) throws IOException, UnsupportedEncodingException {

		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		App app = new App();
		app.readFromFile();
		app.fillBooksToLibrary(app.findFirstEntry());
		Library library = new Library(app.tempLibrary, "Svetislav");
		primaryStage.setTitle("Library");
		ListView<Book> listView = new ListView<>();
		ObservableList<Book> observableList = FXCollections.observableList(library.getBooks());
		listView.setItems(observableList);
		listView.setCellFactory(new Callback<ListView<Book>, ListCell<Book>>() {

			@Override
			public ListCell<Book> call(ListView<Book> param) {
				ListCell<Book> books = new ListCell<Book>() {
					@Override
					protected void updateItem(Book book, boolean bln) {
						super.updateItem(book, bln);
						if (book != null) {
							setText(book.toString());

						}
					}
				};
				return books;
			}

		});
		StackPane root = new StackPane();
		root.getChildren().add(listView);
		primaryStage.setScene(new Scene(root, 500, 750));
		primaryStage.show();
	}

	// parsing temporary arraylist to templibrary list with book model
	private void fillBooksToLibrary(int indexOfFirstEntry) {
		while (indexOfFirstEntry != readFromFile.size()) {
			String idInput = readFromFile.get(indexOfFirstEntry++);
			String idNumber = idInput.substring(0, idInput.length() - 2); // erasing ". " at the end of the String in id number
			Integer id = Integer.valueOf(idNumber);
			String inventoryNumberInput = readFromFile.get(indexOfFirstEntry++);
			String inventoryNumberValue;
			// skip if there is no inventory number
			if (inventoryNumberInput.equals("")) {
				inventoryNumberValue = "0";
			} else {
				inventoryNumberValue = inventoryNumberInput.substring(0, inventoryNumberInput.length() - 2);
			}

			int inventoryNumber = Integer.parseInt(inventoryNumberValue);
			String publisherName = readFromFile.get(indexOfFirstEntry++);

			Year yearOfPublishing;
			// skip if there is not year of publishing
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

			Book book = new Book(id, inventoryNumber, publisherName, yearOfPublishing, edition, nameOfWriterOriginal, writer, originalTitle, title, language, writingSystem, genre, bookCondition,
					bookOrigin, bookLocation);
			tempLibrary.add(book);

			// end if there is "id number" in the string which means it is the
			// bottom of the table
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

			// if string contains "1. ", it is the index of the first entry of
			// the book in txt file
			if (line.equals("1. ")) {
				break outerloop;
			}
			indexOfFirstEntry++;
		}

		return indexOfFirstEntry;
	}

}
