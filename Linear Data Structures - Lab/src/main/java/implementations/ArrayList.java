package implementations;

import interfaces.List;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayList<E> implements List<E> {

    private final static int INITIAL_CAPACITY = 4;
    private Object[] elements;
    private int size;
    private int capacity;

    public ArrayList() {
        this.elements = new Object[INITIAL_CAPACITY];
        this.size = 0;
        this.capacity = INITIAL_CAPACITY;
    }

    @Override
    public boolean add(E element) {
        ensureCapacity();

        this.elements[this.size++] = element;
        return true;
    }


    @Override
    public boolean add(int index, E element) {
        ensureCapacity();

        if (validateIndex(index)) {
            shiftRight(index);
            this.elements[index] = element;
            this.size++;
            return true;
        }

        return false;
    }

    @Override
    public E get(int index) {
        ensureIndex(index);

        return (E) this.elements[index];
    }


    @Override
    public E set(int index, E element) {
        ensureIndex(index);

        Object toReturn = this.elements[index];
        this.elements[index] = element;

        return (E) toReturn;
    }

    @Override
    public E remove(int index) {
        ensureIndex(index);

        E element = this.get(index);

        shiftLeft(index);

        this.size--;

        shrink();
        return (E) element;
    }



    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < this.size; i++) {
            if (this.elements[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(E element) {
        return this.indexOf(element) != -1;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return this.index < size();
            }

            @Override
            public E next() {
                return get(index++);
            }
        };
    }

    private void shrink() {
        if (this.size > this.capacity / 3) {
            return;
        }

        this.capacity /= 2;
        this.elements = Arrays.copyOf(this.elements, this.capacity);
    }

    private void shiftLeft(int index) {
        for (int i = index; i < this.size - 1; i++) {
            this.elements[i] = this.elements[i + 1];
        }
    }

    private void shiftRight(int index) {
        for (int i = this.size - 1; i >= index; i--) {
            this.elements[i + 1] = this.elements[i];
        }
    }

    private void ensureCapacity() {
        if (this.size == this.capacity) {
            grow();
        }
    }

    private void grow() {
        this.capacity *= 2;
        Object[] tmp = new Object[this.capacity];

        for (int i = 0; i < this.size; i++) {
            tmp[i] = this.elements[i];
        }

        this.elements = tmp;
    }

    private void ensureIndex(int index) {
        if (!validateIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
    }

    private boolean validateIndex(int index) {
        return index >= 0 && index < this.size;
    }

}
