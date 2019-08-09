package com.xfdsj.statusbar;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Random;

public class GradientStatusBarActivity extends AppCompatActivity {

    private int mStartColor;
    private int mEndColor;
    private Runnable mRunnable;
    private boolean mDestroy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gradient_activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int systemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
        }

        Toolbar mToolbar = findViewById(R.id.toolbar);
        StatusBarUtil.setPaddingTop(this, mToolbar);

        mRunnable = () -> {
            Random random = new Random();
            mStartColor = 0xff000000 | random.nextInt(0xffffff);
            mEndColor = 0xff000000 | random.nextInt(0xffffff);
            int[] colors = {mStartColor, mEndColor};
            GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
            mToolbar.setBackground(gradientDrawable);
            if (!mDestroy) {
                UiHandler.runOnUIDelayed(mRunnable, 1500);
            }
        };

        UiHandler.runOnUIDelayed(mRunnable, 1000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDestroy = true;
        UiHandler.removeOnUI(mRunnable);
    }
}
