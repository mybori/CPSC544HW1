package com.cpsc544.bubblesort.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class BubbleImageView extends AppCompatImageView {
    public static final float TEXT_SIZE = 40f;
    private int PADDING = 40;
    private Integer valueToDraw;
    private boolean isSelected;
    private boolean isOnFinalPlace;

    public BubbleImageView(Context context) {
        this(context, null);
    }

    public BubbleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (valueToDraw != null) {
            String text = valueToDraw.toString();
            Paint paint = new Paint(Paint.LINEAR_TEXT_FLAG);
            Rect bounds = new Rect();
            paint.setAntiAlias(true);
            paint.setTextSize(TEXT_SIZE);
            paint.getTextBounds(text, 0, text.length(), bounds);
            if (isOnFinalPlace) {
                paint.setColor(Color.GREEN);
            } else {
                if (isSelected) {
                    paint.setColor(Color.BLUE);
                } else {
                    paint.setColor(Color.GRAY);
                }
            }
            int bWidth = bounds.width();
            int bHeight = bounds.height();
            float radius = bHeight * 0.5f + PADDING;
            float cx = radius, cy = radius;
            canvas.drawCircle(cx, cy, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText(text, cx - bWidth * 0.5f, cy + bHeight * 0.5f, paint);
        }
    }

    /**
     * Draws a number as a bitmap inside of the bubble circle.
     *
     * @param numberValueToDraw value which should appears in the center of {@link BubbleImageView}
     */
    public void setNumber(Integer numberValueToDraw) {
        valueToDraw = numberValueToDraw;
        invalidate();
    }

    /**
     * Background color of bubble will be changed to dark blue.
     *
     * @param isOnFinalPlace
     */
    public void setBubbleIsOnFinalPlace(boolean isOnFinalPlace) {
        this.isOnFinalPlace = isOnFinalPlace;
        invalidate();
    }

    public boolean isBubbleSelected() {
        return isSelected;
    }

    /**
     * Background color will be changed to blue if true
     *
     * @param isSelected
     */
    public void setBubbleSelected(boolean isSelected) {
        this.isSelected = isSelected;
        invalidate();
    }
}
