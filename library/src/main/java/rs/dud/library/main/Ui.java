package rs.dud.library.main;

import java.lang.reflect.Field;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
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
import rs.dud.library.model.Book;

public class Ui {
	Button btnListAllBooks = new Button("List all books");
	Button btnSearch = new Button("Search");
	Button btnNewBook = new Button("New Book");
	Button btnSaveNewBook = new Button("Save new book");
	Button btnPopulateFileds = new Button("Populate all fields with default values");
	Button btnDeleteSelectedBook = new Button("Delete selected book");

	TextField txtFieldIdNumber = new TextField();
	TextField txtFieldBookTitle = new TextField();

	TextField txtFieldAddBookID = new TextField();
	TextField txtFieldAddInventoryNumber = new TextField();
	TextField txtFieldAddPublisherName = new TextField();
	TextField txtFieldAddYearOfPublishing = new TextField();
	TextField txtFieldAddEdition = new TextField();
	TextField txtFieldAddNameOfWriterOriginal = new TextField();

	Group groupAddNewBook = new Group();

	public Ui() {
		super();
		setUpTableViewReaction();
		setUpTxtFields();
		setUpGpane();
	}

	public Ui(Button btnListAllBooks, Button btnSearch, Button btnNewBook, Button btnSaveNewBook, Button btnPopulateFileds, Button btnDeleteSelectedBook, TextField txtFieldIdNumber,
			TextField txtFieldBookTitle, TextField txtFieldAddBookID, TextField txtFieldAddInventoryNumber, TextField txtFieldAddPublisherName, TextField txtFieldAddYearOfPublishing,
			TextField txtFieldAddEdition, TextField txtFieldAddNameOfWriterOriginal, TextField txtFieldAddBookWriter, TextField txtFieldAddOriginalTitle, TextField txtFieldAddTitle,
			TextField txtFieldAddLanguage, TextField txtFieldAddWritingSystem, TextField txtFieldAddGenre, TextField txtFieldAddBookCondition, TextField txtFieldAddBookOrigin,
			TextField txtFieldAddBookLocation, Label lblNotValidYear, GridPane gPane, ListView<Book> listViewSelectedBook, TableView<Book> tableViewReturnedBooks) {
		super();
		this.btnListAllBooks = btnListAllBooks;
		this.btnSearch = btnSearch;
		this.btnNewBook = btnNewBook;
		this.btnSaveNewBook = btnSaveNewBook;
		this.btnPopulateFileds = btnPopulateFileds;
		this.btnDeleteSelectedBook = btnDeleteSelectedBook;
		this.txtFieldIdNumber = txtFieldIdNumber;
		this.txtFieldBookTitle = txtFieldBookTitle;
		this.txtFieldAddBookID = txtFieldAddBookID;
		this.txtFieldAddInventoryNumber = txtFieldAddInventoryNumber;
		this.txtFieldAddPublisherName = txtFieldAddPublisherName;
		this.txtFieldAddYearOfPublishing = txtFieldAddYearOfPublishing;
		this.txtFieldAddEdition = txtFieldAddEdition;
		this.txtFieldAddNameOfWriterOriginal = txtFieldAddNameOfWriterOriginal;
		this.txtFieldAddBookWriter = txtFieldAddBookWriter;
		this.txtFieldAddOriginalTitle = txtFieldAddOriginalTitle;
		this.txtFieldAddTitle = txtFieldAddTitle;
		this.txtFieldAddLanguage = txtFieldAddLanguage;
		this.txtFieldAddWritingSystem = txtFieldAddWritingSystem;
		this.txtFieldAddGenre = txtFieldAddGenre;
		this.txtFieldAddBookCondition = txtFieldAddBookCondition;
		this.txtFieldAddBookOrigin = txtFieldAddBookOrigin;
		this.txtFieldAddBookLocation = txtFieldAddBookLocation;
		this.lblNotValidYear = lblNotValidYear;
		this.gPane = gPane;
		this.listViewSelectedBook = listViewSelectedBook;
		this.tableViewReturnedBooks = tableViewReturnedBooks;

		setUpTableViewReaction();
		setUpTxtFields();
		setUpGpane();
	}

	private void setUpTableViewReaction() {
		tableViewReturnedBooks.setOnMouseClicked(e -> {
			ArrayList<Book> list = new ArrayList<Book>();
			list.add(tableViewReturnedBooks.getSelectionModel().getSelectedItem());
			//library.setBooks(list);
			listViewSelectedBook.setItems(FXCollections.observableArrayList(list));
		});
	}

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

	GridPane gPane = new GridPane();
	ListView<Book> listViewSelectedBook = new ListView<Book>();
	TableView<Book> tableViewReturnedBooks = new TableView<>();

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
				txtFieldAddBookLocation, btnSaveNewBook, btnPopulateFileds, lblNotValidYear);
		groupAddNewBook.setVisible(false);
		btnSaveNewBook.setDisable(true);
		lblNotValidYear.setVisible(false);

		tableViewReturnedBooks.setMaxSize(1700, 340);
		listViewSelectedBook.setMaxSize(500, 330);
		groupAddNewBook.setAutoSizeChildren(true);

	}

	//arrange txtFields for Add new book
	public void arrangeFieldsAddNewBook() {
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
}
