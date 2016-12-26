package com.maneater.maneater.tapme;

import android.app.Activity;
import android.content.Intent;
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

    public static void launch(Activity activity, Class<? extends AnimateDelegate> target) {
        Intent intent = new Intent(activity, PlayActivity.class);
        intent.putExtra("target", target);
        activity.startActivity(intent);
    }

    @BindView(R.id.animateLayout)
    AnimateLayout animateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        try {
            Class clz = (Class) getIntent().getSerializableExtra("target");
            AnimateDelegate delegate = (AnimateDelegate) clz.newInstance();
            animateLayout.setAnimateDelegate(delegate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
