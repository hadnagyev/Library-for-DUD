package rs.dud.library.main;

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

		primaryStage.setScene(new Scene(ui.gPane, 1900, 850));//size of the window
		primaryStage.show();

	}

	private ObservableList<Book> refreshBookList() {
		return FXCollections.observableList(library.getBooks());
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
