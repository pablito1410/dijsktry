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
    private JTextField node1 = new JTextField(5);
    private JTextField node2 = new JTextField(5);
    private JTextField distance = new JTextField(5);
    private JTextField speed = new JTextField(5);
    private JButton displayGraphButton;
    private JButton shortestPathButton;
    private JButton addNode;
    private JTextArea textArea;
    private JButton closeGraph;
    private JButton clearGraph;

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
       ui.addTextField("Node 1:", node1);
        ui.addTextField("Node 2:", node2);
        ui.addTextField("Distance:", distance);
        displayGraphButton = displayGraphButton(graph, ui);
        shortestPathButton = shortestPathButton(graph, ui);
        textArea = textArea();
        addNode = addNode(graph, ui, textArea);
        closeGraph = closeGraph(ui);
        clearGraph = clearGraph(ui, graph);
        ui.addTextField("Speed:", speed);

        ui.addButton(displayGraphButton);
        ui.addButton(shortestPathButton);
        ui.addButton(addNode);
        ui.addButton(closeGraph);
        ui.addButton(clearGraph);
        ui.addTextArea(textArea);

//        ui.addTextField("Node 1:", new JTextField(5));
//        ui.addTextField("Node 2:", new JTextField(5));
//        ui.addTextField("Distance:", new JTextField(5));
//        ui.addListener(myListener(graph));
//        pathDisplayer.show(graph.getNode("E"));

        ui.display();
    }
    private JButton clearGraph(UserInterface ui, GraphImpl graph) {
        JButton button = new JButton("Clear graph");
        button.addActionListener(e -> {
            pathDisplayer.clear(graph);
            shortestPathButton.setEnabled(true);
        });
        return button;
    }
    private JButton closeGraph(UserInterface ui) {
        JButton button = new JButton("Close graph");
        button.addActionListener(e -> {
            ui.closeGraph();
            addNode.setEnabled(true);
            displayGraphButton.setEnabled(true);
            node1.setEnabled(true);
            node2.setEnabled(true);
            distance.setEnabled(true);
            closeGraph.setEnabled(false);
        });
        return button;
    }

    private JTextArea textArea() {
        return new JTextArea();
    }

    private JButton addNode(GraphImpl graph, UserInterface ui, JTextArea textArea) {
        JButton button = new JButton("Add node");
        ActionListener worker = (e) -> {
            if (node1.getText() == null || node1.getText().isEmpty()) {
                textArea.setText("Select node 1");
                return;
            }
            if (node2.getText() == null || node2.getText().isEmpty()) {
                textArea.setText("Select node 2");
                return;
            }
            if (distance.getText() == null || distance.getText().isEmpty()) {
                textArea.setText("Select distance");
                return;
            }
            GraphNode node1 = graph.addNode(this.node1.getText());
            GraphNode node2 = graph.addNode( this.node2.getText());
            node1.addDestination(node2, Integer.valueOf(this.distance.getText()));
            textArea.setText(this.node1.getText() + " -- " + this.distance.getText() +
                    " -- > " + this.node2.getText() + "\n");
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
        button.addActionListener(e -> createNewWorker(graph, ui).execute());
//        button.addActionListener(e -> {
//            dijkstry.run(graph, graph.getNode(ui.getNode1()));
//            pathDisplayer.show(graph.getNode(ui.getNode2()));
//        });
        return button;
    }

    private SwingWorker createNewWorker(GraphImpl graph, UserInterface ui) {
        return new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                try {
                    if (node1.getText() == null || node1.getText().isEmpty()) {
                        textArea.setText("Select node 1");
                        return null;
                    }
                    if (node2.getText() == null || node2.getText().isEmpty()) {
                        textArea.setText("Select node 2");
                        return null;
                    }
                    if (speed.getText() == null || speed.getText().isEmpty()) {
                        textArea.setText("Select speed");
                        return null;
                    }
                    if (graph.getNode(node1.getText()) == null) {
                        textArea.setText("Node " + node1.getText() + " not found");
                        return null;
                    }
                    if (graph.getNode(node2.getText()) == null) {
                        textArea.setText("Node " + node2.getText() + " not found");
                        return null;
                    }
                    node1.setEnabled(false);
                    node2.setEnabled(false);
                    shortestPathButton.setEnabled(false);
                    pathDisplayer.setSpeed(Long.valueOf(speed.getText()));
                    pathDisplayer.clear(graph);
                    dijkstry.run(graph, graph.getNode(node1.getText()));
                    pathDisplayer.show(graph.getNode(node1.getText()), graph.getNode(node2.getText()));
                    node1.setEnabled(true);
                    node2.setEnabled(true);
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    private JButton displayGraphButton(GraphImpl graph, UserInterface ui) {
        JButton button = new JButton("Display");
        button.addActionListener(e -> {
            distance.setEnabled(false);
            addNode.setEnabled(false);
            displayGraphButton.setEnabled(false);
            closeGraph.setEnabled(true);
            Viewer graphViewer = graph.display();
            ui.setGraphViewer(graphViewer);
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
