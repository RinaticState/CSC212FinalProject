package edu.smith.cs.csc212.finalproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.smith.cs.csc212.finalproject.Graph.Edge;

/**
 * This represents a place in our text adventure.
 * @author jfoley
 *
 */
public class Place {
	private Graph<Place,Exit> myGraph;
	/**
	 * What to tell the user about this place.
	 */
	private String description;
	/**
	 * Whether reaching this place ends the game.
	 */
	private boolean terminal;
	
	/**
	 * Internal only constructor for Place. Use {@link #create(String, String)} or {@link #terminal(String, String)} instead.
	 * @param id - the internal id of this place.
	 * @param description - the user-facing description of the place.
	 * @param terminal - whether this place ends the game.
	 */
	private Place(Graph<Place,Exit> graph, String description, boolean terminal) {
		this.myGraph = graph;
		this.description = description;
		this.terminal = terminal;
	}
	
	/**
	 * For gameplay, whether this place ends the game.
	 * @return true if this is the end.
	 */
	public boolean isTerminalState() {
		return this.terminal;
	}

	/**
	 * The narrative description of this place.
	 * @return what we show to a player about this place.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Get a view of the exits from this Place, for navigation.
	 * @return all the exits from this place.
	 */
	public List<Graph.Edge<Place, Exit>> getExits() {
		return myGraph.findAllEdges(this);
	}
	
	/**
	 * Give a string for debugging what place is what.
	 */
	public String toString() {
		return this.description;
	}
	
	/**
	 * This is a terminal location (good or bad).
	 * @param id - this is the id of the place (for creating {@link Exit} objects that go here).
	 * @param description - this is the description of the place.
	 * @return the Place object.
	 */
	public static Place terminal(Graph<Place, Exit> g, String description) {
		return new Place(g, description, true);
	}
	
	/**
	 * Create a place with an id and description.
	 * @param id - this is the id of the place (for creating {@link Exit} objects that go here).
	 * @param description - this is what we show to the user.
	 * @return the new Place object (add exits to it).
	 */
	public static Place create(Graph<Place, Exit> g, String description) {
		return new Place(g, description, false);
	}
	
}
