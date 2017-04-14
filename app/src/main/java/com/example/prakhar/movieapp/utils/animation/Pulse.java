package com.example.prakhar.movieapp.utils.animation;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

/**
 * Created by Prakhar on 2/25/2017.
 */

public class Pulse extends AlphaAnimation {
    private static final long DEFAULT_DURATION = 1200;
    private static final float DEFAULT_FROM_ALPHA = 0.3f;
    private static final float DEFAULT_TO_ALPHA = 0.6f;


    public Pulse() {
        super(DEFAULT_FROM_ALPHA, DEFAULT_TO_ALPHA);
        setDuration(DEFAULT_DURATION);
        setInterpolator(new LinearInterpolator());
        setRepeatCount(INFINITE);
        setRepeatMode(Animation.REVERSE);
    }
}
