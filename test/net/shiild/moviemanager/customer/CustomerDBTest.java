/**
 * 
 */
package net.shiild.moviemanager.customer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.shiild.moviemanager.customer.Customer;
import net.shiild.moviemanager.customer.CustomerDB;

/**
 * Test class for CustomerDB class.
 * 
 * @author StephenHildebrand
 */
public class CustomerDBTest {
	/** A database maintaining a list of customers for testing */
	CustomerDB cDB;
	/** Five customers for testing */
	Customer c1, c2, c3, c4, c5;

	/**
	 * Set up the Customer database and Customer objects for use in testing.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		cDB = new CustomerDB();
		// Initialize each Customer with valid arguments
		c1 = new Customer("a", "pw1", 5);
		c2 = new Customer("b", "pw2", 3);
		c3 = new Customer("c", "pw3", 4);
		c4 = new Customer("d", "pw4", 2);
		c5 = new Customer("e", "pw5", 1);

		// Add three customers to the customer database
		cDB.addNewCustomer("a", "pw1", 5); // c1
		cDB.addNewCustomer("b", "pw2", 3); // c2
		cDB.addNewCustomer("c", "pw3", 4); // c3

	}

	/**
	 * Test method for
	 * {@link CustomerDB#CustomerDB()}.
	 */
	@Test
	public void testCustomerDB() {
		assertEquals("a\nb\nc\n", cDB.listAccounts());
		cDB.addNewCustomer("d", "pw4", 2);
		assertEquals("a\nb\nc\nd\n", cDB.listAccounts());
		cDB.cancelAccount("b");
		assertEquals("a\nc\nd\n", cDB.listAccounts());
	}

	/**
	 * Test method for
	 * {@link CustomerDB#verifyCustomer(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testVerifyCustomer() {
		// Test1: null password
		try {
			cDB.verifyCustomer("a", null);
			fail("Invalid customer password verfied.");
		} catch (IllegalArgumentException e) {
			assertEquals("a\nb\nc\n", cDB.listAccounts());
		}
		// Test2: Verify  customer c4 that is not in the list
		try {
			cDB.verifyCustomer("d", "pw4");
			fail("Invalid customer password verfied.");
		} catch (IllegalArgumentException e) {
			assertEquals("a\nb\nc\n", cDB.listAccounts());
		}
		// Test5: Incorrect password
		try {
			cDB.verifyCustomer("a", "pw5");
			fail("Invalid customer password verfied.");
		} catch (IllegalArgumentException e) {
			assertEquals("a\nb\nc\n", cDB.listAccounts());
		}
		// Test4: Verify customer c2 that is in the list
		assertEquals(c2.getId(), cDB.verifyCustomer("b", "pw2").getId());

	}

	/**
	 * Test method for
	 * {@link CustomerDB#addNewCustomer(java.lang.String, java.lang.String, int)}
	 * .
	 */
	@Test
	public void testAddNewCustomer() {
		// Test1: empty id (invalid)
		try {
			cDB.addNewCustomer("", "password", 2);
			fail("Invalid customer was added.");
		} catch (IllegalArgumentException e) {
			assertEquals("a\nb\nc\n", cDB.listAccounts());
		}
		// Test2: whitespace in id (invalid)
		try {
			cDB.addNewCustomer("i d1", "password", 2);
			fail("Invalid customer was added.");
		} catch (IllegalArgumentException e) {
			assertEquals("a\nb\nc\n", cDB.listAccounts());
		}
		// Test3: Customer already in database (invalid)
		try {
			cDB.addNewCustomer("a", "password", 5);
			fail("Invalid customer was added.");
		} catch (IllegalArgumentException e) {
			assertEquals("a\nb\nc\n", cDB.listAccounts());
		}
	}
}
