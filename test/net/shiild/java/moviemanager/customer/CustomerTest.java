/**
 * 
 */
package net.shiild.java.moviemanager.customer;

import static org.junit.Assert.*;

import net.shiild.java.moviemanager.inventory.Movie;
import net.shiild.java.moviemanager.list_util.MultiPurposeList;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the Customer.
 * 
 * @author StephenHildebrand
 */
public class CustomerTest {
	/** Three customers for testing */
	Customer c1, c2, c3;
	/** Two reserve queues for testing */
	MultiPurposeList<Movie> reserve1, reserve2;
	/** Two at home queues for testing */
	MultiPurposeList<Movie> atHome1, atHome2;
	/** Five Movie objects for testing */
	Movie movie1, movie2, movie3, movie4, movie5;

	/**
	 * Set up a Customer for testing.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Initialize each Customer with valid arguments
		c1 = new Customer("id1", "pw1", 5);
		c2 = new Customer("id2", "pw2", 3);
		c3 = new Customer("id3", "pw3", 1);
		// Initialize each movie with valid arguments
		movie1 = new Movie("3 Frozen");
		movie2 = new Movie("2  Gravity");
		movie3 = new Movie("0 Spectre");
		movie4 = new Movie("0 Warcraft");
		movie5 = new Movie("3 How to Train Your Dragon 2");
		// Put movies1-3 in c1's reserve queue
		c1.reserve(movie1);
		c1.reserve(movie2);
		c1.reserve(movie3);

	}

	/**
	 * Test method for
	 * {@link Customer#Customer(java.lang.String, java.lang.String, int)}
	 * .
	 */
	@Test
	public void testCustomer() {
		// Test1: c1 is a valid Customer
		assertEquals("id1", c1.getId());
		assertTrue(c1.verifyPassword("pw1"));
		// Verify current c1 reserve queue
		assertEquals("Spectre\n", c1.traverseReserveQueue());
		// Verify c1 atHomeQueue contents
		assertEquals("Frozen\nGravity\n", c1.traverseAtHomeQueue());

		// Test2: null id
		Customer cNullId = null;
		try {
			cNullId = new Customer(null, "pw", 1);
			fail("A Customer with a null id was created");
		} catch (IllegalArgumentException e) {
			assertNull(cNullId);
		}

		// Test3: empty password
		Customer cEmptyPassword = null;
		try {
			cEmptyPassword = new Customer("id", "", 1);
			fail("A Customer with an empty password was created");
		} catch (IllegalArgumentException e) {
			assertNull(cEmptyPassword);
		}
		// // Test4: negative maxAtHome
		// Customer negMaxAtHome = new Customer("id", "pw", -3);
		// assertEquals(0, negMaxAtHome.maxAtHome);
	}

	/**
	 * Test method for
	 * {@link Customer#compareToByName(Customer)}
	 * .
	 */
	@Test
	public void testCompareToByName() {
		assertTrue(c1.compareToByName(c2) < 0);
		assertTrue(c2.compareToByName(c1) > 0);
		assertTrue(c1.compareToByName(c1) == 0);
	}

	/**
	 * Test method for {@link Customer#login()}.
	 */
	@Test
	public void testLogin() {
		// c2 reserves last copy of movie2 (c1 already reserved copy 1)
		c2.reserve(movie2);
		// Check that movie2 is no long available
		assertFalse(movie2.isAvailable());
		// c3 reserves movie2 -- currently unavailable
		c3.reserve(movie2);
		assertEquals("Gravity\n", c3.traverseReserveQueue());
		assertNull(c3.traverseAtHomeQueue());
		// c1 returns a copy of movie2 (their atHomeQueue position 1)
		c1.returnDVD(1);
		// c3 logs in, and a copy of movie2 is automatically reserved for them
		c3.login();
		assertEquals("Gravity\n", c3.traverseAtHomeQueue());
		assertNull(c3.traverseReserveQueue());
	}

//	/**
//	 * Test method for
//	 * {@link Customer#closeAccount()}.
//	 */
//	@Test
//	public void testCloseAccount() {
//		c1.closeAccount();
//		assertEquals("", c1.traverseAtHomeQueue());
//	}

	/**
	 * Test method for
	 * {@link Customer#returnDVD(int)}.
	 */
	@Test
	public void testReturnDVD() {
		// Test1: Return c1's atHomeQueue position 1 movie
		c1.returnDVD(0);
		assertEquals("Gravity\n", c1.traverseAtHomeQueue());
		// Remove the other movie and check the atHomeQueue
		c1.returnDVD(0);
		assertNull(c1.traverseAtHomeQueue());
		// Try to remove another movie -- nothing should happen
		c1.returnDVD(0);
		assertNull(c1.traverseAtHomeQueue());
	}

	/**
	 * Test method for
	 * {@link Customer#moveAheadOneInReserves(int)}
	 * .
	 */
	@Test
	public void testMoveAheadOneInReserves() {
		// Test1: Move ahead movie1 at position 0 -- shouldn't change the list
		c1.moveAheadOneInReserves(0);
		// Check that reserveQueue is unchanged
		assertEquals("Spectre\n", c1.traverseReserveQueue());

		// Test2: Move ahead movie at invalid position -- throws exception
		try {
			c1.moveAheadOneInReserves(5);
			fail("Invalid position");
		} catch (IllegalArgumentException e) {
			assertEquals("Spectre\n", c1.traverseReserveQueue());
			// Check that reserveQueue is unchanged
		}

		// Test3: Add movie4, which goes to reserveQueue since it is
		// unavailable. Then move ahead movie4 (position 1).
		c1.reserve(movie4);
		c1.moveAheadOneInReserves(1);
		// Check that reserveQueue is correctly changed
		assertEquals("Warcraft\nSpectre\n", c1.traverseReserveQueue());
	}

	/**
	 * Test method for
	 * {@link Customer#unReserve(int)}.
	 */
	@Test
	public void testUnReserve() {
		try {
			c1.unReserve(5);
			fail("Position out of bounds.");
		} catch (IllegalArgumentException e) {
			// Exception thrown if attempt to unreserve a movie outside
			// reserveQueue bounds
		}
	}

	/**
	 * Test method for
	 * {@link Customer#reserve(Movie)}
	 * .
	 */
	@Test
	public void testReserve() {
		// Test1: c1 reserve movie4
		c1.reserve(movie4);
		// Check that reserveQueue is correctly updated.
		assertEquals("Spectre\nWarcraft\n", c1.traverseReserveQueue());
		// Test2: Reserve a null movie
		try {
			c1.reserve(null);
			fail("Null movie cannot be added.");
		} catch (IllegalArgumentException e) {
			// Exception should be thrown if a null movie is reserved
		}
	}
}
