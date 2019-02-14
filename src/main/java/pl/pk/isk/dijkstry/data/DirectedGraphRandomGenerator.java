package pl.pk.isk.dijkstry.data;


import org.graphstream.algorithm.Toolkit;
import org.graphstream.algorithm.generator.BaseGenerator;
import org.graphstream.graph.Edge;
import pl.pk.isk.dijkstry.algh.GraphImpl;
import pl.pk.isk.dijkstry.algh.GraphNode;

import java.util.Random;

public class DirectedGraphRandomGenerator extends BaseGenerator {
    protected int nodeNames;
    private GraphImpl graph;
    private boolean directed;

    public DirectedGraphRandomGenerator(double averageDegree, GraphImpl graph, boolean directed) {
        super(directed, false);
        this.graph = graph;
        this.directed = directed;
        this.nodeNames = 0;
        this.setUseInternalGraph(true);
    }

    public void begin() {
        this.random = this.random == null?new Random(System.currentTimeMillis()):this.random;
        this.addNode("0");
        this.addNode("1");
        this.addNode("2");
        this.addEdge("0-1", "0", "1");
        this.addEdge("1-2", "1", "2");
        this.addEdge("2-0", "2", "0");

        if (!directed) {
            this.addEdge("1-0", "1", "0");
            this.addEdge("2-1", "2", "1");
            this.addEdge("0-2", "0", "2");
        }
        this.nodeNames = 3;
    }

    public boolean nextEvents() {
        String name = Integer.toString(this.nodeNames++);
        Edge edge = Toolkit.randomEdge(this.internalGraph, this.random);
        String n0 = edge.getNode0().getId();
        String n1 = edge.getNode1().getId();
        this.addNode(name);
        this.addEdge(n0 + "-" + name, n0, name);

        this.addEdge(n1 + "-" + name, n1, name);
        GraphNode destination = graph.getNode(name);

        int random1 = random.nextInt(9) + 1;
        int random2 = random.nextInt(9) + 1;
        graph.getNode(n0).addDestinationToExistingEdge(destination, random1, directed);
            graph.getEdge(n0 + "-" + name).addAttribute("ui.label", random1);
        graph.getNode(n1).addDestinationToExistingEdge(destination, random2, directed);
        graph.getEdge(n1 + "-" + name).addAttribute("ui.label", random2);
        return true;
    }
}