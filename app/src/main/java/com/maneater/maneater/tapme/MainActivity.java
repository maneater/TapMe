package com.maneater.maneater.tapme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.maneater.maneater.tapme.game.color.ColorGame;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.vVehicle)
    View vVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.vVehicle, R.id.vColor})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vVehicle:
                break;
            case R.id.vColor:
                PlayActivity.launch(MainActivity.this, ColorGame.class);
                break;
        }
    }

}
