package pl.pk.isk.dijkstry.algh;

import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GraphNode extends SingleNode {

    private int distance = Integer.MAX_VALUE;
    Map<GraphNode, Integer> adjacentNodes = new HashMap<>();
    private List<GraphNode> shortestPath = new LinkedList<>();

    public GraphNode(AbstractGraph graph, String id) {
        super(graph, id);
    }

    public void addDestination(GraphNode destination, int distance) {
        adjacentNodes.put(destination, distance);
        Edge edge = graph.addEdge(getId() + destination.getId(), this, destination, ((GraphImpl)graph).isDirected());
        edge.addAttribute("ui.label", distance);

        if (!((GraphImpl)graph).isDirected()) {
            destination.getAdjacentNodes().put(this, distance);
            Edge edge2 = graph.addEdge(getId() + destination.getId(), destination, this, ((GraphImpl)graph).isDirected());
            edge2.addAttribute("ui.label", distance);
        }
    }

    public void addDestinationToExistingEdge(GraphNode destination, int distance, boolean directed) {
        adjacentNodes.put(destination, distance);
        if (!directed) {
            destination.getAdjacentNodes().put(this, distance);
        }
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Map<GraphNode, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public int getDistance() {
        return distance;
    }

    public List<GraphNode> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(LinkedList<GraphNode> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public void clearPaths() {
        this.distance = Integer.MAX_VALUE;
        this.shortestPath.clear();
    }
}
