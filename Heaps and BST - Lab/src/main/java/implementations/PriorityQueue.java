package implementations;

import interfaces.AbstractQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriorityQueue<E extends Comparable<E>> implements AbstractQueue<E> {

    private List<E> elements;

    public PriorityQueue() {
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
        ensureNonEmpty();
        return getAt(0);
    }

    @Override
    public E poll() {
        ensureNonEmpty();
        E result = this.getAt(0);
        Collections.swap(this.elements, 0, this.size() - 1);
        this.elements.remove(this.size() - 1);
        heapifyDown(0);
        return result;
    }

    private void heapifyUp(int index) {
        while (index > 0 && isLess(getParentIndex(index), index)) {
            Collections.swap(this.elements, index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }

    private void heapifyDown(int index) {
        while (this.getLeftChildIndex(index) < this.size() && isLess(index, this.getLeftChildIndex(index))) {
            int child = this.getLeftChildIndex(index);
            int rightChildIndex = this.getRightChildIndex(index);
            if (rightChildIndex < this.size() && isLess(child, rightChildIndex)) {
                child = rightChildIndex;
            }
            Collections.swap(this.elements, index, child);
            index = child;
        }
    }

    private boolean isLess(int first, int second) {
        return this.getAt(first).compareTo(getAt(second)) < 0;
    }

    private int getLeftChildIndex(int index) {
        return (index * 2) + 1;
    }

    private int getRightChildIndex(int index) {
        return (index * 2) + 2;
    }

    private E getAt(int index) {
        return this.elements.get(index);
    }

    private static int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    private void ensureNonEmpty() {
        if (this.size() == 0) {
            throw new IllegalStateException();
        }
    }
}
