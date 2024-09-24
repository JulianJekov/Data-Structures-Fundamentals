package representation;

public class ArrayStorage {
    private final int INITIAL_CAPACITY = 4;

    private int[] elements;
    private int index;

    public ArrayStorage() {
        this.elements = new int[INITIAL_CAPACITY];
        this.index = 0;
    }

    public boolean add(int element) {
        add(element, ++index);
        return true;
    }

    private void add(int element, int index) {
        if (index == this.elements.length) {
            // TODO: Add grow method call here
            elements = grow(elements);
        }
        this.elements[index] = element;
    }

    private static int[] grow(int[] elements) {
        int[] newElements = new int[elements.length * 2];
        System.arraycopy(elements, 0, newElements, 0, elements.length);
        return newElements;
    }

    // TODO: Implement additional operations like: remove(int element), contains(int element) and more
}
