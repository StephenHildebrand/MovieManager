/**
 * 
 */
package net.shiild.java.flix.inventory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Movie.
 * 
 * @author StephenHildebrand
 */
public class MovieTest {
	/** Valid movie object with 1 copy in stock */
	Movie mValid;
	/** Valid movie object with negative stock */
	Movie mNegativeStock;
	/** Valid movie object with zero stock */
	Movie mZeroStock;

	/**
	 * Sets up the movie objects prior to use by the test methods.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Initialize each valid movie object with appropriate parameters
		mValid = new Movie("1 Movie Name");
		mNegativeStock = new Movie("-1 Name");
		mZeroStock = new Movie("0 Name");
	}

	/**
	 * Test method for
	 * {@link Movie#Movie(java.lang.String)}.
	 */
	@Test
	public void testMovie() {
		// Initialize each invalid movie object
		Movie mNull = null;
		Movie mNoStock = null;
		Movie mInvalidStock = null;
		Movie mNoStockNumberInTitle = null;

		// Valid movie
		assertTrue(mValid.isAvailable());
		assertEquals("Movie Name", mValid.getName());
		mValid.removeOneCopyFromInventory();
		// Remove a copy of movieValid and check that it is now unavailable
		assertFalse(mValid.isAvailable());

		// Valid movie: number at beginning of name
		Movie mNumberInName = new Movie("5 10 Angry Men");
		assertTrue(mNumberInName.isAvailable());
		assertEquals("10 Angry Men", mNumberInName.getName());

		// Valid movie: negative stock
		assertEquals("Name", mNegativeStock.getName());
		assertFalse(mNegativeStock.isAvailable());
		mNegativeStock.backToInventory();
		assertTrue(mNegativeStock.isAvailable());

		// Movie with null string parameter
		try {
			String nullString = null;
			mNull = new Movie(nullString);
			fail("Movie was created with null string");
		} catch (IllegalArgumentException e) {
			assertNull(mNull);
		}

		// Movie with empty string parameter
		try {
			String emptyString = "";
			mNull = new Movie(emptyString);
			fail("Movie was created with empty string");
		} catch (IllegalArgumentException e) {
			assertNull(mNull);
		}

		// Invalid movie: No stock in raw string
		try {
			mNoStock = new Movie("Title");
			fail("movieNoStock was created.");
		} catch (IllegalArgumentException e) {
			assertNull(mNoStock);
		}

		// Invalid movie: no stock in raw string, but a number at title start
		try {
			mInvalidStock = new Movie("10Angry Men");
			fail("movieInvalidStock was created.");
		} catch (IllegalArgumentException e) {
			assertNull(mInvalidStock);
		}

		// Invalid movie: no stock in raw string, but a number inside title
		try {
			mNoStockNumberInTitle = new Movie("The Godfather: Part 2");
			fail("movieNoStockWithNumberInsideTitle was created.");
		} catch (IllegalArgumentException e) {
			assertNull(mNoStockNumberInTitle);
		}
	}

	/**
	 * Test method for
	 * {@link Movie#getDisplayName()}.
	 */
	@Test
	public void testGetDisplayName() {
		// Available movie
		assertEquals("Movie Name", mValid.getDisplayName());
		// Unavailable movie
		assertEquals("Name (currently unavailable)", mZeroStock.getDisplayName());
	}

	/**
	 * Note: in lexical order, "a" is less than "b". Test method for
	 * {@link Movie#compareToByName(Movie)}
	 * .
	 */
	@Test
	public void testCompareToByName() {
		Movie movieAm = new Movie("1 American Sniper");
		Movie movieAm2 = new Movie("2 American Sniper");
		Movie movieAb = new Movie("1 About Time");
		Movie movieG = new Movie("1 Gone Girl");
		Movie movieTheH = new Movie("1  The Hunger Games: Mockingjay, Part 1");
		Movie movieS = new Movie("2 Selma");

		// Compare "American Sniper" to movie with same name
		assertEquals(0, movieAm.compareToByName(movieAm2));

		// Positive when "American Sniper" compared to "About Time"
		assertTrue(movieAm.compareToByName(movieAb) > 0);

		// Negative when the reverse of above is tested.
		assertTrue(movieAb.compareToByName(movieAm) < 0);

		// Positive value when "Gone Girl" is compared to "About Time"
		assertTrue(movieG.compareToByName(movieAb) > 0);

		// Check that article "The" at beginning of a movie is ignored
		assertTrue(movieTheH.compareToByName(movieS) < 0);
	}

	/**
	 * Test method for
	 * {@link Movie#removeOneCopyFromInventory()}
	 * .
	 */
	@Test
	public void testRemoveOneCopyFromInventory() {
		// Stores the original movieZeroStock movie
		Movie movieTemp = mZeroStock;

		// Check that IllegalStateException is thrown and movie is unchanged.
		try {
			mZeroStock.removeOneCopyFromInventory();
			fail("IllegalStateException should have been thrown");
		} catch (IllegalStateException e) {
			assertEquals(movieTemp, mZeroStock);
		}
	}
}
