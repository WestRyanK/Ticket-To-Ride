package byu.codemonkeys.tickettoride.shared.model.map.longestpathsolver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ryan on 11/21/2017.
 */

/// <summary>
/// Represents a single node in a graph.
/// </summary>
public class Node<NodeType, EdgeType> {
	public Collection<Edge<NodeType, EdgeType>> getEdges() {
		return edges.values();
	}
	
	private Map<NodeType, Edge<NodeType, EdgeType>> edges;
	
	public Edge<NodeType, EdgeType> get(NodeType node) {
		if (HasEdge(node))
			return this.edges.get(node);
		else
			return null;
	}
	
	public NodeType getValue() {
		return _value;
	}
	
	private final NodeType _value;
	
	public int getEdgeCount() {
		return this.edges.size();
	}
	
	public Node(NodeType value) {
		this._value = value;
		this.edges = new HashMap<>();
	}
	
	/**
	 * Adds an edge from this node to headNode.
	 *
	 * @param headNode     The node to which this edge connects
	 * @param edgeDistance The weight/distance for the edge
	 * @return The added edge
	 */
	public Edge<NodeType, EdgeType> addEdge(Node<NodeType, EdgeType> headNode,
											EdgeType edgeDistance) {
		Edge<NodeType, EdgeType> newEdge = new Edge<NodeType, EdgeType>(headNode, edgeDistance);
		this.edges.put(headNode.getValue(), newEdge);
		return newEdge;
	}
	
	public boolean HasEdge(Node<NodeType, EdgeType> node) {
		return HasEdge(node.getValue());
	}
	
	public boolean HasEdge(NodeType nodeValue) {
		return this.edges.containsKey(nodeValue);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(").append(this.getValue()).append(", Neighbors: ");
		for(Edge<NodeType, EdgeType> edge : this.edges.values())
		{
			sb.append(edge.getHeadNode().getValue().toString()).append(" \n");
		}
		sb.append(")\n");
		return sb.toString();
	}
}
