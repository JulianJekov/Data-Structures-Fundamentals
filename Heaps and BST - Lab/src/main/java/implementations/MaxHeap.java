package implementations;

import interfaces.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaxHeap<E extends Comparable<E>> implements Heap<E> {

    private List<E> elements;

    public MaxHeap() {
        this.elements = new ArrayList<>();
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public void add(E element) {
        this.elements.add(element);
        this.heapifyUp(this.size() - 1);
    }

    @Override
    public E peek() {
        if (this.size() == 0) {
            throw new IllegalStateException();
        }
        return getAt(0);
    }

    private void heapifyUp(int index) {
        while (index > 0 && isLess(getParentIndex(index), index)) {
            Collections.swap(this.elements, index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }

    private boolean isLess(int parentIndex, int childIndex) {
        return this.getAt(parentIndex).compareTo(getAt(childIndex)) < 0;
    }

    private E getAt(int index) {
        return this.elements.get(index);
    }

    private static int getParentIndex(int index) {
        return (index - 1) / 2;
    }


}
