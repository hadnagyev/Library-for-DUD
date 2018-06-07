package rs.dud.library.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;

import rs.dud.library.model.Book;

public class Database {
	static String path = System.getProperty("user.dir");
	static String booksFile;
	static List<Book> books;

	public interface newOrOld {
		static String NEWFILE = "new";
		static String OLDFILE = "old";
	}

	public static void writeToFile(List<Book> books) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		//Object to JSON in file
		mapper.writeValue(new File(path + findOldestOrNewestFile(newOrOld.OLDFILE)), books);

	}

	//TODO make this return file instead of string as the name suggests
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

	public static List<Book> loadFromFile() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		findOldestOrNewestFile(newOrOld.NEWFILE);
		Gson gson = new Gson();
		//		Type type = new TypeToken<List<Book>>() {
		//		}.getType();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path + findOldestOrNewestFile(newOrOld.NEWFILE)), "UTF-8"));
			booksFile = in.readLine();
			in.close();

		} catch (FileNotFoundException e) {
			LoadFromFile lf = new LoadFromFile();
			books = lf.getTempLibrary();

			mapper.writeValue(new File(path + "/books1.json"), books);
			mapper.writeValue(new File(path + "/books2.json"), books);
			mapper.writeValue(new File(path + "/books3.json"), books);
			return books;
		} catch (Exception e) {

		}

		books = Arrays.asList(gson.fromJson(booksFile, Book[].class));
		return books;

	}
}
