package com.maneater.maneater.tapme;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.maneater.maneater.tapme.core.AnimateAdapter;
import com.maneater.maneater.tapme.core.AnimateChild;
import com.maneater.maneater.tapme.core.AnimateLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayActivity extends AppCompatActivity {

    @BindView(R.id.animateLayout)
    AnimateLayout animateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        animateLayout.setAnimateAdapter(new AnimateAdapter() {
            @Override
            public int getCount() {
                return 20;
            }

            @Override
            public AnimateChild<?> onCreate(LayoutInflater inflater, int index) {
                FrameLayout frameLayout = new FrameLayout(PlayActivity.this);
                final ImageView imageView = new AppCompatImageView(PlayActivity.this);
                imageView.setImageResource(R.mipmap.ic_launcher);
                frameLayout.addView(imageView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                TextView textView = new TextView(PlayActivity.this);
                textView.setText(Math.random() > 0.5f ? "noting" : "everything");
                textView.setTextColor(Color.WHITE);
                textView.setVisibility(View.GONE);
                textView.setBackgroundColor(Color.RED);
                frameLayout.addView(textView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return new AnimateChild<>(frameLayout, null);
            }

            @Override
            public boolean onClick(AnimateChild animateChild, int index) {
                return false;
            }
        });
    }
}
