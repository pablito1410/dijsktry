package pl.pk.isk.dijkstry.ui;

import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;

public class LayoutFrame {

    private final JFrame frame = new JFrame();
    private final JSlider slider = new JSlider();
    private DefaultView graphView;

    public LayoutFrame() {
        frame.setPreferredSize(new Dimension(400, 400));
        frame.setLayout(new FlowLayout());

    }

    void addViewer(Viewer viewer) {
        graphView = (DefaultView) viewer.addDefaultView(false);   // false indicates "no JFrame".
        graphView.setPreferredSize(new Dimension(400, 400));
        frame.add(graphView);
        viewer.enableAutoLayout();
    }

    public void addSlider() {
        slider.addChangeListener(e -> graphView.getCamera().setViewPercent(slider.getValue() / 10.0));
        frame.add(slider);
    }

    public void addButton(JButton button) {
        frame.add(button);
    }

    public void display() {
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addTextField(String text, JTextField textField) {
        frame.add(new JLabel(text));
        frame.add(textField);}

    public void addTextArea(JTextArea textArea) {
        frame.add(textArea);
    }

    public void addCheckbox(JCheckBox stepwise) {
        frame.add(stepwise);
    }

    public void addScroll(JScrollPane sp) {
        frame.getContentPane().add(sp);
    }
}
