package Tetriminos;

import java.util.HashMap;

public class Mino_J extends Tetriminos {
    public Mino_J(int[] position) {
        super(position);

        this.type = Type.Mino_J;

        this.shape = new HashMap<Orientation, Byte[][]>();

        this.shape.put(
            Orientation.Up, 
            new Byte[][] 
            {
                {1, 0, 0},
                {1, 1, 1},
                {0, 0, 0}
            }
        );

        this.shape.put(
            Orientation.Right, 
            new Byte[][] 
            {
                {0, 1, 1},
                {0, 1, 0},
                {0, 1, 0}
            }
        );

        this.shape.put(
            Orientation.Down, 
            new Byte[][] 
            {
                {0, 0, 0},
                {1, 1, 1},
                {0, 0, 1}
            }
        );

        this.shape.put(
            Orientation.Left, 
            new Byte[][] 
            {
                {0, 1, 0},
                {0, 1, 0},
                {1, 1, 0}
            }
        );
    }

    public Mino_J(Mino_J target) {
        this(target.position.clone());
        this.orientation = target.orientation;
    }

    @Override
    public Mino_J clone() {
        return new Mino_J(this);
    }
}
