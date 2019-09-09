package gui;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {
    private int dim;

    private int[][] map;
    private JPanel[][] cells;

    public MapPanel(int[][] map) {
        this.map = map;
        this.dim = map.length;

        this.setLayout(new GridLayout(dim, dim));
        this.createCells();
    }

    private void createCells() {
        this.cells = new JPanel[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                cells[i][j] = new JPanel();
                setCell(i, j, map[i][j]);
                this.add(cells[i][j]);
            }
        }
    }

    public void set() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                setCell(i, j, map[i][j]);
            }
        }
    }

    public void setCell(int i, int j, int value){
        if (value == 1)
            cells[i][j].setBackground(Color.RED);
        else
            cells[i][j].setBackground(Color.WHITE);
    }
}