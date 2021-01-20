package Tetriminos;

import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Collections;
import java.util.List;

public class PsudoRandomTetriminosFactory extends TetriminosFactory {
    Queue<Tetriminos> queue = new LinkedList<Tetriminos>();

    public PsudoRandomTetriminosFactory(int width, int height) {
        super(width, height);
    }

    @Override
    public Tetriminos generateTetriminos() {
        if (!queue.isEmpty()) {
            return queue.poll();
        }

        List<Integer> temp = IntStream.range(0, Tetriminos.Type.values().length).boxed().collect(Collectors.toList());
        Collections.shuffle(temp);
        
        for (int i : temp) {
            Tetriminos.Type type = Tetriminos.Type.vals[i];

            Tetriminos tetriminos = generateTetriminos(type);

            queue.add(tetriminos);
        }

        return queue.poll();
    }
}
