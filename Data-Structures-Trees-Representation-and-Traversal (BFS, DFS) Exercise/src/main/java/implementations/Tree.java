package implementations;

import interfaces.AbstractTree;

import java.util.*;
import java.util.stream.Collectors;

public class Tree<E extends Number> implements AbstractTree<E> {

    private E key;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E key) {
        this.key = key;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    @Override
    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public String getAsString() {
        StringBuilder sb = new StringBuilder();
        traverseTreeWithRecurrence(sb, 0, this);
        return sb.toString().trim();
    }


    @Override
    public List<E> getLeafKeys() {
        List<Tree<E>> allTrees = new ArrayList<>();
        this.traversWithDFS(allTrees, this);
        return allTrees
                .stream()
                .filter(tree -> tree.children.isEmpty())
                .map(Tree::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public List<E> getMiddleKeys() {
        return this.traverseWithBFS()
                .stream()
                .filter(tree -> !tree.children.isEmpty() && tree.parent != null)
                .map(Tree::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {
        List<Tree<E>> trees = this.traverseWithBFS();
        Tree<E> deepestLeftmostNode = null;
        int maxPath = 0;
        for (Tree<E> tree : trees) {
            if (tree.isLeaf()) {
                int currentPath = getStepsFromLeafToRoot(tree);
                if (currentPath > maxPath) {
                    maxPath = currentPath;
                    deepestLeftmostNode = tree;
                }
            }
        }
        return deepestLeftmostNode;
    }

    private int getStepsFromLeafToRoot(Tree<E> tree) {
        int count = 0;
        Tree<E> current = tree;
        while (current.parent != null) {
            count++;
            current = current.parent;
        }
        return count;
    }

    private boolean isLeaf() {
        return this.parent != null && this.children.isEmpty();
    }

    @Override
    public List<E> getLongestPath() {
//        List<Tree<E>> trees = this.traverseWithBFS();
//        List<E> longestPathKeys = new ArrayList<>();
//        for (Tree<E> tree : trees) {
//            if (tree.isLeaf()) {
//                List<E> currentLongestPathKeys = getLongestPathKeys(tree);
//                if (currentLongestPathKeys.size() > longestPathKeys.size()) {
//                    longestPathKeys = currentLongestPathKeys;
//                }
//            }
//        }
//        Collections.reverse(longestPathKeys);
//        return longestPathKeys;
        List<E> longestPath = new ArrayList<>();
        List<E> currentPath = new ArrayList<>();
        findLongestPath(this, longestPath, currentPath);
        return longestPath;
    }

    private void findLongestPath(Tree<E> tree, List<E> longestPath, List<E> currentPath) {
        currentPath.add(tree.getKey());

        if (tree.isLeaf()) {
            if (currentPath.size() > longestPath.size()) {
                longestPath.clear();
                longestPath.addAll(currentPath);
            }
        } else {
            for (Tree<E> child : tree.children) {
                findLongestPath(child, longestPath, currentPath);
            }
        }
        currentPath.remove(currentPath.size() - 1);
    }

    private List<E> getLongestPathKeys(Tree<E> tree) {
        List<E> result = new ArrayList<>();
        Tree<E> current = tree;
        while (current.parent != null) {
            result.add(current.getKey());
            current = current.parent;
        }
        result.add(current.getKey());
        return result;
    }


    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        List<List<E>> result = new ArrayList<>();
        findPath(this, new ArrayList<>(), result, sum);
        return result;
    }

    private void findPath(Tree<E> tree, List<E> currentPath, List<List<E>> result, int sum) {
        currentPath.add(tree.getKey());

        if (tree.isLeaf()) {
            int currentSum = currentPath.stream().mapToInt(Number::intValue).sum();
            if (currentSum == sum) {
                result.add(new ArrayList<>(currentPath));
            }
        }
        for (Tree<E> child : tree.children) {
            findPath(child, currentPath, result, sum);
        }
        currentPath.remove(currentPath.size() - 1);
    }


    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        List<Tree<E>> result = new ArrayList<>();
        findSubTreesWithGivenSum(this, sum, result);
        return result;
    }

    private int findSubTreesWithGivenSum(Tree<E> node, int targetSum, List<Tree<E>> result) {
        int subtreeSum = node.getKey().intValue();
        for (Tree<E> child : node.children) {
            subtreeSum += findSubTreesWithGivenSum(child, targetSum, result);
        }

        if (subtreeSum == targetSum) {
            result.add(node);
        }

        return subtreeSum;
    }

    public List<Tree<E>> traverseWithBFS() {
        Deque<Tree<E>> queue = new ArrayDeque<>();
        queue.offer(this);
        List<Tree<E>> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            Tree<E> current = queue.poll();
            result.add(current);
            for (Tree<E> child : current.children) {
                queue.offer(child);
            }
        }
        return result;
    }

    public void traversWithDFS(List<Tree<E>> collection, Tree<E> tree) {
        collection.add(tree);
        for (Tree<E> child : tree.children) {
            traversWithDFS(collection, child);
        }
    }

    private void traverseTreeWithRecurrence(StringBuilder sb, int indent, Tree<E> tree) {
        sb
                .append(getIndent(indent))
                .append(tree.getKey())
                .append(System.lineSeparator());
        for (Tree<E> child : tree.children) {
            traverseTreeWithRecurrence(sb, indent + 2, child);
        }
    }

    private String getIndent(int size) {
        return " ".repeat(Math.max(0, size));
    }
}



