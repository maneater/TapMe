package com.maneater.maneater.tapme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.maneater.maneater.tapme.core.AnimateChild;
import com.maneater.maneater.tapme.core.AnimateDelegate;
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
        animateLayout.setAnimateDelegate(new AnimateDelegate() {
            @Override
            public int getCount() {
                return 20;
            }

            @Override
            public AnimateChild<?> onCreate(LayoutInflater inflater, int index) {
                return new AnimateChild<>(inflater.inflate(R.layout.view_aniamte_child, null), null);
            }

            @Override
            public AnimateChild<?> onBind(AnimateChild animateChild, View view, int index) {
                TextView vTxText = (TextView) view.findViewById(R.id.vTxText);
                vTxText.setText(String.valueOf(index));
                return animateChild;
            }


            @Override
            public void onClickAnimateFinished(AnimateChild animateChild, int index) {

            }


        });
    }
}
