package rs.dud.library.util;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.TypeReference;


import rs.dud.library.model.Book;

public class WriterReader {
	public void writeToFile(ArrayList<Book>books) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationConfig.Feature.AUTO_DETECT_FIELDS, true);	
		mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.writeValue(new File("books.json"), books);
	}
	public ArrayList<Book> readFromFile() throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationConfig.Feature.AUTO_DETECT_FIELDS, true);	
		File file = new File("books.json");
		ArrayList<Book> books = mapper.readValue(file, new TypeReference <ArrayList<Book>>(){});
		return books;

	}

}
