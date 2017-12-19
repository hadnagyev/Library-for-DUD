package test;

import static org.junit.Assert.*;

import org.junit.Test;
import rs.dud.model.Library;

public class ModelTest {

	@Test
	public void libraryCreationTest() {
		Library library = new Library("Svetislav", "Sonje Marinkovic 33");
		assertEquals("Svetislav", library.getLibraryOwner());
		assertEquals("Sonje Marinkovic 33", library.getLibraryAddresses());
	}

}
