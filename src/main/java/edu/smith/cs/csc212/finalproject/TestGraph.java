package edu.smith.cs.csc212.finalproject;
/**
 * Tests the graph implementation.
 * @author ambernguyen
 *
 */
public class TestGraph {

	public static void main(String[] args) {
		// make a friends' list
		Graph<String, String> testGraph = new Graph<String, String>();
		testGraph.addNode("Rina");
		testGraph.addNode("Haruka");
		testGraph.addNode("Karen");
		testGraph.addNode("Mayu");
		testGraph.addNode("Yurin");
		testGraph.addEdge("Rina", "Haruka");
		testGraph.addEdge("Rina", "Mayu");
		testGraph.addEdge("Yurin", "Mayu");
		testGraph.addEdge("Yurin", "Rina");
		testGraph.addEdge("Karen", "Yurin");
		testGraph.addEdge("Haruka", "Karen");
		testGraph.addEdge("Karen", "Mayu");
		testGraph.depth_first_search("Rina");
		// friends removed :(
		testGraph.removeEdge("Yurin", "Rina");
		testGraph.removeNode("Rina");
		testGraph.print();
		System.out.println("Number Of Nodes: " + testGraph.numNodes);
		System.out.println("Number Of Edges: " + testGraph.numEdges);
	}

}
