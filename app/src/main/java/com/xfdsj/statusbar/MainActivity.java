package com.xfdsj.statusbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private boolean mDarkMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View bg = findViewById(R.id.bg);
        mToolbar = findViewById(R.id.toolbar);

        bg.setBackgroundColor(ContextCompat.getColor(this, R.color.start_color));

        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        TextView mVersionText = findViewById(R.id.version_text);

        // 设置状态栏颜色
        btn1.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ColorStatusBarActivity.class)));

        // 设置状态栏渐变
        btn2.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, GradientStatusBarActivity.class)));

        // 设置状态栏图片背景
        btn3.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PictureStatusBarActivity.class)));

        // 在fragment中使用
        btn4.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, BottomNavigationBarActivity.class)));

        // 设置状态栏模式（暗色）/ 设置状态栏模式（亮光）仅在android6.0及以上有效
        btn5.setOnClickListener(v -> {
            if (mDarkMode) {
                bg.setBackgroundColor(ContextCompat.getColor(this, R.color.end_color));
                mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.end_color));
                StatusBarUtil.setDarkMode(MainActivity.this);
            } else {
                bg.setBackgroundColor(ContextCompat.getColor(this, R.color.start_color));
                mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.start_color));
                StatusBarUtil.setLightMode(MainActivity.this);
            }
            mDarkMode = !mDarkMode;
        });

        mVersionText.setText(
                "Android版本："
                        + android.os.Build.VERSION.RELEASE
                        + "\n手机厂商："
                        + android.os.Build.BRAND
                        + "\n手机型号："
                        + android.os.Build.MODEL
        );
    }
}
