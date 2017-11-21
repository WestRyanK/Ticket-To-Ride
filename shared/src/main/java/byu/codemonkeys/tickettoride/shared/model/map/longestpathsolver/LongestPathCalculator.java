package byu.codemonkeys.tickettoride.shared.model.map.longestpathsolver;

import java.util.Stack;

import byu.codemonkeys.tickettoride.shared.model.UserBase;
import byu.codemonkeys.tickettoride.shared.model.map.City;
import byu.codemonkeys.tickettoride.shared.model.map.GameMap;
import byu.codemonkeys.tickettoride.shared.model.map.Route;

/**
 * Created by Ryan on 11/21/2017.
 */

public class LongestPathCalculator {
	public static int findLongestPath(GameMap map, UserBase user) {
		Graph<City, Integer> graph = createGraph(map, user);
		int longestPathLength = 0;
		for (Node<City, Integer> startNode : graph.getNodes()) {
			int pathLength = findLongestPathFromNode(graph, startNode);
			if (pathLength > longestPathLength)
				longestPathLength = pathLength;
		}
		return longestPathLength;
	}
	
	private static int findLongestPathFromNode(Graph<City, Integer> graph,
											   Node<City, Integer> startNode) {
		PathSummerator action = new PathSummerator();
		graph.DFS(startNode.getValue(), action);
		return action.getPathLength();
	}
	
	private static class PathSummerator implements Graph.Action<City, Integer> {
		
		public int getPathLength() {
			return maxLength;
		}
		
		private Stack<Integer> pathStack;
		private int maxLength;
		
		public PathSummerator() {
			this.pathStack = new Stack<>();
		}
		
		@Override
		public void visit(City nodeA, City nodeB, Integer edgeSize) {
			if (edgeSize != null)
				pathStack.push(edgeSize);
		}
		
		@Override
		public void pop() {
			int length = 0;
			for (int segmentLength : pathStack) {
				length += segmentLength;
			}
			if (length > maxLength)
				maxLength = length;
			if (pathStack.size() > 0)
				pathStack.pop();
		}
	}
	
	private static Graph<City, Integer> createGraph(GameMap map, UserBase user) {
		Graph<City, Integer> graph = new Graph<>();
		for (Route route : map.getAllRoutes()) {
			if (route.isClaimed() && route.getOwner().getUsername().equals(user.getUsername())) {
				Node<City, Integer> nodeA = graph.get(route.getSource());
				if (nodeA == null) {
					nodeA = graph.AddNode(route.getSource());
				}
				Node<City, Integer> nodeB = graph.get(route.getDestination());
				if (nodeB == null) {
					nodeB = graph.AddNode(route.getDestination());
				}
				nodeA.addEdge(nodeB, route.getLength());
				nodeB.addEdge(nodeA, route.getLength());
			}
		}
		
		return graph;
	}
}
