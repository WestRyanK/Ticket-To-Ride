package byu.codemonkeys.tickettoride.shared.model.map.longestpathsolver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ryan on 11/21/2017.
 */

public class Graph<NodeType, EdgeType> {
	
	Map<NodeType, Node<NodeType, EdgeType>> nodes;
	
	public Node<NodeType, EdgeType> get(NodeType node) {
		
		if (this.nodes.containsKey(node))
			return this.nodes.get(node);
		else
			return null;
	}
	
	public int getNodeCount() {
		return this.nodes.size();
	}
	
	public Graph() {
		this.nodes = new HashMap<>();
	}
	
	public Graph(Collection<Node<NodeType, EdgeType>> nodes) {
		super();
		this.AddNodes(nodes);
		for (Node<NodeType, EdgeType> node : nodes) {
			this.AddNode(node);
		}
	}
	
	public boolean ContainsNode(NodeType nodeValue) {
		return this.nodes.containsKey(nodeValue);
	}
	
	public Node<NodeType, EdgeType> AddNode(NodeType value) {
		if (value == null)
			return null;
		Node<NodeType, EdgeType> newNode = new Node<NodeType, EdgeType>(value);
		AddNode(newNode);
		return newNode;
	}
	
	public void AddNode(Node<NodeType, EdgeType> node) {
		this.nodes.put(node.getValue(), node);
	}
	
	public void AddNodes(Collection<Node<NodeType, EdgeType>> nodes) {
		if (nodes == null)
			return;
		
		for (Node<NodeType, EdgeType> node : nodes) {
			AddNode(node);
		}
	}
	
	public Collection<Node<NodeType, EdgeType>> getNodes() {
		return this.nodes.values();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getNodeCount()).append('\n');
		for (Node<NodeType, EdgeType> node : this.getNodes()) {
			sb.append(node.toString()).append('\n');
		}
		return sb.toString();
	}
	
	public interface Action<NodeType, EdgeType> {
		void visit(NodeType nodeA, NodeType nodeB, EdgeType edgeSize);
		
		void pop();
	}
	
	public void DFS(NodeType startNode, Action action) {
		Map<NodeType, Boolean> visitedNodes = new HashMap<>();
		for (Node<NodeType, EdgeType> node : this.getNodes()) {
			visitedNodes.put(node.getValue(), false);
		}
		DFS_recurse(startNode, null, visitedNodes, action);
	}
	
	private void DFS_recurse(NodeType node, NodeType parent,
							 Map<NodeType, Boolean> visitedNodes,
							 Action action) {
		visitedNodes.put(node, true);
		for (Edge<NodeType, EdgeType> neighbor : this.get(node).getEdges()) {
			NodeType head = neighbor.getHeadNode().getValue();
			EdgeType distance = neighbor.getEdgeDistance();
			if (!visitedNodes.get(head)) {
				action.visit(node, head, distance);
				DFS_recurse(neighbor.getHeadNode().getValue(), node, visitedNodes,  action);
			}
			else if (head != parent){
				action.visit(node, head, distance);
				action.pop();
			}
		}
		action.pop();
	}
}
		
