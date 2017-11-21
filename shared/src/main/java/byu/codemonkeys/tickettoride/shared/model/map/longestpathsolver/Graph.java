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
	
	//	public Collection<Node<NodeType, EdgeType>> AddNodes(Collection<NodeType> values) {
	//		if (values == null)
	//			return null;
	//
	//		List<Node<NodeType, EdgeType>> list = new ArrayList<>();
	//		for(NodeType value : values) {
	//			list.add(AddNode(value));
	//		}
	//		return list;
	//	}
	
	public interface Action<NodeType, EdgeType> {
		void visit(NodeType nodeA, NodeType nodeB, EdgeType edgeSize);
		
		void pop();
	}
	
	public void DFS(NodeType startNode, Action action) {
		Map<NodeType, Boolean> visitedNodes = new HashMap<>();
		Map<pair<NodeType>, Boolean> visitedEdges = new HashMap<>();
		for (Node<NodeType, EdgeType> node : this.getNodes()) {
			visitedNodes.put(node.getValue(), false);
			
			for (Edge<NodeType, EdgeType> edge : node.getEdges()) {
				pair<NodeType> pairA = new pair<>(node.getValue(), edge.getHeadNode().getValue());
				visitedEdges.put(pairA, false);
				pair<NodeType> pairB = new pair<>(edge.getHeadNode().getValue(), node.getValue());
				visitedEdges.put(pairB, false);
			}
		}
		DFS_recurse(startNode, visitedNodes, visitedEdges, action);
	}
	
	private static class pair<NodeType> {
		private final NodeType itemA;
		private final NodeType itemB;
		
		private pair(NodeType itemA, NodeType itemB) {
			this.itemA = itemA;
			this.itemB = itemB;
		}
	}
	
	private void DFS_recurse(NodeType node,
							 Map<NodeType, Boolean> visitedNodes,
							 Map<pair<NodeType>, Boolean> visitedEdges,
							 Action action) {
		visitedNodes.put(node, true);
		for (Edge<NodeType, EdgeType> neighbor : this.get(node).getEdges()) {
			NodeType head = neighbor.getHeadNode().getValue();
			EdgeType distance = neighbor.getEdgeDistance();
			if (!visitedNodes.get(head)) {
				action.visit(node, head, distance);
				DFS_recurse(neighbor.getHeadNode().getValue(), visitedNodes, visitedEdges, action);
			}
//			} else if (!visitedEdges.get(new pair<NodeType>(head, node)) && !visitedEdges.get(new pair<NodeType>(node, head))) {
//				action.visit(node, head, distance);
//				action.pop();
//			}
		}
		action.pop();
	}
}
		
