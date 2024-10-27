package solutions;

import java.util.ArrayList;
import java.util.List;

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
        return null;
    }
}
