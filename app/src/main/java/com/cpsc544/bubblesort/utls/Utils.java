package com.cpsc544.bubblesort.utls;

import android.content.Context;

public class Utils {
    public static int dpToPx(Context context, int sizeInDp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (sizeInDp * scale + 0.5f);
    }
}
