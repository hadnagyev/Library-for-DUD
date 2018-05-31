package rs.dud.library.model;


public class Book extends Entity {
	/*
	 * id is redni broj inventoryNumber is inv. broj in excel file
	 */
	String publisherName; // Izdavac
	int yearOfPublishing; // godina izdavanja
	String edition; // edicija
	String nameOfWriterOriginal; // ime pisca izvorno
	String writer; // pisac
	String originalTitle; // naslov originala
	String title; // naslov
	String language; // jezik
	String writingSystem; // pismo (cyrylic, latin etc.)
	String genre; // zanr
	String bookCondition; // stanje knjige
	String bookOrigin; // poreklo knjige
	String bookLocation; // gde se nalazi

	public Book(int id, int inventoryNumber, String publisherName, int yearOfPublishing, String edition, String nameOfWriterOriginal, String writer, String originalTitle, String title,
			String language, String writingSystem, String genre, String bookCondition, String bookOrigin, String bookLocation) {
		super(id,inventoryNumber);
		this.publisherName = publisherName;
		this.yearOfPublishing = yearOfPublishing;
		this.edition = edition;
		this.nameOfWriterOriginal = nameOfWriterOriginal;
		this.writer = writer;
		this.originalTitle = originalTitle;
		this.title = title;
		this.language = language;
		this.writingSystem = writingSystem;
		this.genre = genre;
		this.bookCondition = bookCondition;
		this.bookOrigin = bookOrigin;
		this.bookLocation = bookLocation;
	}

	@Override
	public String toString() {
		String ls = System.getProperty("line.separator");
		return "id=" + id + ls + " inventoryNumber = " + inventoryNumber + ls + " publisherName = " + publisherName + ls + " yearOfPublishing = " + yearOfPublishing + ls + " edition = "
				+ edition + ls + " nameOfWriterOriginal = " + nameOfWriterOriginal + ls + " writer = " + writer + ls + " originalTitle = " + originalTitle + ls + " title = " + title + ls
				+ " language = " + language + ls + " writingSystem = " + writingSystem + ls + " Genre = " + genre + ls + " bookCondition = " + bookCondition + ls + " bookOrigin = " + bookOrigin + ls
				+ " bookLocation = " + bookLocation;
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

	public int getYearOfPublishing() {
		return yearOfPublishing;
	}

	public void setYearOfPublishing(int yearOfPublishing) {
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
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
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
