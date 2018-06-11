package rs.dud.library.main;

import java.util.ArrayList;
import java.util.Comparator;

import javax.annotation.Resource;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import rs.dud.library.model.Book;

public class Buttons {

	@Resource
	Ui ui;
	Button btnListAllBooks = new Button("List all books");
	Button btnSearch = new Button("Search");
	Button btnNewBook = new Button("New Book");
	Button btnSaveNewBook = new Button("Save new book");
	Button btnPopulateFileds = new Button("Populate all fields with default values");
	Button btnDeleteSelectedBook = new Button("Delete selected book");

	private void setUpButtons() {

		btnListAllBooks.setOnAction(e -> {
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
				if (txtSearch.matches("-?\\d+")) {
					Book tempBook = library.getBookByID(Integer.parseInt(txtSearch));
					if (tempBook != null) {
						booksFound.add(tempBook);
					}
				}
				if (txtSearch.matches("-?\\d+")) {
					booksFound = library.getBookByYearOfPublishing(Integer.parseInt(txtSearch));
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
}
