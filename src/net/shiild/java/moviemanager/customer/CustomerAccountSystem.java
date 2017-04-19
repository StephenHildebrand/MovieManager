/**
 *
 */
package net.shiild.java.moviemanager.customer;

import net.shiild.java.moviemanager.rental_system.RentalManager;

/**
 * Represents the customer part of the overall system and implements
 * CustomerAccountManager, allowing for customer management and use login.
 *
 * @author StephenHildebrand
 */
public class CustomerAccountSystem implements CustomerAccountManager {
    /** True iff the administrator is logged into the system */
    private boolean adminLoggedIn;
    /** True iff a customer is logged into the system */
    private boolean customerLoggedIn;
    /** String constant representing the administrator's id and password */
    private static final String ADMIN = "admin";
    /** Database of customers in the system */
    private CustomerDB customerList;
    /** The rental inventory associated with the system */
    private RentalManager inventorySystem;

    /**
     * Constructor for the CustomerAccountSystem.
     *
     * @param inventorySystem inventory of the overall system in the context of a single
     *                        customer
     */
    public CustomerAccountSystem(RentalManager inventorySystem) {
        if (inventorySystem != null) {
            this.inventorySystem = inventorySystem;
            this.customerList = new CustomerDB();
            adminLoggedIn = false;
            customerLoggedIn = false;
        }
    }

    @Override
    public void login(String id, String password) {
        // A user is already logged in
        if (adminLoggedIn || customerLoggedIn) {
            throw new IllegalStateException("The account doesn't exist.");
        }
        if (id.equals(ADMIN) && password.equals(ADMIN)) { // Login admin
            adminLoggedIn = true;
        } else { // Login customer
            Customer customer = customerList.verifyCustomer(id, password);
            customerLoggedIn = true;
            customer.login();
            inventorySystem.setCustomer(customer);
        }
    }

    @Override
    public void logout() {
        if (adminLoggedIn) { // Log out admin
            adminLoggedIn = false;
        } else if (customerLoggedIn) { // Log out customer
            customerLoggedIn = false;
        }
    }

    @Override
    public boolean isAdminLoggedIn() {
        return adminLoggedIn;
    }

    @Override
    public boolean isCustomerLoggedIn() {
        return customerLoggedIn;
    }

    @Override
    public void addNewCustomer(String id, String password, int num) {
        if (adminLoggedIn && customerList != null) {
            customerList.addNewCustomer(id, password, num);
        }
    }

    @Override
    public void cancelAccount(String id) {
        if (adminLoggedIn && customerList != null) {
            customerList.cancelAccount(id);
        }
    }

    @Override
    public String listAccounts() {
        if (customerList != null) {
            return customerList.listAccounts();
        }
        return null;
    }
}
