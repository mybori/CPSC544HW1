package com.cpsc544.bubblesort.animation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cpsc544.bubblesort.utls.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AnimationController {
    private Context context;
    private int scenarioItemIndex = 0;
    private int scenarioIndex = 0;
    private AnimationsActor animationsActor;
    private ArrayList<AlgorithmAnimationListener> listeners;

    public AnimationController(Context context)
    {
        this.context = context;
    }

    public void runAnimation(int[] input,
                             List<List<Integer>> valuesAfterSort,
                             List<List<AnimationScenarioItem>> scenarios,
                             ViewGroup animationCanvas)
    {
        this.addListener(new AlgorithmAnimationListener() {
            @Override
            public void onAnimationEnd(int endedPosition) {
                scenarioIndex++;
                if (scenarioIndex < scenarios.size()) {
                    runAnimationIteration(valuesAfterSort.get(scenarioIndex - 1),
                            scenarios.get(scenarioIndex), animationCanvas);
                } else if (scenarioIndex == scenarios.size()) {
                    ViewGroup bubblesContainer = new LinearLayout(context);
                    drawBubbles(valuesAfterSort.get(scenarioIndex - 1), -1, bubblesContainer);
                    animationCanvas.addView(bubblesContainer);
                }
            }
        });
        runAnimationIteration(Arrays.stream(input).boxed().collect(Collectors.toList()),
                scenarios.get(scenarioIndex), animationCanvas);
    }

    private void runAnimationIteration(List<Integer> listToDraw, final List<AnimationScenarioItem> scenario, ViewGroup animationCanvas)
    {
        ViewGroup bubblesContainer = new LinearLayout(context);
        drawBubbles(listToDraw, scenario.size(), bubblesContainer);
        animationCanvas.addView(bubblesContainer);
        runAnimation(bubblesContainer, scenario);
    }

    private void drawBubbles(List<Integer> listToDraw, int lastUnsortedIndex, ViewGroup bubblesContainer) {
        if (bubblesContainer != null) {
            bubblesContainer.removeAllViews();
            bubblesContainer.clearAnimation();
        }

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int bubbleMargin = 4;
        int marginInPx = Utils.dpToPx(context, bubbleMargin);
        lp.setMargins(0, 0, marginInPx, 0);

        for (int i = 0; i < listToDraw.size(); i++) {
            Integer currentIntValue = listToDraw.get(i);
            BubbleImageView bubbleImageView = new BubbleImageView(context);
            bubbleImageView.setImageBitmap(createCalculatedBitmap(currentIntValue));
            bubbleImageView.setMinimumHeight(200);
            bubbleImageView.setNumber(currentIntValue);
            if (i > lastUnsortedIndex) {
                bubbleImageView.setBubbleIsOnFinalPlace(true);
            }
            bubbleImageView.setId(i);
            if (bubblesContainer != null) {
                bubblesContainer.addView(bubbleImageView, lp);
            }
        }
    }

    private Bitmap createCalculatedBitmap(Integer currentIntValue) {
        final Rect bounds = new Rect();
        final int padding = 48;
        Paint paint = new Paint(Paint.LINEAR_TEXT_FLAG);
        paint.setTextSize(BubbleImageView.TEXT_SIZE);
        paint.getTextBounds(currentIntValue.toString(), 0, currentIntValue.toString().length(), bounds);
        float radius = bounds.height() * 0.5f + padding;
        return Bitmap.createBitmap((int)radius + padding, (int)radius + padding, Bitmap.Config.ALPHA_8);
    }

    public void runAnimation(ViewGroup bubblesContainer, final List<AnimationScenarioItem> scenario)
    {
        animationsActor = new AnimationsActor(bubblesContainer);
        animationsActor.removeListeners();
        animationsActor.addListener(new AlgorithmAnimationListener() {
            @Override
            public void onAnimationEnd(int endedPosition) {
                if (scenarioItemIndex < scenario.size()) {
                    runAnimationStep(scenario);
                } else {
                    notifyAnimationEnd(scenarioItemIndex);
                }
            }
        });
        scenarioItemIndex = 0;
        runAnimationStep(scenario);
    }

    public void addListener(AlgorithmAnimationListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    private void notifyAnimationEnd(int position) {
        if (listeners != null && !listeners.isEmpty()) {
            int numListeners = listeners.size();
            for (int i = 0; i < numListeners; ++i) {
                listeners.get(i).onAnimationEnd(position);
            }
        }
    }

    private void runAnimationStep(List<AnimationScenarioItem> scenario) {
        if (scenario == null || scenario.isEmpty()) {
            return;
        }

        AnimationScenarioItem animationStep = scenario.get(scenarioItemIndex);
        scenarioItemIndex++;
        if (animationStep.isShouldBeSwapped()) {
            animationsActor.showSwapStep(animationStep.getAnimationViewItemPosition(), animationStep.isFinalPlace());
        } else {
            animationsActor.showNonSwapStep(animationStep.getAnimationViewItemPosition(), animationStep.isFinalPlace());
        }
    }
}
