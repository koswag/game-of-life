package gol;

import gui.GameOfLifeApp;

public class GameOfLife implements Runnable {

    private int[][] map;
    private int dim;

    private Thread mainLoop;
    private int counter;
    private volatile boolean running;
    private volatile boolean paused;

    private static final int GRID_START = 0;
    private GameOfLifeApp gui;

    public GameOfLife(int dim, Mode mode, GameOfLifeApp gui) {
        this.dim = dim;
        this.gui = gui;

        map = createMap(dim);
        fill(mode);

        this.mainLoop = createMainLoop();
    }

    private Thread createMainLoop() {
        return new Thread(() -> {
            try {
                while (running) {
                    loop();
                }
            } catch (InterruptedException e) {
                abort();
                System.err.println(e.getMessage());
            }
        });
    }

    private void loop() throws InterruptedException {
        if (!paused) {
            Thread.sleep(100);
            checkNeighbours();
            counter++;

            gui.setMap();
            gui.setCounter(counter);
        }
    }


    @Override
    public void run() {
        running = true;
        mainLoop.start();
    }


    private int[][] createMap(int dim) {
        var map = new int[dim][dim];

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                map[i][j] = 0;
            }
        }

        return map;
    }

    private void fill(Mode mode) {
        switch (mode) {
            case RANDOM:
                fillRandom();
                break;
            case RECTANGLE:
                fillRectangle();
                break;
        }
    }

    private void fillRandom() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (Math.random() > 0.5)
                    map[i][j] = 1;
            }
        }
    }

    private void fillRectangle() {
        for (int i = dim / 4; i < 3 * dim / 4; i++) {
            for (int j = dim / 3; j < 2 * dim / 3; j++) {
                map[i][j] = 1;
            }
        }

        for (int i = dim / 4 + 1; i < 3 * dim / 4 - 1; i++) {
            for (int j = dim / 3 + 1; j < 2 * dim / 3 - 1; j++) {
                map[i][j] = 0;
            }
        }
    }

    private void checkNeighbours() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int counter = 0;

                int rowStart = Math.max(i - 1, GRID_START);
                int rowFinish = Math.min(i + 1, dim - 1);
                int colStart = Math.max(j - 1, GRID_START);
                int colFinish = Math.min(j + 1, dim - 1);

                for (int curRow = rowStart; curRow <= rowFinish; curRow++) {
                    for (int curCol = colStart; curCol <= colFinish; curCol++) {
                        if (map[curRow][curCol] == 1)
                            counter++;
                    }
                }

                if (map[i][j] == 0 && counter == 3)
                    map[i][j] = 1;
                else if (counter < 2 || counter > 3)
                    map[i][j] = 0;
            }
        }
    }

    public void abort() {
        this.running = false;
    }

    public void pause(boolean val) {
        this.paused = val;
    }

    public int[][] getMap() {
        return map;
    }
}
