package edu.smith.cs.csc212.finalproject;

/**
 * This class represents an exit from a Place to another Place.
 * @author jfoley
 *
 */
public class Exit {
	/**
	 * How do we describe this exit to a user, e.g., "A door with a spiderweb."
	 */
	private String description;
	
	/**
	 * Create a new Exit.
	 * @param description - how it looks.
	 */
	public Exit(String description) {
		this.description = description;
	}
	
	/**
	 * A getter for the description of this exit.
	 * @return how it looks.
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Make this debuggable when we print it for ourselves.
	 */
	public String toString() {
		return "Exit("+this.description+")";
	}
}
