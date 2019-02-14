package pl.pk.isk.dijkstry.data;


import org.graphstream.algorithm.Toolkit;
import org.graphstream.algorithm.generator.BaseGenerator;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.generator.RandomEuclideanGenerator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.algorithm.util.RandomTools;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import pl.pk.isk.dijkstry.algh.GraphImpl;
import pl.pk.isk.dijkstry.algh.GraphNode;

import java.util.Iterator;
import java.util.Random;

public class DirectedGraphRandomGenerator extends BaseGenerator {
    protected int nodeNames;
    private GraphImpl graph;

    public DirectedGraphRandomGenerator(GraphImpl graph) {
        super(false, false);
        this.graph = graph;
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
        this.nodeNames = 3;
    }

    public void end() {
        super.end();
    }
//    private GraphImpl graph;
//
//    public DirectedGraphRandomGenerator(GraphImpl graph) {
//        super();
//        this.graph = graph;
//    }
//
////    private double distance(String n1, String n2) {
////        double d = 0.0D;
////        double i;
////        double y1;
////        double x2;
////        double y2;
////        if(this.dimension == 2) {
////            i = this.internalGraph.getNode(n1).getNumber("x");
////            y1 = this.internalGraph.getNode(n1).getNumber("y");
////            x2 = this.internalGraph.getNode(n2).getNumber("x");
////            y2 = this.internalGraph.getNode(n2).getNumber("y");
////            d = Math.pow(i - x2, 2.0D) + Math.pow(y1 - y2, 2.0D);
////        } else if(this.dimension == 3) {
////            i = this.internalGraph.getNode(n1).getNumber("x");
////            y1 = this.internalGraph.getNode(n1).getNumber("y");
////            x2 = this.internalGraph.getNode(n2).getNumber("x");
////            y2 = this.internalGraph.getNode(n2).getNumber("y");
////            double z1 = this.internalGraph.getNode(n1).getNumber("z");
////            double z2 = this.internalGraph.getNode(n2).getNumber("z");
////            d = Math.pow(z1 - z2, 2.0D) + Math.pow(i - x2, 2.0D) + Math.pow(y1 - y2, 2.0D);
////        } else {
////            for(int var17 = 0; var17 < this.dimension; ++var17) {
////                double xi1 = this.internalGraph.getNode(n1).getNumber("x" + var17);
////                double xi2 = this.internalGraph.getNode(n2).getNumber("x" + var17);
////                d += Math.pow(xi1 - xi2, 2.0D);
////            }
////        }
////
////        return Math.sqrt(d);
////    }
//
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
        graph.getNode(n0).addDestinationToExistingEdge(destination, random1);
            graph.getEdge(n0 + "-" + name).addAttribute("ui.label", random1);
        graph.getNode(n1).addDestinationToExistingEdge(destination, random2);
        graph.getEdge(n1 + "-" + name).addAttribute("ui.label", random2);
        return true;
    }
//
////    @Override
////    protected void addNewEdges(double p) {
////        RandomTools.randomPsubset(this.nodeCount, p, this.subset, this.random);
////        String nodeId = this.nodeCount + "";
////        Iterator i$ = this.subset.iterator();
////
////        while(i$.hasNext()) {
////            int i = ((Integer)i$.next()).intValue();
////            String edgeId = i + "_" + nodeId;
////            this.addEdge(edgeId, i + "", nodeId);
////
////            GraphNode destination = graph.getNode(nodeId);
////            graph.getNode(i + "").addDestinationToExistingEdge(destination, 2);
////            graph.getEdge(edgeId).addAttribute("ui.label", 2);
////            if(this.allowRemove) {
////                this.edgeIds.add(edgeId);
////            }
////        }
////
////    }
}