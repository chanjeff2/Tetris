import java.util.Timer;
import Tetriminos.*;

public class Playfield {
    private final int WIDTH = 10;
    private final int HEIGHT = 22;
    private final int REFRESH_INTERVAL = 300;

    Tetriminos stored;
    Tetriminos current;

    Timer timer;

    byte[][] grid = new byte[HEIGHT][WIDTH];

    class PaintCommand {
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
                    if (position[1] + row >= HEIGHT || position[0] + col >= WIDTH) {
                        continue;
                    }
                    grid[position[1] + row][position[0] + col] += shape[row][col];
                }
            }
        }

        public void clear() {
            for (int row = 0; row < shape.length; ++row) {
                for (int col = 0; col < shape[0].length; ++col) {
                    if (position[1] + row >= HEIGHT || position[0] + col >= WIDTH) {
                        continue;
                    }
                    grid[position[1] + row][position[0] + col] -= shape[row][col];
                }
            }
        }
    }

    PaintCommand lastPaint;

    public Playfield() {
        for (int row = 0; row < HEIGHT; ++row) {
            for (int col = 0; col < WIDTH; ++col) {
                grid[row][col] = 0;
            }
        }
    }

    void repaint() {
        Byte[][] shape = current.getShape();
        int[] position = current.getPosition();

        if (lastPaint != null) {
            lastPaint.clear();
        }

        lastPaint = new PaintCommand(shape, position);

        displayGrid();
    }

    public void start() {
        current = new Mino_I(new int[] {3, 0});

        repaint();

        for (int i = 0; i < 500; ++i) {
            try {
                Thread.sleep(REFRESH_INTERVAL);
                softDrop();
                repaint();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    void displayGrid() {
        System.out.print("\033[?25l");
        System.out.print("\033[H\033[2J");

        System.out.println();
        System.out.println();
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

    Boolean haveCollision(Callback callback) {
        Boolean collision = false;

        Tetriminos currentHook = this.current;
        this.current = currentHook.clone();

        callback.run(this.current);

        Byte[][] shape = current.getShape();
        int[] position = current.getPosition();

        lastPaint.clear();

        PaintCommand command = new PaintCommand(shape, position);

        for (int row = 0; row < shape.length; ++row) {
            for (int col = 0; col < shape[0].length; ++col) {
                if (position[1] + row >= HEIGHT || position[0] + col >= WIDTH) {
                    if (shape[row][col] == 1) {
                        command.clear();
                        current = currentHook;
                        return true;
                    }
                } else {
                    if (grid[position[1] + row][position[0] + col] > 1) {
                        command.clear();
                        current = currentHook;
                        return true;
                    }
                }
            }
        }

        command.clear();
        lastPaint.paint();
        this.current = currentHook;
        return collision;
    }

    void rotateClockwise() {
        if (haveCollision( tetriminos -> {
            tetriminos.rotateClockwise();
        })) {
            return;
        }

        current.rotateClockwise();
    }

    void rotateAntiClockwise() {
        if (haveCollision( tetriminos -> {
            tetriminos.rotateAntiClockwise();
        })) {
            return;
        }

        current.rotateAntiClockwise();
    }

    void softDrop() {
        if (haveCollision( tetriminos -> {
            tetriminos.softDrop();
        })) {
            place();
            return;
        }

        current.softDrop();
    }

    void hardDrop() {
        while (!haveCollision( tetriminos -> {
            tetriminos.softDrop();
        })) {
            current.softDrop();
        }

        place();
    }

    void place() {
        Byte[][] shape = current.getShape();
        int[] position = current.getPosition();

        for (int row = 0; row < shape.length; ++row) {
            for (int col = 0; col < shape[0].length; ++col) {
                if (position[1] + row >= HEIGHT || position[0] + col >= WIDTH) {
                    continue;
                }
                grid[position[1] + row][position[0] + col] += shape[row][col];
            }
        }

        current = new Mino_I(new int[] {3, 0});
        lastPaint = new PaintCommand(current.getShape(), current.getPosition());
        displayGrid();
    }

    interface Callback {
        public void run(Tetriminos tetriminos);
    }
}
