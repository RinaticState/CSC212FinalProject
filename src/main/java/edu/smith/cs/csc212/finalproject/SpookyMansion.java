package edu.smith.cs.csc212.finalproject;

import java.util.List;

/**
 * SpookyMansion, the game.
 * @author jfoley
 *
 */
public class SpookyMansion implements GameWorld {
	/**
	 * create a graph to store the places as nodes, exits as edges
	 */
	private Graph<Place, Exit> places = new Graph<>();
	
	/**
	 * Where should the player start?
	 */
	@Override
	public Place getStart() {
		return entranceHall;
	}
	
	/**
	 * create an entranceHall as the game start.
	 */
	Place entranceHall = insert(
			Place.create(places, "You are in the grand entrance hall of a large building.\n"
					+ "The front door is locked. How did you get here?"));

	/**
	 * This constructor builds our SpookyMansion game.
	 */
	public SpookyMansion() {
		
		// create all the places, node value is their description
		Place basement = insert(
				Place.create(places, "You have found the basement of the mansion.\n" + 
		                           "It is darker down here.\n" +
						"You get the sense a secret is nearby, but you only see the stairs you came from."
						));
		Place attic = insert(Place.create(places,
				"Something rustles in the rafters as you enter the attic. Creepy.\n" + "It's big up here."));
		Place attic2 = insert(Place.create(places, "There's definitely a bat in here somewhere.\n"
				+ "This part of the attic is brighter, so maybe you're safe here."));
		Place kitchen = insert(Place.create(places, "You've found the kitchen. You smell old food and some kind of animal."));
		Place dumbwaiter = insert(Place.create(places, "You crawl into the dumbwaiter. What are you doing?"));
		Place secretRoom = insert(Place.create(places, "You have found the secret room."));
		Place hallway0 = insert(Place.create(places, "This is a very long hallway."));
		Place hallway1 = insert(Place.create(places, "This is a very long hallway."));
		Place hallway2 = insert(Place.create(places, "This is a very long hallway."));
		Place crypt = insert(Place.terminal(places, "You have found the crypt.\n"
				+"It is scary here, but there is an exit to outside.\n"+
				"Maybe you'll be safe out there."));
		
		// connect a place and the exit by adding an edge, edge value is defined by description.
		this.places.addEdge(entranceHall, basement).edgeValue = new Exit("There are stairs leading down.");
		this.places.addEdge(basement, entranceHall).edgeValue = new Exit("There are stairs leading up.");
		this.places.addEdge(entranceHall, kitchen).edgeValue = new Exit("There is a red door.");
		this.places.addEdge(entranceHall, attic).edgeValue = new Exit("There are stairs leading up.");
		this.places.addEdge(attic, entranceHall).edgeValue = new Exit("There are stairs leading down.");
		this.places.addEdge(attic, attic2).edgeValue = new Exit("There is more through an archway.");
		this.places.addEdge(attic2, attic).edgeValue = new Exit("There is more back through the archway.");
		this.places.addEdge(kitchen, entranceHall).edgeValue = new Exit("There is a red door.");
		this.places.addEdge(kitchen, dumbwaiter).edgeValue = new Exit("There is a dumbwaiter.");
		this.places.addEdge(dumbwaiter, secretRoom).edgeValue = new Exit("Take it to the bottom.");
		this.places.addEdge(dumbwaiter, kitchen).edgeValue = new Exit("Take it to the middle-level.");
		this.places.addEdge(dumbwaiter, attic2).edgeValue = new Exit("Take it up to the top.");
		this.places.addEdge(secretRoom, hallway0).edgeValue = new Exit("There is a long hallway.");
		this.places.addEdge(hallway0, secretRoom).edgeValue = new Exit("Go back.");
		this.places.addEdge(hallway0, hallway1).edgeValue = new Exit("Go forward.");
		this.places.addEdge(hallway1, hallway0).edgeValue = new Exit("Go back.");
		this.places.addEdge(hallway1, hallway2).edgeValue = new Exit("Go forward.");
		this.places.addEdge(hallway2, hallway1).edgeValue = new Exit("Go back.");
		this.places.addEdge(hallway2, crypt).edgeValue = new Exit("There is darkness ahead.");

		// Can I BFS to the exit?
		List<Graph.Node<Place, Exit>> found = this.places.breadth_first_search(this.getStart());
		for (Graph.Node<Place, Exit> p : found) {
			// if crypt is found, the exits do go somewhere and game is winnable
			if (p.value.equals(crypt)) {
				System.out.println("Game is winnable");
			}
		}
		// BFS visits all the nodes until the exit.
		System.out.println("Breath-first search, visited " + found.size() + " places to reach end.");
		
		// See if depth first search is different than breath first search.
		List<Place> dfs_found = this.places.depth_first_search(this.getStart());
		// DFS could get lucky sometimes and arrive to the exit quick.
		System.out.println("Depth-first search, visited " + dfs_found.size() + " places to reach end.");
	}

	/**
	 * This helper method saves us a lot of typing. We always want to map from p.id
	 * to p.
	 * 
	 * @param p - the place.
	 * @return the place you gave us, so that you can store it in a variable.
	 */
	private Place insert(Place p) {
		places.addNode(p);
		return p;
	}

}
