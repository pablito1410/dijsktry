package pl.pk.isk.dijkstry.ui;

import org.graphstream.graph.Edge;
import pl.pk.isk.dijkstry.algh.GraphImpl;
import pl.pk.isk.dijkstry.algh.GraphNode;

import javax.swing.*;
import java.util.ListIterator;

public class ShortestPathDisplayer implements PathDisplayer {

    private long sleep;

    public ShortestPathDisplayer(long sleep) {
        this.sleep = sleep;
    }

    @Override
    public void markEdgeAndNode(GraphNode from, GraphNode node) {
        Edge edge = node.getEdgeFrom(from);
        if (edge == null) {
            return;
        }
        edge.addAttribute("ui.class", "marked");
        node.setAttribute("ui.class", "marked");
        sleep();
    }

    @Override
    public void unmarkEdgeAndNode(GraphNode from, GraphNode node) {
        Edge edge = node.getEdgeFrom(from);
        if (edge == null) {
            return;
        }
        edge.removeAttribute("ui.class");
        node.removeAttribute("ui.class");
        sleep();
    }

    @Override
    public void show(GraphNode from, GraphNode dest, JTextArea textArea) {
        GraphNode prev = null;
        for (ListIterator<GraphNode> it = dest.getShortestPath().listIterator(); it.hasNext(); ) {
            GraphNode node = it.next();
            if (prev != null) {
                Edge edge = node.getEdgeFrom(prev);
                edge.addAttribute("ui.class", "marked");
            }

            node.setAttribute("ui.class", "marked");
            sleep();
            prev = node;

        }
        if (prev != null) {
            Edge edge = dest.getEdgeFrom(prev);
            edge.addAttribute("ui.class", "marked");
        }
        dest.setAttribute("ui.class", "marked");
    }

    @Override
    public void markNode(GraphNode node) {
        node.setAttribute("ui.class", "marked");
        sleep();
    }

    @Override
    public void unmarkNode(GraphNode node) {
        if (node == null) {
            return;
        }
        node.removeAttribute("ui.class");
        sleep();
    }

    @Override
    public void clear(GraphImpl graph) {
        graph.forEach(node -> {
            node.getEdgeSet().forEach(edge -> unmarkEdge(edge));
            if (node == null) {
                return;
            }
            node.removeAttribute("ui.class");
        });
    }

    @Override
    public void setSpeed(long speed) {
        this.sleep = speed;
    }

    private void unmarkEdge(Edge edge) {
        edge.removeAttribute("ui.class");
    }

    private void sleep() {
        try { Thread.sleep(sleep); } catch (InterruptedException e) { }
    }
}
