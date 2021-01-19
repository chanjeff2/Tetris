package Tetriminos;

abstract class Tetriminos {
    public enum Shape {
        I, J, L, O, S, T, Z
    }

    Shape shape;

    public enum Orientation {
        Up, Right, Down, Left
    }

    Orientation orientation;

    public abstract void rotateClockwise();
    public abstract void rotateAntiClockwise();

    public abstract void moveLeft();
    public abstract void moveRight();

    public abstract void softDrop();
    public abstract void hardDrop();

    public abstract void place();
}