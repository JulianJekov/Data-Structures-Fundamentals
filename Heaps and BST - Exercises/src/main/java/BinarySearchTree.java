import solutions.BinaryTree;

import java.util.*;
import java.util.function.Consumer;

public class BinarySearchTree<E extends Comparable<E>> {
    private Node<E> root;

    public static class Node<E> {
        private E value;
        private Node<E> leftChild;
        private Node<E> rightChild;
        private int count;

        public Node(E value) {
            this.value = value;
            this.count = 1;
        }

        public Node(Node<E> other) {
            this.value = other.value;
            this.count = other.count;
            if (other.getLeft() != null) {
                this.leftChild = new Node<>(other.getLeft());
            }

            if (other.getRight() != null) {
                this.rightChild = new Node<>(other.getRight());
            }
        }

        public Node<E> getLeft() {
            return this.leftChild;
        }

        public Node<E> getRight() {
            return this.rightChild;
        }

        public E getValue() {
            return this.value;
        }

        public int getCount() {
            return this.count;
        }
    }

    public BinarySearchTree() {
    }

    public BinarySearchTree(Node<E> otherRoot) {
        this.root = new Node<>(otherRoot);
    }

    public BinarySearchTree(E element) {
        this.root = new Node<>(element);
    }

    public void eachInOrder(Consumer<E> consumer) {
        nodeInOrder(this.root, consumer);
    }

    private void nodeInOrder(Node<E> node, Consumer<E> consumer) {
        if (node == null) {
            return;
        }

        nodeInOrder(node.getLeft(), consumer);
        consumer.accept(node.getValue());
        nodeInOrder(node.getRight(), consumer);
    }

    public Node<E> getRoot() {
        return this.root;
    }

    public void insert(E element) {
        if (this.root == null) {
            this.root = new Node<>(element);
        } else {
            insertInto(this.root, element);
        }
    }

    private void insertInto(Node<E> node, E element) {
        if (isGreater(element, node)) {
            if (node.getRight() == null) {
                node.rightChild = new Node<>(element);
            } else {
                insertInto(node.getRight(), element);
            }
        } else if (isLess(element, node)) {
            if (node.getLeft() == null) {
                node.leftChild = new Node<>(element);
            } else {
                insertInto(node.getLeft(), element);
            }
        }
        node.count++;
    }

    public boolean contains(E element) {
        //return containsWithIterations(this.root, element);
        return containsWithRecursion(this.root, element) != null;
    }

    private Node<E> containsWithRecursion(Node<E> node, E element) {
        if (node == null) {
            return null;
        }
        if (areEquals(element, node)) {
            return node;
        } else if (isGreater(element, node)) {
            return containsWithRecursion(node.getRight(), element);
        }
        return containsWithRecursion(node.getLeft(), element);
    }

    private boolean containsWithIterations(Node<E> node, E element) {
        while (node != null) {
            if (areEquals(element, node)) {
                return true;
            }
            if (isGreater(element, node)) {
                node = node.getRight();
            } else if (isLess(element, node)) {
                node = node.getLeft();
            }
        }
        return false;
    }

    public BinarySearchTree<E> search(E element) {
        Node<E> search = containsWithRecursion(this.root, element);
        return search == null ? null : new BinarySearchTree<>(search);
    }

    public List<E> range(E lower, E upper) {
        List<E> result = new ArrayList<>();
        if (this.root == null) {
            return result;
        }
        Deque<Node<E>> queue = new ArrayDeque<>();
        queue.offer(this.root);
        while (!queue.isEmpty()) {
            Node<E> current = queue.poll();
            if (current.getLeft() != null) {
                queue.offer(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.offer(current.getRight());
            }

            if (isLess(lower, current) && isGreater(upper, current)) {
                result.add(current.getValue());
            } else if (areEquals(lower, current) || areEquals(upper, current)) {
                result.add(current.getValue());
            }
        }
        return result;
    }

    public void deleteMin() {
        ensureNonEmpty();
        if (this.root.getLeft() == null) {
            this.root = this.root.getRight();
            return;
        }
        Node<E> current = this.root;
        while (current.getLeft().getLeft() != null) {
            current = current.getLeft();
        }
        current.leftChild = current.getLeft().getRight();
        this.root.count--;
    }

    private void ensureNonEmpty() {
        if (this.root == null) {
            throw new IllegalArgumentException("Tree is empty");
        }
    }

    public void deleteMax() {
        ensureNonEmpty();
        if (this.root.getRight() == null) {
            this.root = this.root.getLeft();
            return;
        }
        Node<E> current = this.root;
        while (current.getRight().getRight() != null) {
            current = current.getRight();
        }
        current.rightChild = current.getRight().getLeft();
        this.root.count--;
    }

    public int count() {
        return this.root == null ? 0 : this.root.count;
    }

    public int rank(E element) {
        return nodeRank(this.root, element);
    }

    private int nodeRank(Node<E> node, E element) {
        if (node == null) {
            return 0;
        }
        if (isLess(element, node)) {
            return nodeRank(node.getLeft(), element);
        } else if (areEquals(element, node)) {
            return getNodeCount(node.getLeft());
        }

        return getNodeCount(node.getLeft()) + 1 + nodeRank(node.getRight(), element);
    }

    private int getNodeCount(Node<E> node) {
        return node == null ? 0 : node.getCount();
    }

    public E ceil(E element) {
        if (this.root == null) {
            return null;
        }
        Node<E> current = this.root;
        Node<E> parent = null;
        while (current != null) {
            if (isGreater(element, current)) {
                current = current.getRight();
            } else if (isLess(element, current)) {
                parent = current;
                current = current.getLeft();
            }else {
                Node<E> right = current.getRight();
                if (right != null && parent != null) {
                    parent = isLess(right.value, parent) ? right : parent;
                } else if (parent == null) {
                    parent = right;
                }
                break;
            }

        }
        return parent == null ? null : parent.value;
    }

    public E floor(E element) {
        if (this.root == null) {
            return null;
        }
        Node<E> current = this.root;
        Node<E> parent = null;
        while (current != null) {
            if (isGreater(element, current)) {
                parent = current;
                current = current.getRight();
            } else if (isLess(element, current)) {
                current = current.getLeft();
            } else {
                Node<E> left = current.getLeft();
                if (left != null && parent != null) {
                     parent = isGreater(left.value, parent) ? left : parent;
                } else if (parent == null) {
                    parent = left;
                }
                break;
            }
        }
        return parent == null ? null : parent.value;
    }

    private boolean isLess(E first, Node<E> second) {
        return first.compareTo(second.getValue()) < 0;
    }

    private boolean isGreater(E first, Node<E> second) {
        return first.compareTo(second.getValue()) > 0;
    }

    private boolean areEquals(E first, Node<E> second) {
        return first.compareTo(second.getValue()) == 0;
    }
}
