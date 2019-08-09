package com.xfdsj.statusbar;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class PictureStatusBarActivity extends AppCompatActivity {

    private Runnable mRunnable;
    private boolean mDestroy;
    private boolean isChanged;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int systemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
        }

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
