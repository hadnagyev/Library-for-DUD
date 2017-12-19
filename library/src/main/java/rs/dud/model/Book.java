package rs.dud.model;

import java.time.Year;

public class Book {
	int id; // redni broj in excel file
	int inventoryNumber; // inv. broj
	String publisherName; // Izdavac
	Year yearOfPublishing; // godina izdavanja
	String edition; // edicija
	String nameOfWriterOriginal; // ime pisca izvorno
	String writer; // pisac
	String originalTitle; // naslov originala
	String title; // naslov
	String language; // jezik
	String writingSystem; // pismo (cyrylic, latin etc.)
	String Genre; // zanr
	String bookCondition; // stanje knjige
	String bookOrigin; // poreklo knjige
	String bookLocation; // gde se nalazi

	public Book(int id, int inventoryNumber, String publisherName, Year yearOfPublishing, String edition,
			String nameOfWriterOriginal, String writer, String originalTitle, String title, String language,
			String writingSystem, String genre, String bookCondition, String bookOrigin, String bookLocation) {
		super();
		this.id = id;
		this.inventoryNumber = inventoryNumber;
		this.publisherName = publisherName;
		this.yearOfPublishing = yearOfPublishing;
		this.edition = edition;
		this.nameOfWriterOriginal = nameOfWriterOriginal;
		this.writer = writer;
		this.originalTitle = originalTitle;
		this.title = title;
		this.language = language;
		this.writingSystem = writingSystem;
		Genre = genre;
		this.bookCondition = bookCondition;
		this.bookOrigin = bookOrigin;
		this.bookLocation = bookLocation;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", inventoryNumber=" + inventoryNumber + ", publisherName=" + publisherName
				+ ", yearOfPublishing=" + yearOfPublishing + ", edition=" + edition + ", nameOfWriterOriginal="
				+ nameOfWriterOriginal + ", writer=" + writer + ", originalTitle=" + originalTitle + ", title=" + title
				+ ", language=" + language + ", writingSystem=" + writingSystem + ", Genre=" + Genre
				+ ", bookCondition=" + bookCondition + ", bookOrigin=" + bookOrigin + ", bookLocation=" + bookLocation
				+ "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInventoryNumber() {
		return inventoryNumber;
	}

	public void setInventoryNumber(int inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public Year getYearOfPublishing() {
		return yearOfPublishing;
	}

	public void setYearOfPublishing(Year yearOfPublishing) {
		this.yearOfPublishing = yearOfPublishing;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getNameOfWriterOriginal() {
		return nameOfWriterOriginal;
	}

	public void setNameOfWriterOriginal(String nameOfWriterOriginal) {
		this.nameOfWriterOriginal = nameOfWriterOriginal;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getWritingSystem() {
		return writingSystem;
	}

	public void setWritingSystem(String writingSystem) {
		this.writingSystem = writingSystem;
	}

	public String getGenre() {
		return Genre;
	}

	public void setGenre(String genre) {
		Genre = genre;
	}

	public String getBookCondition() {
		return bookCondition;
	}

	public void setBookCondition(String bookCondition) {
		this.bookCondition = bookCondition;
	}

	public String getBookOrigin() {
		return bookOrigin;
	}

	public void setBookOrigin(String bookOrigin) {
		this.bookOrigin = bookOrigin;
	}

	public String getBookLocation() {
		return bookLocation;
	}

	public void setBookLocation(String bookLocation) {
		this.bookLocation = bookLocation;
	}

}
