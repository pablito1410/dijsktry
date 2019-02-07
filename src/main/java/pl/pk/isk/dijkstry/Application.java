package pl.pk.isk.dijkstry;

import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import pl.pk.isk.dijkstry.algh.DijkstryImpl;
import pl.pk.isk.dijkstry.algh.GraphImpl;
import pl.pk.isk.dijkstry.algh.GraphNode;
import pl.pk.isk.dijkstry.data.CssLoader;
import pl.pk.isk.dijkstry.data.DataLoader;
import pl.pk.isk.dijkstry.data.DemoDataLoader;
import pl.pk.isk.dijkstry.ui.LayoutFrame;
import pl.pk.isk.dijkstry.ui.PathDisplayer;
import pl.pk.isk.dijkstry.ui.ShortestPathDisplayer;
import pl.pk.isk.dijkstry.ui.UserInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application {

    private final DataLoader dataLoader;
    private final DijkstryImpl dijkstry;
    private final PathDisplayer pathDisplayer;

    public Application(DataLoader dataLoader, DijkstryImpl dijkstry, PathDisplayer pathDisplayer) {
        this.dataLoader = dataLoader;
        this.dijkstry = dijkstry;
        this.pathDisplayer = pathDisplayer;
    }

    public static void main(final String[] args) {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        final DataLoader dataLoader = new DemoDataLoader();
        final PathDisplayer pathDisplayer = new ShortestPathDisplayer(300);
        final DijkstryImpl dijkstry = new DijkstryImpl(pathDisplayer);

        Application app = new Application(dataLoader, dijkstry, pathDisplayer);
        app.run();


    }

    private void run() {
        GraphImpl graph = dataLoader.loadGraph(); //new GraphImpl("Test1");
//        graph.setAttribute("ui.stylesheet", CssLoader.loadCss());

        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
//        viewer.enableAutoLayout();
        UserInterface ui = new UserInterface(viewer, new LayoutFrame());
        ui.addButton(displayGraphButton(graph));
        ui.addButton(shortestPathButton(graph, ui));
        ui.addButton(addNode(graph, ui));
//        ui.addTextField("Node 1:", new JTextField(5));
//        ui.addTextField("Node 2:", new JTextField(5));
//        ui.addTextField("Distance:", new JTextField(5));
//        ui.addListener(myListener(graph));
//        pathDisplayer.show(graph.getNode("E"));

        ui.display();
    }


    private JButton addNode(GraphImpl graph, UserInterface ui) {
        JButton button = new JButton("Add node");
        ActionListener worker = (e) -> {
                GraphNode node1 = graph.addNode( ui.getNode1());
                GraphNode node2 = graph.addNode( ui.getNode2());
                node1.addDestination(node2, ui.getDistance());
            graph.setAttribute("ui.stylesheet", CssLoader.loadCss());
        };
        button.addActionListener(e -> worker.actionPerformed(null));
        return button;
    }

    private void sleep(int val) {
        try {
            Thread.sleep(val);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private JButton shortestPathButton(GraphImpl graph, UserInterface ui) {
        JButton button = new JButton("Shortest path");
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                dijkstry.run(graph, graph.getNode(ui.getNode1()));
                pathDisplayer.show(graph.getNode(ui.getNode2()));
                return null;
            }
        };
        button.addActionListener(e -> worker.execute());
        return button;
    }

    private JButton displayGraphButton(GraphImpl graph) {
        JButton button = new JButton("Display");
        button.addActionListener(e -> {
            graph.display();
        });
        return button;
    }

    private ViewerListener myListener(GraphImpl graph) {
        return new ViewerListener() {
            @Override
            public void viewClosed(String viewName) {
                // dont care
            }

            @Override
            public void buttonPushed(String id) {
                Node n = graph.getNode(id);
                String attributes[] = n.getAttributeKeySet().toArray(new String[n.getAttributeKeySet().size()]);

                String attributeToChange = (String) JOptionPane.showInputDialog(null, "Select attibute to modify", "Attribute for " + id, JOptionPane.QUESTION_MESSAGE, null, attributes, attributes[0]);
                String curValue = n.getAttribute(attributeToChange);
                String newValue
                        = JOptionPane.showInputDialog("New Value", curValue);
                n.setAttribute(attributeToChange, newValue);
            }

            @Override
            public void buttonReleased(String id) {
                // don't care
            }
        };
    }

}
