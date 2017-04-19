/**
 *
 */
package net.shiild.java.moviemanager.customer;

import net.shiild.java.moviemanager.inventory.Movie;
import net.shiild.java.moviemanager.list_util.MultiPurposeList;

/**
 * Represents a customer in the movie system customer. Each customer has
 * an atHomeQueue and reserveQueue of movies currently at home and on reserve,
 * and depends on only the Movie and MultiPurposeList classes.
 * <p>
 * Any element added to atHomeQueue or reserveQueue are added to the end of the
 * list.
 *
 * @author StephenHildebrand
 */
public class Customer {
    /** The customer's username used to login to the system */
    private String id;
    /** The customer's password used to login to the system */
    private String password;
    /** The max number of movies the customer can have at home, between 1-5 */
    private int maxAtHome;
    /** The current number of movies the customer has at home */
    private int nowAtHome;
    /** Movies that the customer currently has at home */
    private MultiPurposeList<Movie> atHomeQueue;
    /** Movies that the customer has in their reserve queue */
    private MultiPurposeList<Movie> reserveQueue;

    /**
     * Constructs a Customer object using the id, password and the maximum
     * number of movies a customer can have at home. These values are set by the
     * administrator. An IllegalArgumentException is thrown if the id or
     * password parameters are null or of length 0 after trimming the whitespace
     * ends. If the maxAtHome parameter is negative, it is set to 0.
     *
     * @param id        the Customer's id
     * @param password  the Customer's password
     * @param maxAtHome the Customer's max movie limit
     * @throws IllegalArgumentException if the parameter id or password are null or of length 0
     */
    public Customer(String id, String password, int maxAtHome) throws IllegalArgumentException {
        if (id == null || password == null) {
            throw new IllegalArgumentException();
        }
        // Trim the id and password
        id = id.trim();
        password = password.trim();

        // Check that the trimmed id and password aren't length 0
        if (id.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException();
        }

        // Set this customer's id and password to the valid arguments
        this.id = id;
        this.password = password;

        // Set maxAtHome to 0 if it is negative
        if (maxAtHome > 0) {
            this.maxAtHome = maxAtHome;
        } else {
            this.maxAtHome = 0;
        }

        // Instantiate the customer's atHomeQueue & reserveQueue
        atHomeQueue = new MultiPurposeList<Movie>();
        reserveQueue = new MultiPurposeList<Movie>();

        nowAtHome = 0; // Each customer begins with 0 movies at home
    }

    /**
     * Compares the user-entered password that is passed as a parameter with the
     * stored password. If they are equal, true is returned, otherwise false is
     * returned [UC2, S1], [UC3,S1].
     *
     * @param unverifiedPassword password to be verified
     * @return true if the parameter password matches the stored password
     */
    public boolean verifyPassword(String unverifiedPassword) {
        if ((unverifiedPassword.trim()).equals(password)) {
            return true;
        }
        return false;
    }

    /**
     * Returns the customer's id string.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Lexicographic, case-insensitive comparison of two customer ids [UC3].
     *
     * @param otherCustomer to be compared to
     * @return don't yet know
     */
    public int compareToByName(Customer otherCustomer) {
        // Does null otherCustomer need to be handled???
        String thisName = id.toLowerCase();
        String otherName = otherCustomer.getId().toLowerCase();
        return thisName.compareTo(otherName);
    }

    /**
     * Adjusts the customer's queues (atHome & reserve) when the customer logs
     * in to be up to date with the current state of the inventory.
     * <p>
     * Note: There can only be one customer logged in at a time. The changes
     * described in the login method will only occur when a customer logs in.
     * <p>
     * Suppose Customer A and Customer B want Movie 1, but they were all checked
     * out so the movie shows up in the reserve Queue for both. Customer C is
     * logged in and returns Movie 1. Now there's 1 in the inventory. That Movie
     * is not checked out to a customer until the customer logs in. If Customer
     * A logs in first, Customer A gets the Movie. Customer B logs in later, but
     * there would be no change since there is no movie in the inventory.
     */
    public void login() {
        if (nowAtHome < maxAtHome) {
            Movie firstAvailable = removeFirstAvailable();
            if (firstAvailable != null) { // A movie is available
                checkOut(firstAvailable);
            }
        }
    }

    /**
     * Returns a string of names of movies in the reserve queue in order.
     * Successive movies are separated by newlines [UC9,S1].
     *
     * @return the ordered string of movie names in the reserve queue
     */
    public String traverseReserveQueue() {
        // Handle null reserveQueue???
        return traverseQueue(reserveQueue);
    }

