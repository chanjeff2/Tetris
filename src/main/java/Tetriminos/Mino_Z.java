package Tetriminos;

import java.util.HashMap;

public class Mino_Z extends Tetriminos {
    public Mino_Z(int[] position) {
        super(position);

        this.type = Type.Mino_Z;

        this.shape = new HashMap<Orientation, Byte[][]>();

        this.shape.put(
            Orientation.Up, 
            new Byte[][] 
            {
                {1, 1, 0},
                {0, 1, 1},
                {0, 0, 0}
            }
        );

        this.shape.put(
            Orientation.Right, 
            new Byte[][] 
            {
                {0, 0, 1},
                {0, 1, 1},
                {0, 1, 0}
            }
        );

        this.shape.put(
            Orientation.Down, 
            new Byte[][] 
            {
                {0, 0, 0},
                {1, 1, 0},
                {0, 1, 1}
            }
        );

        this.shape.put(
            Orientation.Left, 
            new Byte[][] 
            {
                {0, 1, 0},
                {1, 1, 0},
                {1, 0, 0}
            }
        );
    }

    public Mino_Z(Mino_Z target) {
        this(target.position.clone());
        this.orientation = target.orientation;
    }

    @Override
    public Mino_Z clone() {
        return new Mino_Z(this);
    }
}
