package implementations;

import java.util.ArrayDeque;
import java.util.Deque;

public class TheMatrix {
    private char[][] matrix;
    private char fillChar;
    private char toBeReplaced;
    private int startRow;
    private int startCol;

    public TheMatrix(char[][] matrix, char fillChar, int startRow, int startCol) {
        this.matrix = matrix;
        this.fillChar = fillChar;
        this.startRow = startRow;
        this.startCol = startCol;
        this.toBeReplaced = this.matrix[this.startRow][this.startCol];
    }

    public void solve() {
        fillMatrix(this.startRow, this.startCol);
//        Deque<int[]> coordinates = new ArrayDeque<>();
//        coordinates.offer(new int[]{this.startRow, this.startCol});
//        fillMatrixWithQueue(coordinates);
    }

    public String toOutputString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < this.matrix.length; row++) {
            for (int col = 0; col < this.matrix[row].length; col++) {
                sb.append(this.matrix[row][col]);
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    private void fillMatrixWithQueue(Deque<int[]> coordinates) {
        while (!coordinates.isEmpty()) {
            int[] position = coordinates.poll();
            int row = position[0];
            int col = position[1];
            this.matrix[row][col] = this.fillChar;

            if (isInBounds(row + 1, col) && this.matrix[row + 1][col] == this.toBeReplaced) {
                coordinates.offer(new int[]{row + 1, col});
            }
            if (isInBounds(row - 1, col) && this.matrix[row - 1][col] == this.toBeReplaced) {
                coordinates.offer(new int[]{row - 1, col});
            }
            if (isInBounds(row, col + 1) && this.matrix[row][col + 1] == this.toBeReplaced) {
                coordinates.offer(new int[]{row, col + 1});
            }
            if (isInBounds(row, col - 1) && this.matrix[row][col - 1] == this.toBeReplaced) {
                coordinates.offer(new int[]{row, col - 1});
            }
        }
    }

    private void fillMatrix(int row, int col) {
        if (isOutOfBounds(row, col) || this.matrix[row][col] != this.toBeReplaced) {
            return;
        }

        this.matrix[row][col] = this.fillChar;

        fillMatrix(row + 1, col);
        fillMatrix(row, col + 1);
        fillMatrix(row - 1, col);
        fillMatrix(row, col - 1);
    }

    private boolean isInBounds(int row, int col) {
        return !this.isOutOfBounds(row, col);
    }

    private boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= this.matrix.length || col < 0 || col >= this.matrix[row].length;
    }
}
