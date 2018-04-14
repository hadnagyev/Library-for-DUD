package rs.dud.library.util;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.reflect.Type;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import rs.dud.library.model.Book;

public class Database {

	public static void writeToFile(ArrayList<Book> books) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		//Object to JSON in file
		mapper.writeValue(new File("F:\\user.json"), books);
	}
	
	public static List<Book> loadFromFile() throws JsonParseException, JsonMappingException, IOException{
		@SuppressWarnings("resource")
		String booksFile = new Scanner(new File("F:\\user.json")).useDelimiter("\\Z").next();

		Gson gson = new Gson();
        Type type = new TypeToken<List<Book>>() {}.getType();
        List<Book> books = gson.fromJson(booksFile, type);
        for(Book b:books){
        	System.out.println(b.getId());
        }
        return books;
        
	}
}
