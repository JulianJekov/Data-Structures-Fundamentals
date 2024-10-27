package solutions;

import interfaces.Decrease;
import interfaces.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinHeap<E extends Comparable<E> & Decrease<E>> implements Heap<E> {

    private List<E> elements;
    public MinHeap() {
        this.elements = new ArrayList<>();
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public void add(E element) {
        this.elements.add(element);
        this.heapifyUp(this.elements.size() - 1);
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.elements.get(0);
    }

    @Override
    public E poll() {
        ensureNonEmpty();
        E element = this.elements.get(0);
        Collections.swap(this.elements, 0, getLastElement());
        this.elements.remove(getLastElement());
        this.heapifyDown();
        return element;
    }

    @Override
    public void decrease(E element) {
        int elementIndex = this.elements.indexOf(element);
        E heapElement = this.elements.get(elementIndex);
        heapElement.decrease();
        this.heapifyUp(elementIndex);
    }

    private void heapifyUp(int index) {
        int parentIndex = getParentIndex(index);
        while (index > 0 && isLess(index, parentIndex)) {
            Collections.swap(this.elements, index, parentIndex);
            index = parentIndex;
            parentIndex = getParentIndex(index);
        }
    }

    private void heapifyDown() {
        int index = 0;
        int swapIndex = getLeftChildIndex(index);
        while (swapIndex < this.elements.size()) {
            int rightChildIndex = getRightChildIndex(index);
            if (rightChildIndex < this.elements.size()) {
                swapIndex = isLess(rightChildIndex, swapIndex) ? rightChildIndex : swapIndex;
            }
            if (isLess(index, swapIndex)) {
                break;
            }
            Collections.swap(this.elements, index, swapIndex);
            index = swapIndex;
            swapIndex = getLeftChildIndex(index);
        }
    }

    private void ensureNonEmpty() {
        if (this.elements.isEmpty()) {
            throw new IllegalStateException();
        }
    }

    private boolean isLess(int index, int parentIndex) {
        return this.elements.get(index).compareTo(elements.get(parentIndex)) < 0;
    }

    private static int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }
    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }

    private int getLastElement() {
        return this.elements.size() - 1;
    }
}
