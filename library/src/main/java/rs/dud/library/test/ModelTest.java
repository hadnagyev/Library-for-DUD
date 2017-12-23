package rs.dud.library.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import rs.dud.library.model.Library;

public class ModelTest {

	@Test
	public void libraryCreationTest() {
		ArrayList<String> addresses = new ArrayList<String>();
		addresses.add("Sonje Marinkovic 33");
//		Library library = new Library("Svetislav", addresses);
//		assertEquals("Svetislav", library.getLibraryOwner());
//		assertEquals("Sonje Marinkovic 33", library.getLibraryAddresses().get(0));
	}

}
