package edu.smith.cs.csc212.finalproject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

/**
 * A graph data structure implementation.
 * @author ambernguyen
 *
 * @param <T> Node object type
 * @param <E> Edge object type
 */
public class Graph<T, E> {
	/**
	 * Hashmap will store the node class.
	 */
	Map<T, Node<T, E>> mapOfNodes;
	/**
	 * Keeps track of the number of nodes in the graph.
	 */
	int numNodes = 0;
	/**
	 * Keeps track of the number of edges in the graph.
	 */
	int numEdges = 0;
	
	/**
	 * The Edge subclass, which is used to connect two nodes to each other.
	 * @author ambernguyen
	 *
	 * @param <T> Node object type
	 * @param <E> Edge object type
	 */
	static class Edge<T, E> {
		public T nodeKey;
		public E edgeValue;
		public Edge (T nodeKey) {
			this.nodeKey = nodeKey;
		}
	}
	
	/**
	 * Node subclass for graph.
	 * @author ambernguyen
	 *
	 * @param <T> Node object type
	 * @param <E> Edge object type
	 */
	static class Node<T, E> {
		/**
		 * Value of any object type to store into node
		 */
		public T value;
		/**
		 * An adjacency list for connecting other nodes
		 */
		public List<Edge<T, E>> connections;
		/**
		 * The node constructor.
		 * @param value - data of any object type for node to store
		 */
		public Node(T value) {
			this.value = value;
			// A new list is created everytime a new node is made.
			this.connections = new LinkedList<>();
		}
	}
	
	/**
	 * Constructor for the graph class.
	 */
	public Graph() {
		mapOfNodes = new HashMap<>();
	}
	
	/**
	 * Adds a node to the graph
	 * @param item - value for node to be added
	 */
	public void addNode(T item) {
		// checks if the node key is unique. if it is, add it.
		if (!mapOfNodes.containsKey(item)) {
			mapOfNodes.put(item, new Node<>(item));
			// increment node count
			numNodes++;
		} else {
			System.out.println(item + " already exists !");
		}
	}
	
	/**
	 * connects two nodes by adding an edge
	 * @param head - start node
	 * @param tail - node to connect to start node
	 * @return the edge
	 */
	public Edge<T,E> addEdge(T head, T tail) {
		// set edge value to the tail value
		Edge<T,E> newEdge = new Edge<>(tail);
		// check if the head already exists
		if (mapOfNodes.containsKey(head)) {
			// add the tail to the head's adjacency list
			mapOfNodes.get(head).connections.add(newEdge);
			numEdges++;
		} else {
			// if head doesn't exist, create a new node
			addNode(head);
			mapOfNodes.get(head).connections.add(newEdge);
			numEdges++;
		}
		return newEdge;
	}
	
	/**
	 * destroy the connection between two nodes
	 * @param head - source node
	 * @param tail - node to remove from source
	 */
	public void removeEdge(T head, T tail) {
		// loop through the head node's edge list
		for (Edge<T,E> edge : mapOfNodes.get(head).connections) {
			// if the tail's value equals the edge value, remove the edge from the edge list
			if (edge.nodeKey.equals(tail)) {
				mapOfNodes.get(head).connections.remove(edge);
				numEdges--;
			}
		}
	}
	
	/**
	 * destroy any edge connections when a node is removed
	 * @param edge - the node key of the edge to remove
	 */
	private void removeEdgesForNodes(T edge) {
		// loop through all the nodes
		for (T key : mapOfNodes.keySet()) {
			// loop through all the edge connections for each node
			for (Edge<T,E> edgeToRemove : mapOfNodes.get(key).connections) {
				// if edge's node key equals the node key passed, remove the edge connection
				if (edgeToRemove.nodeKey.equals(edge)) {
					mapOfNodes.get(key).connections.remove(edgeToRemove);
					numEdges--;
				}
			}
		}
	}
	
	/**
	 * removes a node
	 * @param node value to remove
	 */
	public void removeNode(T node) {
		// destroy any edges of the node
		removeEdgesForNodes(node);
		// see if node exists in the graph
		if (mapOfNodes.containsKey(node)) {
			// if it does, remove it.
			mapOfNodes.remove(node);
			numNodes--;
		}
	}
	
	/**
	 * get adjacent nodes
	 * @param item - node whose neighbours you want to see
	 * @return a list of the adjacent nodes
	 */
	public List<T> getNeighbours(T item) {
		List<T> neighbouringNodes = new LinkedList<>();
		if (mapOfNodes.containsKey(item)) {
			// look at the specified node's adjacency list
			for (Edge<T, E> edge : mapOfNodes.get(item).connections) {
				// get the node key of the edges and add them to neighbouringNodes list
				neighbouringNodes.add(edge.nodeKey);
			}
			return neighbouringNodes;
		}
		return null;
	}
	
