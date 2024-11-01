package solutions;

import java.util.*;
import java.util.stream.Collectors;

public class BinaryTree {
    private int value;
    private BinaryTree left;
    private BinaryTree right;

    public BinaryTree(int key, BinaryTree first, BinaryTree second) {

        this.value = key;
        this.left = first;
        this.right = second;
    }

    public Integer findLowestCommonAncestor(int first, int second) {
        List<Integer> firstPath = findPath(first);
        List<Integer> secondPath = findPath(second);
        int min = Math.min(firstPath.size(), secondPath.size());
        int i = 0;
        for (; i < min; i++) {
            if (!firstPath.get(i).equals(secondPath.get(i))){
                break;
            }
        }
        return firstPath.get(i - 1);
    }

    private List<Integer> findPath(int element) {
        List<Integer> result = new ArrayList<>();
        findNodePath(this, element, result);
        return result;
    }

    private boolean findNodePath(BinaryTree binaryTree, int element, List<Integer> currentPath) {
        if (binaryTree == null) {
            return false;
        }
        if (binaryTree.value == element) {
            return true;
        }
        currentPath.add(binaryTree.value);
        boolean leftPath = findNodePath(binaryTree.left, element, currentPath);
        if (leftPath) {
            return true;
        }
        boolean rightPath = findNodePath(binaryTree.right, element, currentPath);
        if (rightPath) {
            return true;
        }
        currentPath.remove(currentPath.size() - 1);
        return false;
    }

    public List<Integer> topView() {
        Map<Integer, Pair<Integer, Integer>> map = new TreeMap<>();

        traversTree(this, 0, 1, map);
        return map
                .values()
                .stream()
                .map(Pair::getKey)
                .collect(Collectors.toList());
    }
    public List<Integer> bottomView() {
        Map<Integer, Pair<Integer, Integer>> map = new TreeMap<>();
        traversTreeBottomView(this, 0, 0, map);
        return map
                .values()
                .stream()
                .map(Pair::getKey)
                .collect(Collectors.toList());
    }

    private void traversTreeBottomView(BinaryTree binaryTree, int offset, int level,
                                       Map<Integer, Pair<Integer, Integer>> map) {

        if (binaryTree == null) {
            return;
        }
        Pair<Integer, Integer> currentValueLevel = map.get(offset);
        if (currentValueLevel == null || level >= currentValueLevel.getValue()) {
            map.put(offset, new Pair<>(binaryTree.value, level));
        }
        traversTreeBottomView(binaryTree.left, offset - 1, level + 1, map);
        traversTreeBottomView(binaryTree.right, offset + 1, level + 1, map);
    }

    private void traversTree(BinaryTree binaryTree, int offset, int level,
                             Map<Integer, Pair<Integer, Integer>> map) {

        if (binaryTree == null) {
            return;
        }

        Pair<Integer, Integer> currentValueLevel = map.get(offset);
        if (currentValueLevel == null || level < currentValueLevel.getValue()) {
            map.put(offset, new Pair<>(binaryTree.value, level));
        }

        traversTree(binaryTree.left, offset - 1, level + 1, map);
        traversTree(binaryTree.right, offset + 1, level + 1, map);
    }
}
