package pl.pk.isk.dijkstry.algh;

import pl.pk.isk.dijkstry.ui.PathDisplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class DijkstryImpl {

    private final PathDisplayer displayer;

    public DijkstryImpl(PathDisplayer displayer) {
        this.displayer = displayer;
    }

    public GraphImpl run(GraphImpl graph, GraphNode source, JTextArea textArea, boolean stepwise) {
        graph.clearPaths();
        source.setDistance(0);

        Set<GraphNode> settledNodes = new HashSet<>();
        Set<GraphNode> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            GraphNode currentNode = getLowestDistanceNode(unsettledNodes);
            displayCalculations(textArea, graph, source.getId());
            displayer.markNode(currentNode);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<GraphNode, Integer> adjacencyPair
                    : currentNode.getAdjacentNodes().entrySet()) {
                GraphNode adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();

                displayer.markEdgeAndNode(currentNode, adjacentNode);
//                textArea.setText(String.format("%s --> %s %d\n",
//                        currentNode.getId(), adjacentNode.getId(), edgeWeight));
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode, textArea);
                    unsettledNodes.add(adjacentNode);
                }
                waitForSpace(stepwise);
                displayer.unmarkEdgeAndNode(currentNode, adjacentNode);
            }
            displayer.unmarkNode(currentNode);
            settledNodes.add(currentNode);
        }
        waitForSpace(stepwise);
        return graph;
    }

    private void displayCalculations(JTextArea textArea, GraphImpl graph, String id) {
        StringBuilder sb = new StringBuilder();
        graph.getNodeSet().forEach(source ->
                ((GraphNode)source).getShortestPath().forEach(path -> {
                    if (path.getId().equals(id) && ((GraphNode)source).getDistance() > 0)
                        sb.append(path.getId() + " -- >" + source.getId() + " " + ((GraphNode)source).getDistance() + "\n");
            }));
        textArea.setText(sb.toString());
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
                                                 Integer edgeWeigh, GraphNode sourceNode, JTextArea textArea) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<GraphNode> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
//            textArea.append(shortestPath.get(0) + " --> " + evaluationNode.getId() + " " + evaluationNode.getDistance());
        }
    }

    public void waitForSpace(boolean stepwise) {
        if (!stepwise) {
            return;
        }
        final CountDownLatch latch = new CountDownLatch(1);
        KeyEventDispatcher dispatcher = new KeyEventDispatcher() {
            // Anonymous class invoked from EDT
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE)
                    latch.countDown();
                return false;
            }
        };
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(dispatcher);
        try {
            latch.await();
        } catch (InterruptedException e) {

        }
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(dispatcher);
    }
}
