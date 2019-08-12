package com.xfdsj.statusbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class PictureStatusBarActivity extends AppCompatActivity {

    private Runnable mRunnable;
    private boolean mDestroy;
    private boolean isChanged;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_activity);

        StatusBarUtil.setImmerseStatusBarSystemUiVisibility(this);

        ImageView imageView = findViewById(R.id.iv_bg);

        mRunnable = () -> {
            isChanged = !isChanged;
            if (isChanged) {
                imageView.setImageResource(R.mipmap.photo6);
            } else {
                imageView.setImageResource(R.mipmap.photo5);
            }
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
