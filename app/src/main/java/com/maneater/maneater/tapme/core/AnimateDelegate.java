package com.maneater.maneater.tapme.core;


import android.view.LayoutInflater;
import android.view.View;

public abstract class AnimateDelegate<AD extends AnimateChild<?>> {

    public boolean cacheData() {
        return false;
    }

    public abstract int getCount();

    public abstract AD onCreate(LayoutInflater inflater, int index);

    public abstract AD onBind(AD ad, View view, int index);

    /**
     * @param ad
     * @param index
     * @return true will stop, false will use animate
     */
    public boolean onClick(AD ad, int index) {
        return false;
    }

    public abstract void onClickAnimateFinished(AD ad, int index);


}
