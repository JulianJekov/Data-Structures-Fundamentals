package implementations;

import interfaces.AbstractBinaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BinaryTree<E> implements AbstractBinaryTree<E> {

    private E key;
    private BinaryTree<E> leftChild;
    private BinaryTree<E> rightChild;

    public BinaryTree(E key, BinaryTree<E> leftChild, BinaryTree<E> rightChild) {
        this.key = key;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public AbstractBinaryTree<E> getLeft() {
        return this.leftChild;
    }

    @Override
    public AbstractBinaryTree<E> getRight() {
        return this.rightChild;
    }

    @Override
    public void setKey(E key) {
        this.key = key;
    }

    @Override
    public String asIndentedPreOrder(int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(createPadding(indent))
                .append(this.getKey());
        if (this.getLeft() != null) {
            String indentedPreOrder = this.getLeft().asIndentedPreOrder(indent + 2);
            sb.append(System.lineSeparator()).append(indentedPreOrder);
        }
        if (this.getRight() != null) {
            String indentedPreOrder = this.getRight().asIndentedPreOrder(indent + 2);
            sb.append(System.lineSeparator());sb.append(indentedPreOrder);
        }
        return sb.toString();
    }

    private String createPadding(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    @Override
    public List<AbstractBinaryTree<E>> preOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();

        result.add(this);

        if (this.getLeft() != null) {
            List<AbstractBinaryTree<E>> abstractBinaryTrees = this.getLeft().preOrder();
            result.addAll(abstractBinaryTrees);
        }
        if (this.getRight() != null) {
            List<AbstractBinaryTree<E>> abstractBinaryTrees = this.getRight().preOrder();
            result.addAll(abstractBinaryTrees);
        }

        return result;
    }

    @Override
    public List<AbstractBinaryTree<E>> inOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();

        if (this.getLeft() != null) {
            List<AbstractBinaryTree<E>> abstractBinaryTrees = this.getLeft().inOrder();
            result.addAll(abstractBinaryTrees);

        }
        result.add(this);

        if (this.getRight() != null) {
            List<AbstractBinaryTree<E>> abstractBinaryTrees = this.getRight().inOrder();
            result.addAll(abstractBinaryTrees);
        }

        return result;
    }

    @Override
    public List<AbstractBinaryTree<E>> postOrder() {
        List<AbstractBinaryTree<E>> result = new ArrayList<>();

        if (this.getLeft() != null) {
            List<AbstractBinaryTree<E>> abstractBinaryTrees = this.getLeft().postOrder();
            result.addAll(abstractBinaryTrees);

        }

        if (this.getRight() != null) {
            List<AbstractBinaryTree<E>> abstractBinaryTrees = this.getRight().postOrder();
            result.addAll(abstractBinaryTrees);
        }

        result.add(this);

        return result;
    }

    @Override
    public void forEachInOrder(Consumer<E> consumer) {
        if (this.getLeft() != null) {
            this.getLeft().forEachInOrder(consumer);
        }
        consumer.accept(this.getKey());
        if (this.getRight() != null) {
            this.getRight().forEachInOrder(consumer);
        }
    }
}
