import Tetriminos.*;

public class Playfield {
    private final int WIDTH = 10;
    private final int HEIGHT = 22;
    private final int REFRESH_INTERVAL = 300;

    private Tetriminos.Type stored;
    private Boolean isStoreTriggered = false;
    private Tetriminos current;
    private Tetriminos next;

    private TetriminosFactory tetriminosFactory = new PsudoRandomTetriminosFactory(WIDTH, HEIGHT);

    private byte[][] grid = new byte[HEIGHT][WIDTH];

    private Boolean isGameOver = false;

    private class PaintCommand {
        Byte[][] shape;
        int[] position;

        public PaintCommand(Byte[][] shape, int[] position) {
            this.shape = shape.clone();
            this.position = position.clone();

            paint();
        }

        public void paint() {
            for (int row = 0; row < shape.length; ++row) {
                for (int col = 0; col < shape[0].length; ++col) {
                    if (position[1] + row < 0 || 
                        position[1] + row >= HEIGHT || 
                        position[0] + col < 0 || 
                        position[0] + col >= WIDTH) {
                        continue;
                    }
                    grid[position[1] + row][position[0] + col] += shape[row][col];
                }
            }
        }

        public void clear() {
            for (int row = 0; row < shape.length; ++row) {
                for (int col = 0; col < shape[0].length; ++col) {
                    if (position[1] + row < 0 || 
                        position[1] + row >= HEIGHT || 
                        position[0] + col < 0 || 
                        position[0] + col >= WIDTH) {
                        continue;
                    }
                    grid[position[1] + row][position[0] + col] -= shape[row][col];
                }
            }
        }
    }

    private PaintCommand lastPaint;

    public Playfield() {
        for (int row = 0; row < HEIGHT; ++row) {
            for (int col = 0; col < WIDTH; ++col) {
                grid[row][col] = 0;
            }
        }
    }

    private void repaint() {
        Byte[][] shape = current.getShape();
        int[] position = current.getPosition();

        if (lastPaint != null) {
            lastPaint.clear();
        }

        lastPaint = new PaintCommand(shape, position);

        displayGrid();
    }

