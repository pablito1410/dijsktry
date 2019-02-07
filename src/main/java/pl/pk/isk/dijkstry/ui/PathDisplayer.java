package pl.pk.isk.dijkstry.ui;

import pl.pk.isk.dijkstry.algh.GraphNode;

public interface PathDisplayer {

    void markEdgeAndNode(GraphNode from, GraphNode node);

    void unmarkEdgeAndNode(GraphNode from, GraphNode node);

    void show(GraphNode dest);

    void markNode(GraphNode currentNode);

    void unmarkNode(GraphNode currentNode);
}
