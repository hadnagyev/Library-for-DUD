package rs.dud.library.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.List;
import java.util.Scanner;
import java.lang.reflect.Type;
import java.nio.file.Files;

import java.nio.file.attribute.BasicFileAttributes;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import rs.dud.library.model.Book;

public class Database {
	static String path = System.getProperty("user.dir");
	static String booksFile;
	static List<Book> books;

	public interface newOrOld {
		static String NEWFILE = "new";
		static String OLDFILE = "old";
	}

	public static void writeToFile(List<Book> books) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		//Object to JSON in file
		mapper.writeValue(new File(path + findOldestOrNewestFile(newOrOld.OLDFILE)), books);
	}

	public static String findOldestOrNewestFile(String ageOfFile) {
		String oldest = "";
		String newest = "";
		try {
			Long books1 = Files.readAttributes(new File(path + "/books1.json").toPath(), BasicFileAttributes.class).lastModifiedTime().toMillis();
			Long books2 = Files.readAttributes(new File(path + "/books2.json").toPath(), BasicFileAttributes.class).lastModifiedTime().toMillis();
			Long books3 = Files.readAttributes(new File(path + "/books3.json").toPath(), BasicFileAttributes.class).lastModifiedTime().toMillis();
			if (books1 > books2) {
				if (books2 > books3) {
					oldest = "books3";
					newest = "books1";
				} else {
					oldest = "books2";
					if (books1 > books3) {
						newest = "books1";
					} else {
						newest = "books3";
					}
				}
			} else {
				oldest = "books1";
				if (books1 > books3) {
					newest = "books2";
					oldest = "books3";
				} else {
					oldest = "books1";
					if (books2 > books3) {
						newest = "books2";
					} else {
						newest = "books3";
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ageOfFile.equals(newOrOld.OLDFILE) == true) {
			return "/" + oldest + ".json";
		} else {
			return "/" + newest + ".json";
		}

	}

	public static List<Book> loadFromFile() throws JsonParseException, JsonMappingException, IOException {
		findOldestOrNewestFile(newOrOld.NEWFILE);
		try {
			Scanner scanner = new Scanner(new File(path + findOldestOrNewestFile(newOrOld.NEWFILE)));
			booksFile = scanner.useDelimiter("\\Z").next();
			scanner.close();
		} catch (FileNotFoundException e) {
			LoadFromFile lf = new LoadFromFile();
			books = lf.getTempLibrary();
			writeToFile(books);
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(new File(path + "/books1.json"), books);
			mapper.writeValue(new File(path + "/books2.json"), books);
			mapper.writeValue(new File(path + "/books3.json"), books);
			return books;
		}

		Gson gson = new Gson();
		Type type = new TypeToken<List<Book>>() {
		}.getType();
		books = gson.fromJson(booksFile, type);
		return books;

	}
}
