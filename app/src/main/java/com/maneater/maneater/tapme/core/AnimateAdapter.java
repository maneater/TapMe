package com.maneater.maneater.tapme.core;


import android.view.LayoutInflater;
import android.view.View;

public abstract class AnimateAdapter<AD extends AnimateChild<?>> {

    public abstract int getCount();

    public abstract AD onCreate(LayoutInflater inflater, int index);

    public abstract boolean onClick(AD ad, int index);


}
