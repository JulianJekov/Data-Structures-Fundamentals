package solutions;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class TopViewTest {

    @Test
    public void testLCA() {
        BinaryTree binaryTree =
                new BinaryTree(1,
                        new BinaryTree(2,
                                new BinaryTree(4, null, null),
                                new BinaryTree(5, null, null)),
                        new BinaryTree(3,
                                new BinaryTree(6, null, null),
                                new BinaryTree(7, null, null)));


        List<Integer> list = binaryTree.topView();

        Collections.sort(list);
        Integer[] expected = {1, 2, 3, 4, 7};

        assertEquals(expected.length, list.size());

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], list.get(i));
        }
    }

    @Test
    public void testBottomView() {
        BinaryTree binaryTree = new BinaryTree(20,
                new BinaryTree(22,
                        new BinaryTree(25, null, null),
                        new BinaryTree(3,
                                new BinaryTree(14, null,
                                        new BinaryTree(7, null, null)),
                                new BinaryTree(10, null, null))),
                new BinaryTree(8, null,
                        new BinaryTree(5, null, null)));
        List<Integer> list = binaryTree.bottomView();
        Collections.sort(list);
        Integer[] expected = {5, 7, 10, 14, 25};
        assertEquals(expected.length, list.size());
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], list.get(i));
        }
    }
}
