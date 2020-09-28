package com.cpsc544.bubblesort.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.cpsc544.bubblesort.animation.AnimationScenarioItem;

public class BubbleSort implements SortAlgorithm {
    List<List<Integer>> valuesAfterSort;
    List<List<AnimationScenarioItem>> scenarios;

    public BubbleSort() {
        valuesAfterSort = new ArrayList<>();
        scenarios = new ArrayList<>();
    }

    @Override
    public void sort(int[] input) {
        boolean isLastInLoop;
        List<Integer> values = Arrays.stream(input).boxed().collect(Collectors.toList());
        for (int i = 0; i < input.length - 1; i++) {
            List<AnimationScenarioItem> scenario = new ArrayList<>();
            for (int j = 0; j < values.size() - i - 1; j++) {
                if (j == values.size() - i - 2) {
                    isLastInLoop = true;
                } else {
                    isLastInLoop = false;
                }
                if (values.get(j) > values.get(j + 1)) {
                    swap(values, j);
                    scenario.add(new AnimationScenarioItem(true, j, isLastInLoop));
                } else {
                    scenario.add(new AnimationScenarioItem(false, j, isLastInLoop));
                }
            }
            valuesAfterSort.add(new ArrayList<>(values));
            scenarios.add(scenario);
        }
    }

    @Override
    public List<List<Integer>> getSortedAfterEachIteration() {
        return valuesAfterSort;
    }

    @Override
    public List<List<AnimationScenarioItem>> getAnimationScenarios() {
        return scenarios;
    }

    private void swap(final List<Integer> list, final int inner) {
        int temp = list.get(inner);
        list.set(inner, list.get(inner + 1));
        list.set(inner + 1, temp);
    }
}
