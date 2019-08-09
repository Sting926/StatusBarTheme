package com.xfdsj.statusbar;

import android.os.Handler;
import android.os.Looper;

public class UiHandler {
    private static android.os.Handler sHandler = new Handler(Looper.getMainLooper());

    public static void runOnUI(Runnable r) {
        sHandler.post(r);
    }

    public static void runOnUIDelayed(Runnable r, long delayMills) {
        sHandler.postDelayed(r, delayMills);
    }

    public static void removeOnUI(Runnable r) {
        sHandler.removeCallbacks(r);
    }
}
