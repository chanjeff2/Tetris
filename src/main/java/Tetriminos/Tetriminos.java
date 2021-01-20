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
        Mino_Z
    }

    protected Type type;

    public enum Orientation {
        Up(0), 
        Right(1), 
        Down(2), 
        Left(3);

        int mIndex;

        Orientation(int i) {
            mIndex = i;
        }

        int index() {
            return mIndex;
        }

        static Orientation getOrientation(int i) {
            switch (i) {
                case 0:
                    return Up;
                case 1:
                    return Right;
                case 2:
                    return Down;
                case 3:
                    return Left;
                default:
                    return Up;
            }
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
        int o = orientation.index();
        o = (o + 1) % 4;
        this.orientation = Orientation.getOrientation(o);
    }

    public void rotateAntiClockwise() {
        int o = orientation.index();
        o = (o - 1) % 4;
        if (o < 0) {
            o += 4;
        }
        this.orientation = Orientation.getOrientation(o);
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
    public void hardDrop(int y) {
        position[1] = y;
    }

    public abstract Tetriminos clone();
}
