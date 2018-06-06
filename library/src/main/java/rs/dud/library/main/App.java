package rs.dud.library.main;

import java.util.ArrayList;
import java.util.Comparator;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rs.dud.library.model.Book;
import rs.dud.library.model.Library;
import rs.dud.library.util.Database;
import rs.dud.library.util.LoadFromFile;

public class App extends Application {
	Ui ui;
	String libraryOwner = "Svetislav";
	ObservableList<Book> observableList;
	Library library;

	public static void main(String[] args) {
		launch(args);
	}

	public void getBooksFromLegacy() {
		LoadFromFile lf = new LoadFromFile();
		library = new Library(lf.getTempLibrary(), libraryOwner);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ui = new Ui();
		getBooksFromLegacy();//use to get books from legacy doc file
		primaryStage.setTitle(library.getLibraryOwner() + " Library");
		library.setBooks(Database.loadFromFile());
		setUpButtons();
		primaryStage.setScene(new Scene(ui.gPane, 1900, 850));//size of the window
		primaryStage.show();

	}

	private ObservableList<Book> refreshBookList() {
		return FXCollections.observableList(library.getBooks());
	}

	private void setUpButtons() {

		// list all books button
		observableList = refreshBookList();
		ui.btnListAllBooks.setOnAction(e -> {
			ui.tableViewReturnedBooks.setItems(observableList);
		});

		ui.btnNewBook.setOnAction(e -> {
			ui.arrangeFieldsAddNewBook();
			ui.groupAddNewBook.setVisible(true);
			//getting id of the last book in library and adding 1 for the new one
			ui.txtFieldAddBookID.setText(Integer.toString(library.getLastBook().getId() + 1));
			ui.txtFieldAddBookID.setDisable(true);
			ui.btnNewBook.setDisable(true);
			ui.btnSaveNewBook.setDisable(false);
		});

		//list Book by specific id or text that user typed in txtFields
		ui.btnSearch.setOnAction(e -> {
			ArrayList<Book> booksFound = new ArrayList<Book>();
			//only execute if txtfieldTitle has value
			String txtSearch = ui.txtFieldBookTitle.getText();
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
			if (ui.txtFieldIdNumber.getText().matches("-?\\d+") && ui.txtFieldIdNumber.getText() != null && !ui.txtFieldIdNumber.getText().isEmpty()
					&& Integer.parseInt(ui.txtFieldIdNumber.getText()) < library.getBooks().size() - 1) {
				booksFound.add(library.getBookByID(Integer.parseInt(ui.txtFieldIdNumber.getText()) - 1));
				if (library.getBookByInventoryNumber(Integer.parseInt(ui.txtFieldIdNumber.getText())) != null) {
					booksFound.add(library.getBookByInventoryNumber(Integer.parseInt(ui.txtFieldIdNumber.getText())));
				}
			}

			//if only one item found in tableview how it immediatelly in listview, otherwise clear listview
			if (booksFound.size() < 2 && booksFound.size() > 0) {
				ui.listViewSelectedBook.setItems(FXCollections.observableArrayList(booksFound));
			} else {
				ui.listViewSelectedBook.getItems().clear();
			}
			booksFound.sort(Comparator.comparing(Book::getId));
			ObservableList<Book> observableList2 = FXCollections.observableList(booksFound);
			ui.tableViewReturnedBooks.setItems(observableList2);

		});

		ui.btnSaveNewBook.setOnAction(e -> {
			defaultValueOnAddNew();
			if (saveNewBook()) {
				setUpButtons();
				ui.btnListAllBooks.fire();
				ui.btnNewBook.setDisable(false);
				ui.btnSaveNewBook.setDisable(true);
				writeChangesToFile();
			}
			;

		});

		ui.btnPopulateFileds.setOnAction(e -> {
			defaultValueOnAddNew();
		});

		ui.btnDeleteSelectedBook.setOnAction(e -> {
			try {
				deleteSelectedBook();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			setUpButtons();
			writeChangesToFile();
			ui.btnListAllBooks.fire();
		});
		//hide warning for invalid year
		ui.txtFieldAddYearOfPublishing.setOnMouseClicked(e -> {
			ui.lblNotValidYear.setVisible(false);
		});
	}

	private void writeChangesToFile() {
		try {
			Database.writeToFile(library.getBooks());
		} catch (JsonGenerationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void deleteSelectedBook() throws Exception {
		ui.listViewSelectedBook.getSelectionModel().selectFirst();
		Book book = ui.listViewSelectedBook.getSelectionModel().getSelectedItem();
		ui.listViewSelectedBook.getSelectionModel().clearSelection();

		library.deleteBook(book);
		Database.writeToFile(library.getBooks());
		ui.btnListAllBooks.fire();
	}

	private void defaultValueOnAddNew() {
		if (ui.txtFieldAddInventoryNumber.getText().isEmpty()) {
			ui.txtFieldAddInventoryNumber.setText("0");
		}
		if (ui.txtFieldAddYearOfPublishing.getText().isEmpty()) {
			ui.txtFieldAddYearOfPublishing.setText("0");
		}

	}

	private boolean saveNewBook() {
		int id = Integer.parseInt(ui.txtFieldAddBookID.getText());
		int inventoryNumber = Integer.parseInt(ui.txtFieldAddInventoryNumber.getText());
		String publisherName = ui.txtFieldAddPublisherName.getText();
		int yearOfPublishing = Integer.parseInt(ui.txtFieldAddYearOfPublishing.getText());
		String edition = ui.txtFieldAddEdition.getText();
		String nameOfWriterOriginal = ui.txtFieldAddNameOfWriterOriginal.getText();
		String writer = ui.txtFieldAddBookWriter.getText();
		String originalTitle = ui.txtFieldAddOriginalTitle.getText();
		String title = ui.txtFieldAddTitle.getText();
		String language = ui.txtFieldAddLanguage.getText();
		String writingSystem = ui.txtFieldAddWritingSystem.getText();
		String genre = ui.txtFieldAddGenre.getText();
		String bookCondition = ui.txtFieldAddBookCondition.getText();
		String bookOrigin = ui.txtFieldAddBookOrigin.getText();
		String bookLocation = ui.txtFieldAddBookLocation.getText();

		Book newBook = new Book(id, inventoryNumber, publisherName, yearOfPublishing, edition, nameOfWriterOriginal, writer, originalTitle, title, language, writingSystem, genre, bookCondition,
				bookOrigin, bookLocation);
		library.addBook(newBook);
		return true;
	}

}
