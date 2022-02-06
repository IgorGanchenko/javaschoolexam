package com.tsystems.javaschool.tasks.pyramid;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        pyramidChecking(inputNumbers);
        Collections.sort(inputNumbers);

        int rows = getNumbersOfRows(inputNumbers);
        int columns = rows * 2 - 1;
        int[][] pyramid = new int[rows][columns];

        int counter = 0;
        for (int i = 0; i < rows; i++) {
            int firstIndex = columns / 2 - i;
            int necessaryQty = i + 1;
            for (int j = 0; j < columns; j++) {
                if (necessaryQty > 0) {
                    if (counter < inputNumbers.size()) {
                        if (firstIndex == j) {
                            pyramid[i][j] = inputNumbers.get(counter);
                            counter++;
                            firstIndex += 2;
                            necessaryQty--;
                        }
                    }
                } else {
                    pyramid[i][j] = 0;
                }
            }
        }
        return pyramid;
    }

    private void pyramidChecking(List<Integer> inputNumbers) {
        if (inputNumbers.contains(null) || !checkIfPossibleCreate(inputNumbers)) {
            throw new CannotBuildPyramidException();
        }
    }

    private boolean checkIfPossibleCreate(List<Integer> inputNumbers) {
        int row = getNumbersOfRows(inputNumbers);
        int qty = inputNumbers.size();
        double necessaryQty = (1 + row) / 2.0 * row;
        return (necessaryQty == qty);
    }

    private int getNumbersOfRows(List<Integer> inputNumbers) {
        int rows = 0;
        for (int i = 0; i < inputNumbers.size(); i = rows + i) {
            rows++;
        }
        return rows;
    }

}
