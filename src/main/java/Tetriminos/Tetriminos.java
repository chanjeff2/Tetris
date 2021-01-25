package Tetriminos;

import java.util.HashMap;

public abstract class Tetriminos {

    public enum Type {
        Mino_I(4), 
        Mino_J(3), 
        Mino_L(3), 
        Mino_O(4), 
        Mino_S(3), 
        Mino_T(3), 
        Mino_Z(3);

        private int width = 0;
        
        Type(int width) {
            this.width = width;
        }

        public int getWidth() {
            return width;
        }

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

    public Type getType() {
        return type;
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
