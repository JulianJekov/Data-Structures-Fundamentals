package implementations;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void testArrayDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(13);
        deque.add(14);
        System.out.println(deque.remove(1));
    }

}