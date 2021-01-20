package Tetriminos;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class TetriminosFactory {
    protected int width;
    protected int height;

    public TetriminosFactory(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public abstract Tetriminos generateTetriminos();

    public Tetriminos generateTetriminos(Tetriminos.Type type) {
        int x = width;
        switch (type) {
            case Mino_I:
            case Mino_O:
                x -= 4;
                break;

            default:
                x -= 3;
                break;
        }

        x /= 2;
            
        try {
            Class<?> _class = Class.forName("Tetriminos." + type.toString());
            Constructor<?> _constructor = _class.getConstructor(int[].class);
            Object object = _constructor.newInstance(new int[] {x, 0});
            
            return (Tetriminos) object;
        } catch (ClassNotFoundException |
                 IllegalAccessException |
                 java.lang.InstantiationException |
                 NoSuchMethodException |
                 InvocationTargetException e) {
            
            // Class not present
            e.printStackTrace();
            return new Mino_I(new int[] {x, 0});
        }
    }
}
