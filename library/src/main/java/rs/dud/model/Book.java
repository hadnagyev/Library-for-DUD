package rs.dud.model;

import java.time.Year;

public class Book {
	int id; 						//redni broj in excel file
	int inventoryNumber; 			//inv. broj 
	String publisherName; 			//Izdavac 
	Year yearOfPublishing;			//godina izdavanja 
	String edition;					//edicija 
	String nameOfWriterOriginal; 	// ime pisca izvorno 
	String writer;					//pisac 
	String originalTitle; 			// naslov originala 
	String title;					//naslov 
	String language;				//jezik 
	String writingSystem;			//pismo (cyrylic, latin etc.)
	String Genre;					//zanr
	String bookCondition;			//stanje knjige
	String bookOrigin;				//poreklo knjige
	String bookLocation;			//gde se nalazi
	
}
