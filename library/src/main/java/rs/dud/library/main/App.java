package rs.dud.library.main;

import rs.dud.library.model.Book;
import rs.dud.library.model.Library;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
	ArrayList<String> readFromFile = new ArrayList<String>();
	ArrayList<Book> tempLibrary = new ArrayList<Book>();
	String file = "POPIS KNJIGA najnoviji.txt";

	public static void main(String[] args) throws IOException, UnsupportedEncodingException {
		launch(args);
	}
	
	Button btnList = new Button("List all books");
	Button btnListID = new Button("List book by id");
	TextField txtFieldIdNumber = new TextField();
	GridPane gPane = new GridPane();
	ListView<Book> listView = new ListView<>();

	@Override
	public void start(Stage primaryStage) throws Exception {
		App app = new App();
		Library library = new Library(app.tempLibrary, "Svetislav");// TODO hard coded library owner, fix it, maybe not even needed
		app.readFromFile();//populate arraylist from txt file provided
		app.fillBooksToLibrary(app.findFirstEntry()); //find row of the first entry
		primaryStage.setTitle(library.getLibraryOwner() + " Library");

		setUpButtons(library); 
		setUpTxtFields();
		setUpGpane(gPane,listView,btnListID,txtFieldIdNumber);
		primaryStage.setScene(new Scene(gPane, 1000, 800));//size of the window
		primaryStage.show();
	}


	private void setUpTxtFields() {
		//txt field for searching books by id
		txtFieldIdNumber.setPromptText("input id of the book");//initial text in the textfield
		//if enter gets pressed while txtfieldID has focus button for listing that id is pressed
		txtFieldIdNumber.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {

	            		btnListID.fire();

	                
	            }
	        }
	    });
		
	}


	private void setUpButtons(Library library) {
		// list all books button
		
		btnList.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {

				if (txtFieldIdNumber.getText(0,9) != null && !txtFieldIdNumber.getText().isEmpty()){
					System.out.println(library.getBooks().size()-1);
					ObservableList<Book> observableList = FXCollections.observableList(library.getBooks());
					listView.setItems(observableList);
					
				} 
			}
		});
		//list Book by specific id that user typed in txtFieldIdNumber
		btnListID.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				if (txtFieldIdNumber.getText() != null && !txtFieldIdNumber.getText().isEmpty()&&Integer.parseInt(txtFieldIdNumber.getText())<library.getBooks().size()-1) {
					ArrayList<Book> bookByID = new ArrayList<Book>(); //TODO making array list instead of sending only one item
					bookByID.add(library.getBooks().get(Integer.parseInt(txtFieldIdNumber.getText()) - 1));
					ObservableList<Book> observableList = FXCollections.observableList(bookByID);
					listView.setItems(observableList);
				} else {
					//TODO show some error for searching book with id greater than in database
				}
			}
		});
		
	}


	private void setUpGpane(GridPane gPane,ListView<Book> listView,Button btnListID,TextField txtFieldIdNumber) {
		gPane.setHgap(5);//gap between columns
		gPane.getChildren().add(listView);
		gPane.setPadding(new Insets(20, 20, 20, 20)); //padding from all 4 sides
		gPane.getColumnConstraints().add(new ColumnConstraints(800)); //limiting first column width
		gPane.getRowConstraints().add(new RowConstraints(700));//limiting first row height
		GridPane.setColumnIndex(btnList, 1);//set button to specific column
		GridPane.setConstraints(btnListID, 1, 2);
		GridPane.setConstraints(txtFieldIdNumber, 1, 0);//set text field to specific column and row
		GridPane.setValignment(txtFieldIdNumber, VPos.BOTTOM);//align the textfield for id input
		gPane.getChildren().add(btnList);
		gPane.getChildren().add(btnListID);
		gPane.getChildren().add(txtFieldIdNumber);
		listView.setMaxSize(1000, 1000);
		StackPane.setAlignment(btnList, Pos.CENTER_LEFT);//align the button for list all books
		
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
