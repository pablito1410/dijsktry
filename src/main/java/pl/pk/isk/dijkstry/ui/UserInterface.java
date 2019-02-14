package pl.pk.isk.dijkstry.ui;

import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

import javax.swing.*;
import java.util.Optional;

public class UserInterface {
    private Viewer viewer;
    private LayoutFrame layoutFrame;
    private Viewer graphViewer;

    public UserInterface(Viewer viewer, LayoutFrame layoutFrame) {
        this.viewer = viewer;
        this.viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
        this.layoutFrame = layoutFrame;
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

    public void addTextArea(JTextArea textArea) {
        layoutFrame.addTextArea(textArea);
    }

    public void closeGraph() {
        graphViewer.close();
    }

    public void setGraphViewer(Viewer graphViewer) {
        this.graphViewer = graphViewer;
    }

    public void addCheckbox(JCheckBox stepwise) {
        layoutFrame.addCheckbox(stepwise);
    }

    public void addScroll(JScrollPane sp) {
        layoutFrame.addScroll(sp);
    }
}
