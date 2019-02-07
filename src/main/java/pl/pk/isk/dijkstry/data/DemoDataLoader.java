package pl.pk.isk.dijkstry.data;

import pl.pk.isk.dijkstry.algh.GraphImpl;
import pl.pk.isk.dijkstry.algh.GraphNode;

public class DemoDataLoader implements DataLoader {
    @Override
    public GraphImpl loadGraph() {
        GraphImpl graph = new GraphImpl("TEst1");
        graph.setAttribute("ui.stylesheet", CssLoader.loadCss());


        GraphNode nodeA = graph.addNode( "A");
        GraphNode nodeB = graph.addNode( "B");
        GraphNode nodeC = graph.addNode( "C");
        GraphNode nodeD = graph.addNode( "D");
        GraphNode nodeE = graph.addNode( "E");
        GraphNode nodeF = graph.addNode( "F");

        nodeA.addDestination(nodeB, 10);
        nodeA.addDestination(nodeC, 15);

        nodeB.addDestination(nodeD, 12);
        nodeB.addDestination(nodeF, 15);

        nodeC.addDestination(nodeE, 10);

        nodeD.addDestination(nodeE, 2);
        nodeD.addDestination(nodeF, 1);

        nodeF.addDestination(nodeE, 5);

        return graph;
    }
}
