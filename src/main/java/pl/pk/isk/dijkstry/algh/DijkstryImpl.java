package pl.pk.isk.dijkstry.algh;

import pl.pk.isk.dijkstry.ui.PathDisplayer;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class DijkstryImpl {

    private final PathDisplayer displayer;

    public DijkstryImpl(PathDisplayer displayer) {
        this.displayer = displayer;
    }

    public GraphImpl run(GraphImpl graph, GraphNode source) {
        source.setDistance(0);

        Set<GraphNode> settledNodes = new HashSet<>();
        Set<GraphNode> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            GraphNode currentNode = getLowestDistanceNode(unsettledNodes);
            displayer.markNode(currentNode);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<GraphNode, Integer> adjacencyPair
                    : currentNode.getAdjacentNodes().entrySet()) {
                GraphNode adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();

                displayer.markEdgeAndNode(currentNode, adjacentNode);

                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
                displayer.unmarkEdgeAndNode(currentNode, adjacentNode);
            }
            displayer.unmarkNode(currentNode);
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private static GraphNode getLowestDistanceNode(Set < GraphNode > unsettledNodes) {
        GraphNode lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (GraphNode node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(GraphNode evaluationNode,
                                                 Integer edgeWeigh, GraphNode sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<GraphNode> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }
}