    public void start() {
        current = tetriminosFactory.generateTetriminos();
        next = tetriminosFactory.generateTetriminos();

        repaint();

        int counter = 0;

        while (!isGameOver) {
            try {
                Thread.sleep(REFRESH_INTERVAL);
                moveRight();
                softDrop();
                repaint();
                ++counter;
                if (counter % 25 == 10) {
                    store();
                }
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }

    private void displayGrid() {
        System.out.print("\033[?25l");
        System.out.print("\033[H\033[2J");

        System.out.println();
        System.out.println();

        System.out.print("\t Stored: \t Next: \n\n");

        if (stored != null) {
            Tetriminos temp_stored = tetriminosFactory.generateTetriminos(stored);
            Byte[][] stored_shape = temp_stored.getShape();
            Byte[][] next_shape = next.getShape();

            for (int i = 0; i < 4; ++i) {
                System.out.print("\t ");

                // last row
                if (i == 3 && stored_shape.length < 4) {
                    System.out.print("\t\t\t");
                } else {
                    for (int j = 0; j < 4; ++j) {
                        // last col
                        if (j == 3 && stored_shape[0].length < 4) {
                            System.out.print("  ");
                            break;
                        }
    
                        if (stored_shape[i][j] == 0) {
                            System.out.print("  ");
                        } else {
                            System.out.print("O ");
                        }
                    }
                }

                System.out.print("\t ");

                if (i == 3 && next_shape.length < 4) {
                    System.out.println();
                    break;
                }

                for (int j = 0; j < 4; ++j) {
                    // last col
                    if (j == 3 && next_shape[0].length < 4) {
                        System.out.print("  ");
                        break;
                    }

                    if (next_shape[i][j] == 0) {
                        System.out.print("  ");
                    } else {
                        System.out.print("O ");
                    }
                }

                System.out.println();
            }
        } else {
            Byte[][] shape = next.getShape();

            for (int i = 0; i < 4; ++i) {
                System.out.print("\t\t\t");

                // last row
                if (i == 3 && shape.length < 4) {
                    System.out.println();
                    break;
                }

                for (int j = 0; j < 4; ++j) {
                    // last col
                    if (j == 3 && shape[0].length < 4) {
                        System.out.print("  ");
                        break;
                    }

                    if (shape[i][j] == 0) {
                        System.out.print("  ");
                    } else {
                        System.out.print("O ");
                    }
                }

                System.out.println();
            }
        }

        System.out.print("\t");

        for (int i = 0; i < WIDTH + 2; ++i) {
            System.out.print("O ");
        }

        System.out.println();

        for (int row = 0; row < HEIGHT; ++row) {
            System.out.print("\tO ");
            for (int col = 0; col < WIDTH; ++col) {
                if (grid[row][col] == 0) {
                    System.out.print("  ");
                } else {
                    System.out.print("O ");
                }
            }
            System.out.println("O ");
        }

        System.out.print("\t");

        for (int i = 0; i < WIDTH + 2; ++i) {
            System.out.print("O ");
        }

        System.out.println();
    }

    private Boolean haveCollision(Callback callback) {
        Boolean collision = false;

        Tetriminos currentHook = this.current;
        this.current = currentHook.clone();

        callback.run(this.current);

        Byte[][] shape = current.getShape();
        int[] position = current.getPosition();

        lastPaint.clear();

        PaintCommand command = new PaintCommand(shape, position);

        for (int row = 0; row < shape.length; ++row) {
            if (collision) {
                break;
            }
            for (int col = 0; col < shape[0].length; ++col) {
                if (position[1] + row < 0 || 
                    position[1] + row >= HEIGHT || 
                    position[0] + col < 0 || 
                    position[0] + col >= WIDTH) {
                    // overflow
                    if (shape[row][col] == 1) {
                        collision = true;
                        break;
                    }
                } else {
                    // overlap
                    if (grid[position[1] + row][position[0] + col] > 1) {
                        collision = true;
                        break;
                    }
                }
            }
        }

        command.clear();
        lastPaint.paint();
        this.current = currentHook;
        return collision;
    }

    private void moveLeft() {
        if (haveCollision( tetriminos -> {
            tetriminos.moveLeft();
        })) {
            return;
        }

        current.moveLeft();
    }

    private void moveRight() {
        if (haveCollision( tetriminos -> {
            tetriminos.moveRight();
        })) {
            return;
        }

        current.moveRight();
    }

    private void rotateClockwise() {
        if (haveCollision( tetriminos -> {
            tetriminos.rotateClockwise();
        })) {
            return;
        }

        current.rotateClockwise();
    }

    private void rotateAntiClockwise() {
        if (haveCollision( tetriminos -> {
            tetriminos.rotateAntiClockwise();
        })) {
            return;
        }

        current.rotateAntiClockwise();
    }

    private void softDrop() {
        if (haveCollision( tetriminos -> {
            tetriminos.softDrop();
        })) {
            place();
            return;
        }

        current.softDrop();
    }

    private void hardDrop() {
        while (!haveCollision( tetriminos -> {
            tetriminos.softDrop();
        })) {
            current.softDrop();
        }

        place();
    }

    private void place() {
        isStoreTriggered = false;
        
        current = next;
        next = tetriminosFactory.generateTetriminos();
        lastPaint = new PaintCommand(current.getShape(), current.getPosition());

        if (haveCollision( tetriminos -> {

        })) {
            isGameOver = true;
        }

        displayGrid();
    }

    private void store() {
        if (isStoreTriggered) {
            return;
        }

        isStoreTriggered = true;
        
        if (stored == null) {
            stored = current.getType();
            current = next;
            next = tetriminosFactory.generateTetriminos();
        } else {
            Tetriminos.Type storedType = stored;
            stored = current.getType();
            current = tetriminosFactory.generateTetriminos(storedType);
        }
    }

    interface Callback {
        public void run(Tetriminos tetriminos);
    }
}
