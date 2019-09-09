package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import gol.GameOfLife;
import gol.Mode;

public class GameOfLifeApp extends JFrame {

    private static final long serialVersionUID = 1L;

    private GameOfLife gol;

    private MapPanel map;
    private JTextField counter;

    private boolean started = false;
    private boolean paused = true;

    public GameOfLifeApp(int dim, Mode mode) {
        this.setTitle("Game of Life");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setSize(490, 480);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        this.gol = new GameOfLife(dim, mode, this);
    }

    public void createUI() {
        this.add(createMap(), BorderLayout.CENTER);
        this.add(createInterface(), BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private MapPanel createMap() {
        map = new MapPanel(gol.getMap());
        return map;
    }

    private JPanel createInterface() {
        JPanel res = new JPanel();
        res.setLayout(new BorderLayout());

        res.add(createButtonPanel(), BorderLayout.WEST);

        counter = createCounter();
        res.add(counter, BorderLayout.CENTER);

        return res;
    }

    private JPanel createButtonPanel() {
        var res = new JPanel();
        res.setLayout(new GridLayout(1, 3));

        res.add(createStartButton());
        res.add(createPauseButton());
        res.add(createParametersButton());

        return res;
    }

    private JButton createStartButton() {
        var res = new JButton("Start");

        res.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            started = !started;
            if (started) {
                res.setText("Stop");
                gol.run();
            } else {
                res.setText("Start");
                gol.abort();
            }
        }));

        return res;
    }

    private JButton createPauseButton() {
        var res = new JButton("Pause");

        res.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            paused = !paused;
            if (!paused) {
                res.setText("Pause");
                gol.pause(false);
            } else {
                res.setText("Resume");
                gol.pause(true);
            }
        }));

        return res;
    }

    private JButton createParametersButton() {
        var res = new JButton("Change parameters");

        var app = this;
        res.addActionListener(e -> {
            new ParametersWindow().createUI();
            app.dispose();
        });

        return res;
    }

    private JTextField createCounter() {
        var res = new JTextField(0 + "");
        res.setBackground(Color.WHITE);
        res.setEditable(false);

        return res;
    }

    public void setCounter(int val) {
        SwingUtilities.invokeLater(() -> counter.setText(val + ""));
    }

    public void setMap() {
        SwingUtilities.invokeLater(() -> map.set());
    }
}
