package Tetriminos;

import java.util.HashMap;

public class Mino_I extends Tetriminos {
    public Mino_I(int[] position) {
        super(position);

        this.type = Type.Mino_I;

        this.shape = new HashMap<Orientation, Byte[][]>();

        this.shape.put(
            Orientation.Up, 
            new Byte[][] 
            {
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
            }
        );

        this.shape.put(
            Orientation.Right, 
            new Byte[][] 
            {
                {0, 0, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 1, 0},
                {0, 0, 1, 0}
            }
        );

        this.shape.put(
            Orientation.Down, 
            new Byte[][] 
            {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0}
            }
        );

        this.shape.put(
            Orientation.Left, 
            new Byte[][] 
            {
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
            }
        );
    }

    public Mino_I(Mino_I target) {
        this(target.position.clone());
        this.orientation = target.orientation;
    }

    @Override
    public Mino_I clone() {
        return new Mino_I(this);
    }
}
