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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class App extends Application {
	Button btnListAllBooks = new Button("List all books");
	Button btnSearch = new Button("Search");
	Button btnAddNewBook = new Button("New Book");
	TextField txtFieldIdNumber = new TextField();
	TextField txtFieldBookTitle = new TextField();

	GridPane gPane = new GridPane();
	ListView<Book> listViewSelectedBook = new ListView<Book>();
	TableView<Book> tableViewReturnedBooks = new TableView<>();
	String libraryOwner = "Svetislav";
	Library library = new Library(libraryOwner);
	Group group = new Group();
	TextField txtFieldAddBookID = new TextField("BookID");
	TextField txtFieldAddInventoryNumber = new TextField("InventoryNumber");
	TextField txtFieldAddPublisherName = new TextField("PublisherName");
	TextField txtFieldAddEdition = new TextField("Edition");
	TextField txtFieldNameOfWriterOriginal = new TextField("NameOfWriterOriginal");
	TextField txtFieldAddBookWriter = new TextField("BookWriter");
	TextField txtFieldAddTitle = new TextField("Title");
	TextField txtFieldAddLanguage = new TextField("Language");
	TextField txtFieldAddWritingSystem = new TextField("WritingSystem");
	TextField txtFieldAddGenre = new TextField("Genre");
	TextField txtFieldAddBookCondition = new TextField("BookCondition");
	TextField txtFieldAddBookOrigin = new TextField("BookOrigin");
	TextField txtFieldAddBookLocation = new TextField("BookLocation");

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

		btnAddNewBook.setOnAction(e -> {
			arrangeFieldsAddNewBook();
			group.setVisible(true);
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
	}
	//arrange txtFields for Add new book
	private void arrangeFieldsAddNewBook() {
		int x = 0;
		int y = -350;
		int count = 0;
		for(Node n:group.getChildren()){
			count++;
			n.relocate(x, y);
			y+=40;
			if(count==7){
				x=200;
				y=-350;
			}
		}
		
		
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
		GridPane.setColumnIndex(btnAddNewBook, 0);
		GridPane.setRowIndex(btnAddNewBook, 1);
		GridPane.setRowIndex(group, 1);

		GridPane.setConstraints(txtFieldIdNumber, 1, 0);//set text field to specific column and row
		GridPane.setConstraints(txtFieldBookTitle, 1, 1);
		GridPane.setConstraints(listViewSelectedBook, 0, 1);

		GridPane.setValignment(txtFieldIdNumber, VPos.BOTTOM);//align the textfieldIDnumber for id input
		GridPane.setValignment(listViewSelectedBook, VPos.BOTTOM);
		GridPane.setValignment(tableViewReturnedBooks, VPos.TOP);
		GridPane.setValignment(txtFieldBookTitle, VPos.BOTTOM);
		GridPane.setValignment(btnAddNewBook, VPos.TOP);
		GridPane.setValignment(group, VPos.CENTER);

		GridPane.setHalignment(btnAddNewBook, HPos.CENTER);
		GridPane.setHalignment(group, HPos.CENTER);
		gPane.setGridLinesVisible(true);
		gPane.getChildren().add(btnListAllBooks);
		gPane.getChildren().add(btnAddNewBook);
		gPane.getChildren().add(txtFieldIdNumber);
		gPane.getChildren().add(txtFieldBookTitle);
		gPane.getChildren().add(tableViewReturnedBooks);
		gPane.getChildren().add(listViewSelectedBook);
		gPane.getChildren().add(group);


		group.getChildren().addAll(txtFieldAddBookID, txtFieldAddInventoryNumber, txtFieldAddBookWriter, txtFieldAddPublisherName, txtFieldAddEdition, txtFieldNameOfWriterOriginal, txtFieldAddTitle,
				txtFieldAddLanguage, txtFieldAddWritingSystem, txtFieldAddGenre, txtFieldAddBookCondition, txtFieldAddBookOrigin, txtFieldAddBookLocation);
		group.setVisible(false);
		
		tableViewReturnedBooks.setMaxSize(1700, 340);
		listViewSelectedBook.setMaxSize(500, 330);
		group.setAutoSizeChildren(true);

	}
}
