/**
 *
 */
package net.shiild.java.flix.rental_system;

import net.shiild.java.flix.customer.Customer;
import net.shiild.java.flix.inventory.Movie;
import net.shiild.java.flix.inventory.MovieDB;

/**
 * Represents the inventory part of the overall system in the context of a
 * single customer and implements the RentalManager interface.
 *
 * @author StephenHildebrand
 */
public class MovieRentalSystem implements RentalManager {
    /** The customer currently logged into the system */
    private Customer currentCustomer;
    /** The database of movies in the system */
    private MovieDB inventory;

    /**
     * Constructor for MovieRentalSystem, representing the system inventory
     *
     * @param fileName name of the movie inventory file
     */
    public MovieRentalSystem(String fileName) {
        if (fileName != null && !fileName.equals("")) {
            inventory = new MovieDB(fileName);
        }
    }

    @Override
    public String showInventory() {
        if (inventory != null) {
            return inventory.traverse();
        }
        return null;
    }

    @Override
    public void setCustomer(Customer c) {
        currentCustomer = c;
    }

    @Override
    public void addToCustomerQueue(int position) {
        if (currentCustomer == null) {
            throw new IllegalStateException();
        }
        Movie movie = inventory.findItemAt(position);
        currentCustomer.reserve(movie);
    }

    @Override
    public void reserveMoveAheadOne(int position) {
        if (currentCustomer == null) {
            throw new IllegalStateException();
        }
        currentCustomer.moveAheadOneInReserves(position);
    }

    @Override
    public void removeSelectedFromReserves(int position) {
        if (currentCustomer == null) {
            throw new IllegalStateException();
        }
        currentCustomer.unReserve(position);
    }

    @Override
    public String traverseReserveQueue() {
        if (currentCustomer == null) {
            throw new IllegalStateException();
        }
        return currentCustomer.traverseReserveQueue();
    }

    @Override
    public String traverseAtHomeQueue() {
        if (currentCustomer == null) {
            throw new IllegalStateException();
        }
        return currentCustomer.traverseAtHomeQueue();
    }

    /**
     * Return a movie from the customer's at home queue to inventory.
     */
    @Override
    public void returnItemToInventory(int position) {
        if (currentCustomer == null) {
            throw new IllegalStateException();
        }
        currentCustomer.returnDVD(position);
    }
}
