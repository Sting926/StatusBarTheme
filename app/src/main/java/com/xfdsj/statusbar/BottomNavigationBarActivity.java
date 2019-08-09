package com.xfdsj.statusbar;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BottomNavigationBarActivity extends AppCompatActivity {

    private int mCurrentPos = -1;
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_in_fragment);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int systemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
        }

        mFragments = Arrays.asList(
                FirstFragment.newInstance(),
                SecondFragment.newInstance(0),
                SecondFragment.newInstance(1),
                SecondFragment.newInstance(2)
        );

        BottomNavigationView bottomMain = findViewById(R.id.bottom_main);
        bottomMain.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.one:
                    switchFragmentIndex(0);
                    break;
                case R.id.two:
                    switchFragmentIndex(1);
                    break;
                case R.id.three:
                    switchFragmentIndex(2);
                    break;
                case R.id.four:
                    switchFragmentIndex(3);
                    break;
                default:
                    break;
            }
            return true;
        });
        switchFragmentIndex(0);
    }

    private void switchFragmentIndex(int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentPos != -1) {
            fragmentTransaction.hide(mFragments.get(mCurrentPos));
        }
        if (!mFragments.get(position).isAdded()) {
            fragmentTransaction.add(R.id.fl_content, mFragments.get(position));
        }
        fragmentTransaction.show(mFragments.get(position)).commit();
        mCurrentPos = position;
    }
}
