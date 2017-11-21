package byu.codemonkeys.tickettoride.shared.model.map.longestpathsolver;

/**
 * Created by Ryan on 11/21/2017.
 */

public class Edge<NodeType, EdgeType> {
	private final Node<NodeType, EdgeType> headNode;
	
	public Node<NodeType, EdgeType> getHeadNode() {
		return headNode;
	}
	
	public EdgeType getEdgeDistance() {
		return edgeDistance;
	}
	
	private final EdgeType edgeDistance;
	
	public Edge(Node<NodeType, EdgeType> headNode, EdgeType edgeDistance) {
		this.headNode = headNode;
		this.edgeDistance = edgeDistance;
	}
	
	@Override
	public String toString() {
		return String.format("($1, $2)", headNode, edgeDistance);
	}
}

