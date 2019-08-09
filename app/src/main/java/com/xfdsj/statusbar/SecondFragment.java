package com.xfdsj.statusbar;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SecondFragment extends Fragment {

    private int position = -1;

    public static SecondFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("extra", position);
        SecondFragment fragment = new SecondFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.second_fragment, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            position = getArguments().getInt("extra");
        }

        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        TextView textView = view.findViewById(R.id.txt);
        LinearLayout container = view.findViewById(R.id.container);

        StatusBarUtil.setPaddingTop(getActivity(), mToolbar);
        switch (position) {
            case 0:
                mToolbar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.end_color));
                container.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.photo2));
                textView.setText("Second fragment");
                break;
            case 1:
                mToolbar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                container.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.photo3));
                textView.setText("Third fragment");
                break;
            case 2:
                mToolbar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black));
                container.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.photo4));
                textView.setText("Fourth fragment");
                break;
            default:
                break;
        }

    }
}
