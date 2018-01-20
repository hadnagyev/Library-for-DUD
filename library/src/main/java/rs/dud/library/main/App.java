package rs.dud.library.main;

import rs.dud.library.model.Book;
import rs.dud.library.model.Library;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class App extends Application {
	ArrayList<String> readFromFile = new ArrayList<String>();
	ArrayList<Book> tempLibrary = new ArrayList<Book>();
	String file = "POPIS KNJIGA najnoviji.txt";

	Button btnListAllBooks = new Button("List all books");
	Button btnSearch = new Button("Search");
	TextField txtFieldIdNumber = new TextField();
	TextField txtFieldBookTitle = new TextField();
	GridPane gPane = new GridPane();
	ListView<Book> listViewSelectedBook = new ListView<Book>();
	TableView<Book> tableViewReturnedBooks = new TableView<>();

	public static void main(String[] args) throws IOException, UnsupportedEncodingException {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		App app = new App();
		Library library = new Library(app.tempLibrary, "Svetislav");// TODO hard coded library owner, fix it, maybe not even needed
		app.readFromFile();//populate arraylist from txt file provided
		app.fillBooksToLibrary(app.findFirstEntry()); //find row of the first book entry
		primaryStage.setTitle(library.getLibraryOwner() + " Library");
		setUpButtons(library);
		setUpTableViewreaction();
		setUpTxtFields();
		setUpGpane();
		primaryStage.setScene(new Scene(gPane, 1900, 800));//size of the window
		primaryStage.show();
	}

	private void setUpTxtFields() {
		//txt field for searching books by various parameters
		txtFieldIdNumber.setPromptText("input id of the book");//initial text in the textfield
		txtFieldBookTitle.setPromptText("input search parameter");//initial text in the textfield
		//if enter gets pressed while txtfieldID has focus, button for listing that id is fired
		txtFieldIdNumber.setOnKeyPressed(ke -> {
			if (ke.getCode().equals(KeyCode.ENTER)) {
				btnSearch.fire();
			}
		});
		txtFieldBookTitle.setOnKeyPressed(ke -> {
			if (ke.getCode().equals(KeyCode.ENTER)) {
				btnSearch.fire();
			}
		});
	}
	private void setUpTableViewreaction(){
		tableViewReturnedBooks.setOnMouseClicked(e ->{
			ArrayList<Book> list = new ArrayList<Book>();
			list.add(tableViewReturnedBooks.getSelectionModel().getSelectedItem());
			listViewSelectedBook.setItems(FXCollections.observableArrayList(list));
			});
		
	}
	private void setUpButtons(Library library) {
		// list all books button
		ObservableList<Book> observableList = FXCollections.observableList(library.getBooks());
		btnListAllBooks.setOnAction(e -> {

			tableViewReturnedBooks.setItems(observableList);

		});
		//list Book by specific id that user typed in txtFieldIdNumber
		//TODO make universal search, to find entered data in all fields and mark it in the table what field returned a result, maybe bold font on that particular entry
		btnSearch.setOnAction(e -> {
			ArrayList<Book> booksFound = new ArrayList<Book>();
			//only execute if txtfieldTitle has value
			String txtSearch = txtFieldBookTitle.getText();
			if (txtSearch != null && !txtSearch.isEmpty()) {
				booksFound = library.getBookByTitle(booksFound, txtSearch);
				booksFound = library.getBookByOriginalTitle(booksFound, txtSearch);
				booksFound = library.getBookByPublisherName(booksFound, txtSearch);
				booksFound = library.getBookByEdition(booksFound, txtSearch);
				booksFound = library.getBookByWriter(booksFound, txtSearch);
				booksFound = library.getBookByLanguage(booksFound, txtSearch);
				booksFound = library.getBookByGenre(booksFound, txtSearch);
				booksFound = library.getBookByNameOfWriterOriginal(booksFound, txtSearch);
				booksFound = library.getBookByBookCondition(booksFound, txtSearch);
				booksFound = library.getBookByBookOrigin(booksFound, txtSearch);
				booksFound = library.getBookByBookLocation(booksFound, txtSearch);
			}
			//adding books returned with id search
			//only execute if txtFieldIDnumber has valid data, number, not empty and less than largest id in array list
			if (txtFieldIdNumber.getText() != null && !txtFieldIdNumber.getText().isEmpty() && Integer.parseInt(txtFieldIdNumber.getText()) < library.getBooks().size() - 1) {
				booksFound.add(library.getBooks().get(Integer.parseInt(txtFieldIdNumber.getText()) - 1));
				
			}
			//if only one item found in tableview how it immediatelly in listview, otherwise clear listview
			if(booksFound.size()<2&&booksFound.size()>0){
				listViewSelectedBook.setItems(FXCollections.observableArrayList(booksFound));
			}else{
				listViewSelectedBook.getItems().clear();
			}
			booksFound.sort(Comparator.comparing(Book::getId));
			ObservableList<Book> observableList2 = FXCollections.observableList(booksFound);
			tableViewReturnedBooks.setItems(observableList2);
			
		});
	}

	private void setUpGpane() {
		//create field names in table view from Book class
		Field[] fields = Book.class.getDeclaredFields();//get all variables in Book
		for (Field f : fields) {
			TableColumn<Book, ?> columnName = new TableColumn<Book, Book>(f.getName());
			columnName.setMinWidth(110);
			columnName.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
			tableViewReturnedBooks.getColumns().add(columnName);
		}
		listViewSelectedBook.setMaxSize(600, 350);
		gPane.setHgap(5);//gap between columns
		gPane.setPadding(new Insets(20, 20, 20, 20)); //padding from all 4 sides
		gPane.getColumnConstraints().add(new ColumnConstraints(1700)); //limiting first column width
		gPane.getRowConstraints().add(new RowConstraints(700));//limiting first row height
		GridPane.setValignment(listViewSelectedBook, VPos.BOTTOM);
		GridPane.setValignment(tableViewReturnedBooks, VPos.TOP);
		GridPane.setColumnIndex(btnListAllBooks, 1);//set button to specific column
		GridPane.setConstraints(btnSearch, 1, 2);
		GridPane.setConstraints(txtFieldIdNumber, 1, 0);//set text field to specific column and row
		GridPane.setValignment(txtFieldIdNumber, VPos.BOTTOM);//align the textfieldIDnumber for id input
		GridPane.setConstraints(txtFieldBookTitle, 1, 1);
		GridPane.setValignment(txtFieldBookTitle, VPos.BOTTOM);
		gPane.getChildren().add(btnListAllBooks);
		gPane.getChildren().add(btnSearch);
		gPane.getChildren().add(txtFieldIdNumber);
		gPane.getChildren().add(txtFieldBookTitle);
		gPane.getChildren().add(tableViewReturnedBooks);
		gPane.getChildren().add(listViewSelectedBook);
		tableViewReturnedBooks.setMaxSize(1700,340);
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
			// bottom of the table got from file
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