	/**
	 * check if two nodes are adjacent to each other
	 * @param source node
	 * @param neighbour - see if node is connected to source node
	 * @return true or false depending on condition
	 */
	public Boolean isNeighbour(T source, T neighbour) {
		if (mapOfNodes.containsKey(source)) {
			// loop through source node's adjacency list
			for (Edge<T, E> edge : mapOfNodes.get(source).connections) {
				// see if any of the edge's node key matches the specified node
				if (edge.nodeKey.equals(neighbour)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * do a depth first search of the graph
	 * @param item - starting node
	 * @return a list of nodes visited during transversal
	 */
	public List<T> depth_first_search(T item) {
		// when a node is visited, add it to the list of visited nodes
		List<T> visited = new LinkedList<>();
		// call the recursive depth first search method
		depth_first_search(item, visited);
		return visited;
	}
	
	/**
	 * recursive depth first transversal
	 * @param item - starting node
	 * @param visited - list of nodes visited
	 * @return visited list
	 */
	private List<T> depth_first_search(T item, List<T> visited) {
		// add starting node to visited list because we started there
		visited.add(mapOfNodes.get(item).value);
		// check adjacent nodes
		for (Edge<T, E> edge : mapOfNodes.get(item).connections) {
			// if the adjacent node hasn't been visited
			if (!visited.contains(edge.nodeKey)) {
				// do a depth first search on that node
				depth_first_search(edge.nodeKey, visited);
				// return an updated visited list
				return visited;
			}
		}
		return visited;
	}
	
	/**
	 * breadth first transversal of the graph
	 * @param item - node to start from
	 * @return - list of visited nodes during transversal
	 */
	public List<Node<T,E>> breadth_first_search(T item) {
		// set up a queue of nodes to look at their adjacency lists when it's their turn
		Queue<Node<T, E>> queueOfNodes = new LinkedList<>();
		// keep track of nodes visited
		List<Node<T, E>> visited = new LinkedList<>();
		// add the starting node to the queue
		queueOfNodes.add(mapOfNodes.get(item));
		// starting node is also the first node we've visited
		visited.add(mapOfNodes.get(item));
		
		// while the node queue isn't empty
		while (!queueOfNodes.isEmpty()) {
			// remove the first node in the queue
			Node<T, E> checkNode = queueOfNodes.remove();
			// let's look at the removed node's adjacency list
			for (Edge<T, E> edge : checkNode.connections) {
				// get the neighbouring edge's node key 
				Node<T, E> neighbour = mapOfNodes.get(edge.nodeKey);
				// if the neighbouring node hasn't been visited
				if (!visited.contains(neighbour)) {
					// add it to the queue so we could look at its adjacency list later
					queueOfNodes.add(neighbour);
					// and we've also visited it
					visited.add(neighbour);
				}
			}
		}
		return visited;
	}
	
	/**
	 * get a list of nodes in the graph
	 * @return a list of nodes
	 */
	public List<T> getNodes() {
		List<T> listOfNodes = new LinkedList<>();
		// the nodes are stored as keys
		for (T key : mapOfNodes.keySet()) {
			listOfNodes.add(key);
		}
		return listOfNodes;
	}
	
	/**
	 * get a specified edge between two nodes
	 * @param head - start node
	 * @param tail - adjacent node
	 * @return specified edge
	 */
	public Edge<T, E> getEdge(T head, T tail) {
		// loop through start node's edge list
		for (Edge<T, E> edge : mapOfNodes.get(head).connections) {
			// if the node key for edge is equivalent to adjacent node's
			if (edge.nodeKey.equals(tail)) {
				// return the edge
				return edge;
			}
		}
		return null;
	}
	
	/**
	 * an edge basically stores the reference to another node
	 * @param edge
	 * @return node key of the edge
	 */
	public T getEdgeKey(Edge<T, E> edge) {
		return edge.nodeKey;
	}
	
	/**
	 * get number of nodes that exist in the graph
	 * @return number of nodes
	 */
	public int numNodes() {
		return numNodes;
	}
	
	/**
	 * get the number of edges that exist in the graph
	 * @return number of edges
	 */
	public int numEdges() {
		return numEdges;
	}
	
	/**
	 * print out the nodes and their adjacent nodes
	 */
	public void print() {
		// get the node
		for (T key : mapOfNodes.keySet()) {
			System.out.print(mapOfNodes.get(key).value + ": ");
			// get the adjacent nodes
			for (Edge<T, E> edge : mapOfNodes.get(key).connections) {
				System.out.print(edge.nodeKey + ", ");
			}
			System.out.println();
		}
	}

	/**
	 * get list of edges for specified node
	 * @param item - node
	 * @return list of edges
	 */
	public List<Edge<T, E>> findAllEdges(T item) {
		List<Edge<T,E>> edgeList = new LinkedList<>();
		// loop through adjacency list
		for (Edge<T, E> edge : mapOfNodes.get(item).connections) {
			// store edges in the edgeList
			edgeList.add(edge);
		}
		return edgeList;
	}

}