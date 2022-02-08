package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) throws IllegalArgumentException {
        checkTheListsToNull(x);
        checkTheListsToNull(y);
        for (int i = 0; i < x.size(); i++) {
            int index = y.indexOf(x.get(i));
            if (index != -1) {
                y = y.subList(index, y.size());
            } else {
                return false;
            }
        }
        return true;
    }

    private void checkTheListsToNull(List y) {
        if (y == null){
            throw new IllegalArgumentException();
        }
    }


}
