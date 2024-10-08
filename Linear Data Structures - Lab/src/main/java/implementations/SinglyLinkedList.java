package implementations;

import interfaces.LinkedList;

import java.util.Iterator;

public class SinglyLinkedList<E> implements LinkedList<E> {

    private Node<E> head;
    private int size;

    private static class Node<E> {
        private Node<E> next;
        private E value;

        public Node(E value) {
            this.value = value;
        }
    }

    public SinglyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public void addFirst(E element) {
        Node<E> toAdd = new Node<>(element);
        toAdd.next = this.head;
        this.head = toAdd;
        this.size++;
    }

    @Override
    public void addLast(E element) {
        Node<E> toAdd = new Node<>(element);
        if (this.isEmpty()) {
            this.head = toAdd;
        } else {
            Node<E> current = this.head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = toAdd;
        }
        this.size++;
    }

    @Override
    public E removeFirst() {
        ensureNonEmpty();
        Node<E> toRemove = this.head;
        this.head = toRemove.next;
        this.size--;
        return toRemove.value;
    }


    @Override
    public E removeLast() {
        ensureNonEmpty();
        E value;
        if (this.size == 1) {
            value = this.head.value;
            this.head = null;
        } else {
            Node<E> preLast = this.head;
            Node<E> toRemove = this.head.next;
            while (toRemove.next != null) {
                preLast = toRemove;
                toRemove = toRemove.next;
            }
            value = toRemove.value;
            preLast.next = null;
        }
        this.size--;
        return value;
    }

    @Override
    public E getFirst() {
        ensureNonEmpty();
        return this.head.value;
    }

    @Override
    public E getLast() {
        ensureNonEmpty();
        Node<E> current = this.head;
        while (current.next != null) {
            current = current.next;
        }

        return current.value;
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
            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E value = this.current.value;
                this.current = current.next;
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
