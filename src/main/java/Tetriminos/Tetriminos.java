package Tetriminos;

import java.util.HashMap;

public abstract class Tetriminos {

    public enum Type {
        Mino_I, 
        Mino_J, 
        Mino_L, 
        Mino_O, 
        Mino_S, 
        Mino_T, 
        Mino_Z;

        public static Type[] vals = values();
    }

    protected Type type;

    public enum Orientation {
        Up,
        Right,
        Down,
        Left;

        private static Orientation[] vals = values();

        Orientation next() {
            return vals[(this.ordinal() + 1) % vals.length];
        }

        Orientation prev() {
            return vals[(this.ordinal() + -1 + vals.length) % vals.length];
        }
    }

    protected Orientation orientation;

    protected int[] position = new int[2]; // {x, y}

    protected HashMap<Orientation, Byte[][]> shape;

    public Tetriminos(int[] position) {
        this.position = position;
        this.orientation = Orientation.Up;
    }

    public Byte[][] getShape() {
        return shape.get(orientation);
    }

    public int[] getPosition() {
        return position;
    }

    public void rotateClockwise() {
        this.orientation = this.orientation.next();
    }

    public void rotateAntiClockwise() {
        this.orientation = this.orientation.prev();
    }

    public void moveLeft() {
        --position[0];
    }
    public void moveRight() {
        ++position[0];
    }

    public void softDrop() {
        ++position[1];
    }

    public abstract Tetriminos clone();
}
