package Tetriminos;

import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Collections;
import java.util.List;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PsudoRandomTetriminosFactory extends TetriminosFactory {
    Queue<Tetriminos> queue = new LinkedList<Tetriminos>();

    @Override
    public Tetriminos generateTetriminos(int width) {
        if (!queue.isEmpty()) {
            return queue.poll();
        }

        List<Integer> temp = IntStream.range(0, Tetriminos.Type.values().length).boxed().collect(Collectors.toList());
        Collections.shuffle(temp);
        
        for (int i : temp) {
            Tetriminos.Type type = Tetriminos.Type.vals[i];
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
                queue.add((Tetriminos) object);
            } catch (ClassNotFoundException e) {
                // Class not present
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // no constructor
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // can't create new instance
                e.printStackTrace();
            }
        }

        return queue.poll();
    }
}
