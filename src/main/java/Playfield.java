import java.util.Timer;
import Tetriminos.*;

public class Playfield {
    private final int WIDTH = 10;
    private final int HEIGHT = 22;
    private final int REFRESH_INTERVAL = 500;

    Tetriminos stored;
    Tetriminos current;

    Timer timer;

    byte[][] grid = new byte[HEIGHT][WIDTH];

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

        for (int row = 0; row < HEIGHT; ++row) {
            for (int col = 0; col < WIDTH; ++col) {
                if (grid[row][col] == 2) {
                    grid[row][col] = 0;
                }
            }
        }

        for (int row = 0; row < shape.length; ++row) {
            for (int col = 0; col < shape[0].length; ++col) {
                if (position[1] + row >= HEIGHT || position[0] + col >= WIDTH) {
                    continue;
                }
                grid[position[1] + row][position[0] + col] += shape[row][col] * 2;
            }
        }
        displayGrid();
    }

    public void start() {
        current = new Mino_I(new int[] {3, 0});

        repaint();

        for (int i = 0; i < 22; ++i) {
            try {
                Thread.sleep(REFRESH_INTERVAL);
                current.softDrop();
                repaint();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    void displayGrid() {
        System.out.print("\033[?25l");
        System.out.print("\033[H\033[2J");

        for (int i = 0; i < WIDTH + 2; ++i) {
            System.out.print("口");
        }

        System.out.println();

        for (int row = 0; row < HEIGHT; ++row) {
            System.out.print("口");
            for (int col = 0; col < WIDTH; ++col) {
                if (grid[row][col] == 0) {
                    System.out.print("　");
                } else {
                    System.out.print("口");
                }
                
            }
            System.out.println("口");
        }

        for (int i = 0; i < WIDTH + 2; ++i) {
            System.out.print("口");
        }

        System.out.println();
    }
}
