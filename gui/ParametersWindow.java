package gui;

import gol.Mode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ParametersWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private TextField dim;
    private JComboBox<Mode> mode;

    public ParametersWindow() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(200, 200);
    }

    public void createUI() {
        this.add(createMenuPanel());
        this.setVisible(true);
    }

    private JPanel createMenuPanel() {
        var res = new JPanel();

        res.setLayout(new BorderLayout());
        centerize(res);
        res.add(createForm(), BorderLayout.CENTER);

        return res;
    }

    private JPanel createForm() {
        var res = new JPanel();
        res.setLayout(new GridLayout(5, 1));
        res.add(new JLabel("Set parameters:"));

        dim = createDimField();
        res.add(dim);

        mode = createModeCombo();
        res.add(mode);

        var submit = createSubmitButton(dim);
        res.add(submit);

        var cancel = createCancelButton(this);
        res.add(cancel);

        return res;
    }

    private TextField createDimField() {
        return new TextField("Dimension");
    }

    private JComboBox<Mode> createModeCombo() {
        var res = new JComboBox<>(Mode.values());
        res.setBackground(Color.WHITE);
        return res;
    }

    private Button createSubmitButton(TextField dim) {
        var submit = new Button("Submit", dim);
        submit.addActionListener(e -> createApp(getDim(), getMode()));
        return submit;
    }

    private int getDim() {
        return Integer.parseInt(dim.getFieldText());
    }

    private Mode getMode() {
        return (Mode) mode.getSelectedItem();
    }

    private void createApp(int dim, Mode mode) {
        var app = new GameOfLifeApp(dim, mode);
        app.createUI();
        this.dispose();
    }

    private JButton createCancelButton(JFrame window) {
        var cancel = new JButton("Cancel");
        cancel.addActionListener(e -> window.dispose());
        return cancel;
    }

    private static void centerize(JPanel panel) {
        panel.add(new JPanel(), BorderLayout.NORTH);
        panel.add(new JPanel(), BorderLayout.SOUTH);
        panel.add(new JPanel(), BorderLayout.EAST);
        panel.add(new JPanel(), BorderLayout.WEST);
    }
}
