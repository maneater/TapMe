package com.maneater.maneater.tapme.game.color;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.maneater.maneater.tapme.R;
import com.maneater.maneater.tapme.core.AnimateChild;
import com.maneater.maneater.tapme.core.AnimateDelegate;

public class ColorGame extends AnimateDelegate<AnimateChild<Integer>> {

    private String[] colorName = new String[]{"赤", "橙", "黄", "绿", "青", "蓝", "紫"};
    private int[] colorValue = new int[]{0xffff0000, 0xffff8000, 0xffff0000, 0xff00ff00, 0xff00ffff, 0xff0000ff, 0xff8000ff};

    public ColorGame() {
    }

    @Override
    public int getCount() {
        return colorValue.length;
    }

    @Override
    public AnimateChild<Integer> onCreate(LayoutInflater inflater, int index, int globalIndex) {
        return new AnimateChild<Integer>(inflater.inflate(R.layout.view_color_child, null), globalIndex);
    }

    @Override
    public void onClickAnimateFinished(AnimateChild animateChild, int index, int globalIndex) {

    }

    @Override
    public AnimateChild<Integer> onBind(AnimateChild<Integer> animateChild, View view, int index, int globalIndex) {
        ColorView imageView = (ColorView) view.findViewById(R.id.vImg);
        imageView.setColor(colorValue[globalIndex]);
        TextView textView = (TextView) view.findViewById(R.id.vTxText);
        textView.setText(colorName[globalIndex]);
        return animateChild;
    }
}
