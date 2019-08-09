package com.xfdsj.statusbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Random;

public class ColorStatusBarActivity extends AppCompatActivity {

    private int mColor;
    private Runnable mRunnable;
    private boolean mDestroy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_activity);

        View bg = findViewById(R.id.bg);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        bg.setBackgroundColor(ContextCompat.getColor(this, R.color.start_color));

        mRunnable = () -> {
            Random random = new Random();
            mColor = 0xff000000 | random.nextInt(0xffffff);
            mToolbar.setBackgroundColor(mColor);
            bg.setBackgroundColor(mColor);
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
