package implementations;

import interfaces.AbstractBinarySearchTree;

public class BinarySearchTree<E extends Comparable<E>> implements AbstractBinarySearchTree<E> {
    private Node<E> root;
    private Node<E> leftChild;
    private Node<E> rightChild;

    public BinarySearchTree() {
    }

    public BinarySearchTree(Node<E> root) {
        this.copy(root);
    }

    private void copy(Node<E> root) {
        if (root != null) {
            this.insert(root.value);
            this.copy(root.leftChild);
            this.copy(root.rightChild);
        }
    }

    @Override
    public void insert(E element) {
        Node<E> newNode = new Node<>(element);
        if (this.getRoot() == null) {
            this.root = newNode;
        } else {
            Node<E> current = this.root;
            Node<E> prev = current;
            while (current != null) {
                prev = current;
                if (isLess(element, current.value)) {
                    current = current.leftChild;
                } else if (isGreater(element, current.value)) {
                    current = current.rightChild;
                } else {
                    return;
                }
            }

            if (isLess(element, prev.value)) {
                prev.leftChild = newNode;
            } else if (isGreater(element, prev.value)) {
                prev.rightChild = newNode;
            }
        }
    }

    private boolean isLess(E first, E second) {
        return first.compareTo(second) < 0;
    }

    private boolean isGreater(E first, E second) {
        return first.compareTo(second) > 0;
    }


    @Override
    public boolean contains(E element) {
        Node<E> current = this.root;
        while (current != null) {
            if (isLess(element, current.value)) {
                current = current.leftChild;
            } else if (isGreater(element, current.value)) {
                current = current.rightChild;
            } else
                return true;
        }
        return false;
    }

    @Override
    public AbstractBinarySearchTree<E> search(E element) {
        AbstractBinarySearchTree<E> result = new BinarySearchTree<>();
        Node<E> current = this.root;
        while (current != null) {
            if (isLess(element, current.value)) {
                current = current.leftChild;
            } else if (isGreater(element, current.value)) {
                current = current.rightChild;
            } else
                return new BinarySearchTree<>(current);
        }

        return result;
    }

    @Override
    public Node<E> getRoot() {
        return this.root;
    }

    @Override
    public Node<E> getLeft() {
        return this.leftChild;
    }

    @Override
    public Node<E> getRight() {
        return this.rightChild;
    }

    @Override
    public E getValue() {
        return this.root.value;
    }
}
