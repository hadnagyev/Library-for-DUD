package rs.dud.library.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.reflect.Type;
import java.nio.file.Path;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import rs.dud.library.model.Book;

public class Database {
	static String path = System.getProperty("user.dir");
	static String booksFile;
	static List<Book> books;

	public static void writeToFile(List<Book> books) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		//Object to JSON in file
		mapper.writeValue(new File(path + "/books.json"), books);
	}

	public static List<Book> loadFromFile() throws JsonParseException, JsonMappingException, IOException {

		try {
			booksFile = new Scanner(new File(path + "/books.json")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			LoadFromFile lf = new LoadFromFile();
			books = lf.getTempLibrary();
			writeToFile(books);
			return books;
		}

		Gson gson = new Gson();
		Type type = new TypeToken<List<Book>>() {
		}.getType();
		books = gson.fromJson(booksFile, type);
		for (Book b : books) {
			System.out.println(b.getId());
		}
		return books;

	}
}
