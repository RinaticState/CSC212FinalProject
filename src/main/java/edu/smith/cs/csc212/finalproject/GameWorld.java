package edu.smith.cs.csc212.finalproject;

/**
 * This is the interface through which our main method in {@link InteractiveFiction} interacts with different games.
 * @author jfoley
 *
 */
public interface GameWorld {
	/**
	 * What is the id of the starting location for this game?
	 * @return the id of a Place.
	 */
	public Place getStart();
}
