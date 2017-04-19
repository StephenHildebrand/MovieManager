/**
 * 
 */
package net.shiild.java.moviemanager.customer;

import org.junit.Before;

import net.shiild.java.moviemanager.rental_system.RentalManager;

/**
 * Test class for CustomerAccountSystem
 * 
 * @author StephenHildebrand
 */
public class MovieCustomerAccountSystemTest {
	CustomerAccountSystem system;
	RentalManager inventory;

	/**
	 * Set up objects for testing.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		system = new CustomerAccountSystem(inventory);
	}

//	/**
//	 * Test method for
//	 * {@link CustomerAccountSystem#CustomerAccountSystem(RentalManager)}
//	 * .
//	 */
//	@Test
//	public void testMovieCustomerAccountSystem() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for
//	 * {@link CustomerAccountSystem#login(java.lang.String, java.lang.String)}
//	 * .
//	 */
//	@Test
//	public void testLogin() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for
//	 * {@link CustomerAccountSystem#logout()}
//	 * .
//	 */
//	@Test
//	public void testLogout() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for
//	 * {@link CustomerAccountSystem#isAdminLoggedIn()}
//	 * .
//	 */
//	@Test
//	public void testIsAdminLoggedIn() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for
//	 * {@link CustomerAccountSystem#isCustomerLoggedIn()}
//	 * .
//	 */
//	@Test
//	public void testIsCustomerLoggedIn() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for
//	 * {@link CustomerAccountSystem#addNewCustomer(java.lang.String, java.lang.String, int)}
//	 * .
//	 */
//	@Test
//	public void testAddNewCustomer() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for
//	 * {@link CustomerAccountSystem#cancelAccount(java.lang.String)}
//	 * .
//	 */
//	@Test
//	public void testCancelAccount() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for
//	 * {@link CustomerAccountSystem#listAccounts()}
//	 * .
//	 */
//	@Test
//	public void testListAccounts() {
//		fail("Not yet implemented");
//	}

}
