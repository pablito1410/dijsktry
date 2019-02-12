package pl.pk.isk.dijkstry.ui;

import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

import javax.swing.*;
import java.util.Optional;

public class UserInterface {
    private Viewer viewer;
    private LayoutFrame layoutFrame;
//    private JTextField node1 = new JTextField(5);
//    private JTextField node2 = new JTextField(5);
//    private JTextField distance = new JTextField(5);
    private Viewer graphViewer;

    public UserInterface(Viewer viewer, LayoutFrame layoutFrame) {
        this.viewer = viewer;
//        this.viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
        this.viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
        this.layoutFrame = layoutFrame;
//        addTextField("Node 1:", node1);
//        addTextField("Node 2:", node2);
//        addTextField("Distance:", distance);
//        this.layoutFrame.addViewer(viewer);
//        this.layoutFrame.addSlider();
//        this.layoutFrame.addButton();
    }

    public void addListener(ViewerListener listener) {
        ViewerPipe vp = viewer.newViewerPipe();
        vp.addViewerListener(listener);
    }

    public void display() {
        layoutFrame.display();
    }

    public void addSlider() {
        layoutFrame.addSlider();
    }

    public void addButton(JButton button) {
        layoutFrame.addButton(button);
    }

    public void addTextField(String text, JTextField textField) {
        layoutFrame.addTextField(text, textField);
    }

//    public String getNode1() {
//        return node1.getText();
//    }
//
//    public String getNode2() {
//        return node2.getText();
//    }
//
//    public int getDistance() {
//        return Integer.valueOf(distance.getText());
//    }

    public void addTextArea(JTextArea textArea) {
        layoutFrame.addTextArea(textArea);
    }

    public void closeGraph() {
        graphViewer.close();
    }

    public void setGraphViewer(Viewer graphViewer) {
        this.graphViewer = graphViewer;
    }
}
