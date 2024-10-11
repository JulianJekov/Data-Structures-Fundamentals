package implementations;

import interfaces.ReversedListInt;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReversedList<E> implements ReversedListInt<E> {
    private Object[] elements;
    private int size;
    private static final int INITIAL_CAPACITY = 2;

    public ReversedList() {
        this.elements = new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public void add(E element) {
        if (this.size == this.elements.length) {
            grow();
        }
        this.elements[this.size] = element;
        this.size++;
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int capacity() {
        return this.elements.length;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return this.getAt(this.size - 1 - index);
    }

    @Override
    public E removeAt(int index) {
        checkIndex(index);
        int realIndex = this.size - 1 - index;
        E removedElement = this.getAt(realIndex);
        for (int i = realIndex; i < this.size - 1; i++) {
            this.elements[i] = this.elements[i + 1];
        }
        this.size--;
        this.elements[this.size] = null;
        return removedElement;
    }

    @Override
    public Iterator<E> iterator() {
        return new ReverseIterator();
    }

    @SuppressWarnings("unchecked")
    private E getAt(int index) {
        return (E) this.elements[index];
    }

    private void grow() {
        Object[] newElements = new Object[this.elements.length * 2];
        System.arraycopy(this.elements, 0, newElements, 0, this.elements.length);
        this.elements = newElements;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    private class ReverseIterator implements Iterator<E> {
        private int currentIndex;

        public ReverseIterator() {
            this.currentIndex = size - 1;
        }

        @Override
        public boolean hasNext() {
            return this.currentIndex >= 0;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return getAt(this.currentIndex--);
        }
    }
}
