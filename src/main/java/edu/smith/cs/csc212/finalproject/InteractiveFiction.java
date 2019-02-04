package edu.smith.cs.csc212.finalproject;

import java.util.HashSet;
import java.util.List;

/**
 * This is our main class for P4. It interacts with a GameWorld and handles user-input.
 * @author jfoley
 *
 */
public class InteractiveFiction {

	/**
	 * This is where we play the game.
	 * @param args
	 */
	public static void main(String[] args) {
		// This is a text input source (provides getUserWords() and confirm()).
		TextInput input = TextInput.fromArgs(args);

		// This is the game we're playing.
		GameWorld game = new SpookyMansion();
		
		// This is the current location of the player (initialize as start).
		// Maybe we'll expand this to a Player object.
		Place here = game.getStart();

		// Play the game until quitting.
		// This is too hard to express here, so we just use an infinite loop with breaks.
		while (true) {
			// Print the description of where you are.
			System.out.println(here.getDescription());

			// Game over after print!
			if (here.isTerminalState()) {
				break;
			}

			// Show a user the ways out of this place.
			List<Graph.Edge<Place, Exit>> exits = here.getExits();
			for (Graph.Edge<Place, Exit> e : exits) {
				System.out.println(" - " + e.edgeValue.getDescription());
			}

			// Figure out what the user wants to do
			List<String> action = input.getUserWords(">");
			if (action.contains("quit")) {
				if (input.confirm("Are you sure you want to quit?")) {
					break;
				} else {
					continue;
				}
			} else if (action.contains("q")) {
				if (input.confirm("Are you sure you want to quit?")) {
					break;
				} else {
					continue;
				}
			}

			// See if what the user typed matches any exits.
			HashSet<Graph.Edge<Place, Exit>> matches = new HashSet<>();
			for (Graph.Edge<Place, Exit> e : exits) {
				List<String> keywords = WordSplitter.splitTextToWords(e.edgeValue.getDescription());
				for (String a : action) {
					if (keywords.contains(a)) {
						matches.add(e);
						break;
					}
				}
			}
			
			// If they typed a unique word, they want to go there.
			if (matches.size() == 1) {
				Graph.Edge<Place,Exit> e = matches.iterator().next();
				here = e.nodeKey;
				continue;
			} else if (matches.size() >= 2) {
				// If not, express our confusion.
				System.out.println("I can't tell which you mean:");
				for (Graph.Edge<Place, Exit> e : matches) {
					System.out.println(" - " + e.edgeValue.getDescription());
				}
				continue;
			} else {
				// If there were no matches at all.
				System.out.println("I'm not sure what you mean by: " + action);
			}
		}

		// You get here by "quit" or by reaching a Terminal Place.
		System.out.println(">>> GAME OVER <<<");
	}

}
