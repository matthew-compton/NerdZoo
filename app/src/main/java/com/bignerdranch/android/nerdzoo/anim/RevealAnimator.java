package com.bignerdranch.android.nerdzoo.anim;

import android.animation.Animator;
import android.view.View;
import android.view.ViewAnimationUtils;

public class RevealAnimator {

    public static void reveal(View view) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        anim.setDuration(1000);
        view.setVisibility(View.VISIBLE);
        anim.start();
    }

}
