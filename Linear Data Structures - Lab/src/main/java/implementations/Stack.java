package implementations;

import interfaces.AbstractStack;

import java.util.Iterator;

public class Stack<E> implements AbstractStack<E> {

    private Node<E> top;
    private int size;

    private static class Node<E> {
        private Node<E> prev;
        private final E value;

        public Node(E value) {
            this.value = value;
        }
    }

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    @Override
    public void push(E element) {
        Node<E> toAdd = new Node<>(element);
        toAdd.prev = this.top;
        this.top = toAdd;
        this.size++;
    }

    @Override
    public E pop() {
        ensureNonEmpty();
        Node<E> tmp = this.top;
        this.top = tmp.prev;
        this.size--;
        return tmp.value;
    }


    @Override
    public E peek() {
        ensureNonEmpty();
        return this.top.value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = top;

            @Override
            public boolean hasNext() {
                return this.current != null;
            }

            @Override
            public E next() {
                E value = this.current.value;
                this.current = this.current.prev;
                return value;
            }
        };
    }

    private void ensureNonEmpty() {
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
    }

}
