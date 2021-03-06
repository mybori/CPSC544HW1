package com.cpsc544.bubblesort.animation;

public interface AlgorithmStepsInterface {
    /**
     * Visualizes step, when elements should change their places with each other
     *
     * @param position             position of the firs element, which should be changed
     * @param isBubbleOnFinalPlace set true, when element after swapping is on the right place and his position is final
     */
    void showSwapStep(int position, boolean isBubbleOnFinalPlace);

    /**
     * Visualizes step, when elements should stay on the same places;
     *
     * @param position             position of the firs element
     * @param isBubbleOnFinalPlace set true, when element on position+1 is on the right place and his position is final
     */
    void showNonSwapStep(int position, boolean isBubbleOnFinalPlace);

    /**
     * Call when last item was sorted. Notifies user that sorting is finished.
     */
    void showFinish();

    /**
     * Cancel all current animations
     */
    void cancelAllVisualisations();
}
