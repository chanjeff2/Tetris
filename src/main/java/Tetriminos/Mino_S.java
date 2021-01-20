package Tetriminos;

import java.util.HashMap;

public class Mino_S extends Tetriminos {
    public Mino_S(int[] position) {
        super(position);

        this.type = Type.Mino_S;

        this.shape = new HashMap<Orientation, Byte[][]>();

        this.shape.put(
            Orientation.Up, 
            new Byte[][] 
            {
                {0, 1, 1}
                {1, 1, 0},
                {0, 0, 0}
            }
        );

        this.shape.put(
            Orientation.Right, 
            new Byte[][] 
            {
                {0, 1, 0}
                {0, 1, 1},
                {0, 0, 1}
            }
        );

        this.shape.put(
            Orientation.Down, 
            new Byte[][] 
            {
                {0, 0, 0}
                {0, 1, 1},
                {1, 1, 0}
            }
        );

        this.shape.put(
            Orientation.Left, 
            new Byte[][] 
            {
                {1, 0, 0}
                {1, 1, 0},
                {0, 1, 0}
            }
        );
    }
}
