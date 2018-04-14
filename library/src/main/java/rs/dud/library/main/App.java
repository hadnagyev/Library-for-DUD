package rs.dud.library.main;

import rs.dud.library.model.Book;
import rs.dud.library.model.Library;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class App extends Application {
	Button btnListAllBooks = new Button("List all books");
	Button btnSearch = new Button("Search");
	Button btnNewBook = new Button("New Book");
	Button btnSaveNewBook = new Button("Save new book");
	Button btnPopulateFileds = new Button("Populate all fields with default values");
	Button btnDeleteSelectedBook = new Button("Delete selected book");
	TextField txtFieldIdNumber = new TextField();
	TextField txtFieldBookTitle = new TextField();

	GridPane gPane = new GridPane();
	ListView<Book> listViewSelectedBook = new ListView<Book>();
	TableView<Book> tableViewReturnedBooks = new TableView<>();
	String libraryOwner = "Svetislav";
	Library library = new Library(libraryOwner);
	Group groupAddNewBook = new Group();
	TextField txtFieldAddBookID = new TextField();
	TextField txtFieldAddInventoryNumber = new TextField();
	TextField txtFieldAddPublisherName = new TextField();
	TextField txtFieldAddYearOfPublishing = new TextField();
	TextField txtFieldAddEdition = new TextField();
	TextField txtFieldAddNameOfWriterOriginal = new TextField();
	TextField txtFieldAddBookWriter = new TextField();
	TextField txtFieldAddOriginalTitle = new TextField();
	TextField txtFieldAddTitle = new TextField();
	TextField txtFieldAddLanguage = new TextField();
	TextField txtFieldAddWritingSystem = new TextField();
	TextField txtFieldAddGenre = new TextField();
	TextField txtFieldAddBookCondition = new TextField();
	TextField txtFieldAddBookOrigin = new TextField();
	TextField txtFieldAddBookLocation = new TextField();
	
	Label lblNotValidYear = new Label("Year not valid");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(library.getLibraryOwner() + " Library");
		setUpButtons();
		setUpTableViewReaction();
		setUpTxtFields();
		setUpGpane();
		primaryStage.setScene(new Scene(gPane, 1900, 850));//size of the window
		primaryStage.show();
	}

	private void setUpTxtFields() {
		//txt field for searching books by various parameters
		txtFieldIdNumber.setPromptText("input id of the book");//initial text in the textfield
		txtFieldBookTitle.setPromptText("input search parameter");//initial text in the textfield
		//if enter gets pressed while txtfieldID has focus, button for listing that id is fired

		//Add new book txtfields setting initial text
		txtFieldAddBookID.setPromptText("ID");
		txtFieldAddInventoryNumber.setPromptText("Inventory Number");
		txtFieldAddPublisherName.setPromptText("Publisher Name");
		txtFieldAddYearOfPublishing.setPromptText("Year of publishing");
		txtFieldAddEdition.setPromptText("Edition");
		txtFieldAddNameOfWriterOriginal.setPromptText("Writer Original");
		txtFieldAddBookWriter.setPromptText("Writer");
		txtFieldAddOriginalTitle.setPromptText("Original title");
		txtFieldAddTitle.setPromptText("Title");
		txtFieldAddLanguage.setPromptText("Language");
		txtFieldAddWritingSystem.setPromptText("Writing System");
		txtFieldAddGenre.setPromptText("Genre");
		txtFieldAddBookCondition.setPromptText("Book Condition");
		txtFieldAddBookOrigin.setPromptText("Book Origin");
		txtFieldAddBookLocation.setPromptText("Book Location");

		//set up dynamic search
		txtFieldIdNumber.setOnKeyReleased(ke -> btnSearch.fire());

		txtFieldBookTitle.setOnKeyReleased(ke -> {
			btnSearch.fire();
		});
	}

	private void setUpTableViewReaction() {
		tableViewReturnedBooks.setOnMouseClicked(e -> {
			ArrayList<Book> list = new ArrayList<Book>();
			list.add(tableViewReturnedBooks.getSelectionModel().getSelectedItem());
			listViewSelectedBook.setItems(FXCollections.observableArrayList(list));
		});
	}

	private void setUpButtons() {

		// list all books button
		ObservableList<Book> observableList = FXCollections.observableList(library.getBooks());
		btnListAllBooks.setOnAction(e -> {
			tableViewReturnedBooks.setItems(observableList);
		});

		btnNewBook.setOnAction(e -> {
			arrangeFieldsAddNewBook();
			groupAddNewBook.setVisible(true);
			//getting id of the last book in library and adding 1 for the new one
			txtFieldAddBookID.setText(Integer.toString(library.getLastBook().getId() + 1));
			txtFieldAddBookID.setDisable(true);
			btnNewBook.setDisable(true);
			btnSaveNewBook.setDisable(false);
		});

		//list Book by specific id or text that user typed in txtFields
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
				booksFound.add(library.getBookByID(Integer.parseInt(txtFieldIdNumber.getText()) - 1));
				if (library.getBookByInventoryNumber(Integer.parseInt(txtFieldIdNumber.getText())) != null) {
					booksFound.add(library.getBookByInventoryNumber(Integer.parseInt(txtFieldIdNumber.getText())));
				}
			}

			//if only one item found in tableview how it immediatelly in listview, otherwise clear listview
			if (booksFound.size() < 2 && booksFound.size() > 0) {
				listViewSelectedBook.setItems(FXCollections.observableArrayList(booksFound));
			} else {
				listViewSelectedBook.getItems().clear();
			}
			booksFound.sort(Comparator.comparing(Book::getId));
			ObservableList<Book> observableList2 = FXCollections.observableList(booksFound);
			tableViewReturnedBooks.setItems(observableList2);

		});

		btnSaveNewBook.setOnAction(e -> {
			defaultValueOnAddNew();
			if(saveNewBook()){
				setUpButtons();
				btnListAllBooks.fire();
				btnNewBook.setDisable(false);
				btnSaveNewBook.setDisable(true);
			};

		});

		btnPopulateFileds.setOnAction(e -> {
			defaultValueOnAddNew();
		});

		btnDeleteSelectedBook.setOnAction(e -> {
			deleteSelectedBook();
			setUpButtons();
			btnListAllBooks.fire();
		});
		//hide warning for invalid year
		txtFieldAddYearOfPublishing.setOnMouseClicked(e ->{
			lblNotValidYear.setVisible(false);
		});
	}

	private void deleteSelectedBook() {
		listViewSelectedBook.getSelectionModel().selectFirst();
		Book book = listViewSelectedBook.getSelectionModel().getSelectedItem();
		listViewSelectedBook.getSelectionModel().clearSelection();
		library.deleteBook(book.getId() - 1);
		btnListAllBooks.fire();
	}

	private void defaultValueOnAddNew() {
		if (txtFieldAddInventoryNumber.getText().isEmpty()) {
			txtFieldAddInventoryNumber.setText("0");
		}

	}

	private boolean saveNewBook() {
		int id = Integer.parseInt(txtFieldAddBookID.getText());
		int inventoryNumber = Integer.parseInt(txtFieldAddInventoryNumber.getText());
		String publisherName = txtFieldAddPublisherName.getText();
		int yearOfPublishing = Integer.parseInt(txtFieldAddYearOfPublishing.getText());
		String edition = txtFieldAddEdition.getText();
		String nameOfWriterOriginal = txtFieldAddNameOfWriterOriginal.getText();
		String writer = txtFieldAddBookWriter.getText();
		String originalTitle = txtFieldAddOriginalTitle.getText();
		String title = txtFieldAddTitle.getText();
		String language = txtFieldAddLanguage.getText();
		String writingSystem = txtFieldAddWritingSystem.getText();
		String genre = txtFieldAddGenre.getText();
		String bookCondition = txtFieldAddBookCondition.getText();
		String bookOrigin = txtFieldAddBookOrigin.getText();
		String bookLocation = txtFieldAddBookLocation.getText();

		Book newBook = new Book(id, inventoryNumber, publisherName, yearOfPublishing, edition, nameOfWriterOriginal, writer, originalTitle, title, language, writingSystem, genre, bookCondition,
				bookOrigin, bookLocation);
		library.getBooks().add(newBook);
		return true;
	}

	//arrange txtFields for Add new book
	private void arrangeFieldsAddNewBook() {
		//starting positions
		int x = 0;
		int y = -350;
		//counter for rows, every nth item reset y and ofset x value
		int count = 0;

		for (Node n : groupAddNewBook.getChildren()) {
			count++;
			n.relocate(x, y);
			y += 40;
			if (count == 8) {
				x = 200;
				y = -350;
			}
		}
		lblNotValidYear.relocate(80, -185);
		lblNotValidYear.setTextFill(Paint.valueOf("red"));

	}

	private void setUpGpane() {
		//create field names in table view from Book class
		Field[] fields = Book.class.getDeclaredFields();//get all variables in Book
		for (Field f : fields) {
			TableColumn<Book, Book> columnName = new TableColumn<Book, Book>(f.getName());
			columnName.setMinWidth(110);
			columnName.setCellValueFactory(new PropertyValueFactory<>(f.getName()));
			tableViewReturnedBooks.getColumns().add(columnName);
		}
		listViewSelectedBook.setMaxSize(600, 350);
		gPane.setHgap(5);//gap between columns
		gPane.setPadding(new Insets(20, 20, 20, 20)); //padding from all 4 sides
		gPane.getColumnConstraints().add(new ColumnConstraints(1700)); //limiting first column width
		gPane.getRowConstraints().add(new RowConstraints(400));//limiting first row height
		gPane.getRowConstraints().add(new RowConstraints(400));
		GridPane.setColumnIndex(btnListAllBooks, 1);//set button to specific column
		GridPane.setColumnIndex(btnNewBook, 0);
		GridPane.setColumnIndex(btnDeleteSelectedBook, 1);
		GridPane.setRowIndex(btnNewBook, 1);
		GridPane.setRowIndex(groupAddNewBook, 1);
		GridPane.setRowIndex(btnDeleteSelectedBook, 1);

		GridPane.setConstraints(txtFieldIdNumber, 1, 0);//set text field to specific column and row
		GridPane.setConstraints(txtFieldBookTitle, 1, 1);
		GridPane.setConstraints(listViewSelectedBook, 0, 1);

		GridPane.setValignment(txtFieldIdNumber, VPos.BOTTOM);//align the textfieldIDnumber for id input
		GridPane.setValignment(listViewSelectedBook, VPos.BOTTOM);
		GridPane.setValignment(tableViewReturnedBooks, VPos.TOP);
		GridPane.setValignment(txtFieldBookTitle, VPos.BOTTOM);
		GridPane.setValignment(btnNewBook, VPos.TOP);
		GridPane.setValignment(groupAddNewBook, VPos.CENTER);
		GridPane.setValignment(btnDeleteSelectedBook, VPos.CENTER);

		GridPane.setHalignment(btnNewBook, HPos.CENTER);
		GridPane.setHalignment(groupAddNewBook, HPos.CENTER);
		gPane.setGridLinesVisible(true);
		gPane.getChildren().add(btnListAllBooks);
		gPane.getChildren().add(btnNewBook);
		gPane.getChildren().add(txtFieldIdNumber);
		gPane.getChildren().add(btnDeleteSelectedBook);
		gPane.getChildren().add(txtFieldBookTitle);
		gPane.getChildren().add(tableViewReturnedBooks);
		gPane.getChildren().add(listViewSelectedBook);
		gPane.getChildren().add(groupAddNewBook);

		groupAddNewBook.getChildren().addAll(txtFieldAddBookID, txtFieldAddInventoryNumber, txtFieldAddBookWriter, txtFieldAddPublisherName, txtFieldAddYearOfPublishing, txtFieldAddEdition,
				txtFieldAddNameOfWriterOriginal, txtFieldAddTitle, txtFieldAddLanguage, txtFieldAddWritingSystem, txtFieldAddGenre, txtFieldAddBookCondition, txtFieldAddBookOrigin,
				txtFieldAddBookLocation, btnSaveNewBook, btnPopulateFileds,lblNotValidYear);
		groupAddNewBook.setVisible(false);
		btnSaveNewBook.setDisable(true);
		lblNotValidYear.setVisible(false);

		tableViewReturnedBooks.setMaxSize(1700, 340);
		listViewSelectedBook.setMaxSize(500, 330);
		groupAddNewBook.setAutoSizeChildren(true);

	}
}
