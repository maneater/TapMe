package com.maneater.maneater.tapme.core;

import android.view.View;


public class AnimateChild<T> {
    View animateView;
    T animateData;
    int position = -1;

    public AnimateChild(View animateView, T animateData) {
        this.animateView = animateView;
        this.animateData = animateData;
    }

    public View getAnimateView() {
        return animateView;
    }

    public T getAnimateData() {
        return animateData;
    }
}
