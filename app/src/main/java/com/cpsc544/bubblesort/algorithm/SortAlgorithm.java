package com.cpsc544.bubblesort.algorithm;

import com.cpsc544.bubblesort.animation.AnimationScenarioItem;

import java.util.List;

public interface SortAlgorithm {
    public void sort(int[] input);
    public List<List<Integer>> getSortedAfterEachIteration();
    public List<List<AnimationScenarioItem>> getAnimationScenarios();
}
