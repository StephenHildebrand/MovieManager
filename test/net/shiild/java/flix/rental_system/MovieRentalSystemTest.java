/**
 * 
 */
package net.shiild.java.flix.rental_system;

import static org.junit.Assert.*;

import net.shiild.java.flix.customer.Customer;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for MovieRentalSystem
 * 
 * @author StephenHildebrand
 */
public class MovieRentalSystemTest {
	MovieRentalSystem inventory;

	/**
	 * Set up the inventory for testing.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		inventory = new MovieRentalSystem("movies-short.txt");
	}

	/**
	 * Test method for
	 * {@link MovieRentalSystem#MovieRentalSystem(java.lang.String)}
	 * .
	 */
	@Test
	public void testDVDRentalSystem() {
		Customer c1 = new Customer("id1", "pw1", 2);
		inventory.setCustomer(c1);
		inventory.addToCustomerQueue(0);
		assertTrue(c1.traverseAtHomeQueue().contains("Frozen"));
		assertEquals(inventory.traverseAtHomeQueue(), c1.traverseAtHomeQueue());
		inventory.addToCustomerQueue(1);
		inventory.addToCustomerQueue(2);
		inventory.addToCustomerQueue(3);
		assertEquals(inventory.traverseReserveQueue(), c1.traverseReserveQueue());
		inventory.reserveMoveAheadOne(1);
		assertEquals("Spectre\n", inventory.traverseReserveQueue());
		inventory.removeSelectedFromReserves(0);
		assertNull(inventory.traverseReserveQueue());
		inventory.returnItemToInventory(0);
		assertFalse(inventory.traverseAtHomeQueue().contains("Frozen"));

	}

	/**
	 * Test method for
	 * {@link MovieRentalSystem#showInventory()}
	 * .
	 */
	@Test
	public void testShowInventory() {
		assertEquals(
				"Frozen\nGravity\nHow to Train Your Dragon 2\nSpectre (currently unavailable)\nWarcraft (currently unavailable)\n",
				inventory.showInventory());
	}
}
