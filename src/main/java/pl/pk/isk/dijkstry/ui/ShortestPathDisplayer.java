package pl.pk.isk.dijkstry.ui;

import org.graphstream.graph.Edge;
import pl.pk.isk.dijkstry.algh.GraphNode;

import java.util.ListIterator;

public class ShortestPathDisplayer implements PathDisplayer {

    private final long sleep;

    public ShortestPathDisplayer(long sleep) {
        this.sleep = sleep;
    }

    @Override
    public void markEdgeAndNode(GraphNode from, GraphNode node) {
        Edge edge = node.getEdgeFrom(from);
        edge.addAttribute("ui.class", "marked");
        node.setAttribute("ui.class", "marked");
        sleep();
    }

    @Override
    public void unmarkEdgeAndNode(GraphNode from, GraphNode node) {
        Edge edge = node.getEdgeFrom(from);
        edge.removeAttribute("ui.class");
        node.removeAttribute("ui.class");
        sleep();
    }

    @Override
    public void show(GraphNode dest) {
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

    private void sleep() {
        try { Thread.sleep(sleep); } catch (InterruptedException e) { }
    }
}