    /**
     * Returns a string of ordered names of movies at home. Successive movies
     * are separated by newlines [UC9,S2].
     *
     * @return the ordered string of movie names that are at home
     */
    public String traverseAtHomeQueue() {
        // Handle null atHomeQueue???
        return traverseQueue(atHomeQueue);
    }

    /**
     * Closes this account and returns all movies at home to the inventory
     * [UC4].
     */
    public void closeAccount() {
        if (atHomeQueue != null && !atHomeQueue.isEmpty()) {
            for (int psn = 0; psn < atHomeQueue.size(); psn++) {
                (atHomeQueue.remove(psn)).backToInventory();
            }
            atHomeQueue = new MultiPurposeList<Movie>();
        }
    }

    /**
     * Removes the movie in the given position from the queue of movies at home
     * and returns it to the inventory [UC10]. Throws an
     * IllegalArgumentException�if the position is out of bounds.
     *
     * @param psn the position of the movie to be returned to inventory
     */
    public void returnDVD(int psn) {
        if (atHomeQueue != null && !atHomeQueue.isEmpty()) {
            if (psn < 0 || psn > atHomeQueue.size()) {
                throw new IllegalArgumentException("Position out of bounds");
            }
            Movie movie = atHomeQueue.lookAtItemN(psn); // Get the Movie
            movie.backToInventory(); // Return Movie to inventory
            atHomeQueue.remove(psn); // Remove Movie from home queue
            nowAtHome--; // Decrement at home movies counter
            // Now try to send the next available movie in reserve queue
            if (nowAtHome < maxAtHome) {
                checkOut(removeFirstAvailable());
            }
        }
    }

    /**
     * Moves the movie in the given position ahead one position in the reserve
     * queue [UC12]. Throws an�IllegalArgumentException�if the position is out
     * of bounds. If the position is 0, there is no exception but there is also
     * no change in the list.
     *
     * @param psn of the movie to be moved up
     * @throws IllegalArgumentException when the position argument is out of range
     */
    public void moveAheadOneInReserves(int psn) {
        if (psn < 0 || psn > reserveQueue.size()) {
            throw new IllegalArgumentException();
        }
        if (psn > 0) {
            reserveQueue.moveAheadOne(psn);
        }
    }

    /**
     * Removes the movie in the given position from the reserve queue [UC11].
     * Throws an�IllegalArgumentException�if the position is out of bounds.
     *
     * @param psn in the queue of the movie to be removed
     */
    public void unReserve(int psn) {
        if (reserveQueue != null && !reserveQueue.isEmpty()) {
            if (psn < 0 || psn > reserveQueue.size()) {
                throw new IllegalArgumentException("Position out of bounds");
            }
            reserveQueue.remove(psn);
        }
    }

    /**
     * Places the movie at the end of the reserve queue [UC8]. Throws
     * an�IllegalArgumentException�if�Movie�is null.
     *
     * @param movie to be added to the queue
     * @throws IllegalArgumentException if the Movie is null
     */
    public void reserve(Movie movie) throws IllegalArgumentException {
        if (movie == null) {
            throw new IllegalArgumentException("Movie not specified.");
        }
        if (movie.isAvailable()) {
            checkOut(movie);
        } else {
            reserveQueue.addToRear(movie);
        }
    }

    private String traverseQueue(MultiPurposeList<Movie> movies) {
        String stringDB = null;
        if (!movies.isEmpty()) {
            stringDB = "";
            movies.resetIterator();
            while (movies.hasNext()) {
                stringDB = stringDB + movies.next().getName() + "\n";
            }
        }
        return stringDB;
    }

    private void checkOut(Movie movie) {
        if (movie != null && nowAtHome < maxAtHome) {
            atHomeQueue.addToRear(movie);
            movie.removeOneCopyFromInventory();
            nowAtHome++;
        }

    }

    /**
     * Private method used by login() to remove the first available Movie in
     * reserveQueue. Returns null if none of the movies in the reserve queue are
     * available.
     *
     * @return Movie first available movie in the reserveQueue
     */
    private Movie removeFirstAvailable() {
        if (reserveQueue != null) {
            reserveQueue.resetIterator();
            int psn = 0;
            while (reserveQueue.hasNext()) {
                Movie movie = reserveQueue.next();
                if (movie.isAvailable()) { // Movie is available
                    reserveQueue.remove(psn); // Remove from reserve queue
                    return movie;
                }
                psn++;
            }
        }
        return null;
    }
}
