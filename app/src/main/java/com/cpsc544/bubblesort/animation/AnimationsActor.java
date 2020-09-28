package com.cpsc544.bubblesort.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.ViewGroup;

import java.util.ArrayList;

public class AnimationsActor implements AlgorithmStepsInterface {
    private ViewGroup bubblesContainer;
    private ArrayList<AlgorithmAnimationListener> listeners;
    private ValueAnimator blinkAnimation;

    public AnimationsActor(ViewGroup bubblesContainer) {
        this.bubblesContainer = bubblesContainer;
    }

    @Override
    public void showSwapStep(final int position, final boolean isBubbleOnFinalPosition) {
        if (bubblesContainer != null &&
                bubblesContainer.getChildCount() > 0 &&
                bubblesContainer.getChildCount() > position + 1) {
            final BubbleImageView tempView = (BubbleImageView) bubblesContainer.getChildAt(position);
            final BubbleImageView nextTempView = (BubbleImageView) bubblesContainer.getChildAt(position + 1);

            //BLINKING
            blinkAnimation = ValueAnimator.ofInt(0, 5);
            blinkAnimation.setDuration(2000);
            blinkAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    if (value % 2 == 0) {
                        tempView.setBubbleSelected(false);
                        nextTempView.setBubbleSelected(false);
                    } else {
                        tempView.setBubbleSelected(true);
                        nextTempView.setBubbleSelected(true);
                    }
                }
            });


            blinkAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    tempView.setBubbleSelected(false);
                    tempView.setBubbleIsOnFinalPlace(isBubbleOnFinalPosition);
                    nextTempView.setBubbleSelected(false);
                    bubblesContainer.removeView(tempView);
                    bubblesContainer.addView(tempView, position + 1);

                    notifySwapStepAnimationEnd(position);
                }
            });

            blinkAnimation.start();
        }
    }

    @Override
    public void showNonSwapStep(final int position, final boolean isBubbleOnFinalPlace) {
        if (bubblesContainer != null && bubblesContainer.getChildCount() > 0 && bubblesContainer.getChildCount() > position + 1) {
            final BubbleImageView tempView = (BubbleImageView) bubblesContainer.getChildAt(position);
            final BubbleImageView nextTempView = (BubbleImageView) bubblesContainer.getChildAt(position + 1);

            //BLINKING
            blinkAnimation = ValueAnimator.ofInt(0, 7);
            blinkAnimation.setDuration(3000);
            blinkAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = ((Integer) animation.getAnimatedValue()).intValue();
                    if (value % 2 == 0) {
                        tempView.setBubbleSelected(false);
                        nextTempView.setBubbleSelected(false);
                    } else {
                        tempView.setBubbleSelected(true);
                        nextTempView.setBubbleSelected(true);
                    }
                }
            });

            blinkAnimation.start();
            blinkAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    tempView.setBubbleSelected(false);
                    nextTempView.setBubbleSelected(false);
                    nextTempView.setBubbleIsOnFinalPlace(isBubbleOnFinalPlace);

                    notifySwapStepAnimationEnd(position);
                }
            });
        }
    }

    @Override
    public void showFinish() {
        if (bubblesContainer != null && bubblesContainer.getChildCount() > 0) {
            ((BubbleImageView) bubblesContainer.getChildAt(0)).setBubbleIsOnFinalPlace(true);
        }
    }

    @Override
    public void cancelAllVisualisations() {
        if (blinkAnimation != null) {
            blinkAnimation.removeAllListeners();
            blinkAnimation.cancel();
            bubblesContainer.clearAnimation();
        }
    }

    private void notifySwapStepAnimationEnd(int position) {
        if (listeners != null && !listeners.isEmpty()) {
            int numListeners = listeners.size();
            for (int i = 0; i < numListeners; ++i) {
                listeners.get(i).onAnimationEnd(position);
            }
        }
    }

    public void addListener(AlgorithmAnimationListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    public void removeListeners()
    {
        if (listeners == null) {
            return;
        }
        listeners.clear();
    }

    public void removeListener(Animator.AnimatorListener listener) {
        if (listeners == null) {
            return;
        }
        listeners.remove(listener);
        if (listeners.size() == 0) {
            listeners = null;
        }
    }
}
