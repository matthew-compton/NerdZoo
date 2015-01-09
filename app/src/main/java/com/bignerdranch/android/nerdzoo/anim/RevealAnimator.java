package com.bignerdranch.android.nerdzoo.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.bignerdranch.android.nerdzoo.util.BuildUtils;

public class RevealAnimator {

    public static void show(View view) {
        if (BuildUtils.isLollipopEnabled()) {
            int cx = (view.getLeft() + view.getRight()) / 2;
            int cy = (view.getTop() + view.getBottom()) / 2;
            int finalRadius = Math.max(view.getWidth(), view.getHeight());
            Animator animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
            animator.setDuration(1000);
            view.setVisibility(View.VISIBLE);
            animator.start();
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hide(View view) {
        if (BuildUtils.isLollipopEnabled()) {
            int cx = (view.getLeft() + view.getRight()) / 2;
            int cy = (view.getTop() + view.getBottom()) / 2;
            int initialRadius = view.getWidth();
            Animator animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);
            animator.setDuration(1000);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.INVISIBLE);
                }
            });
            animator.start();
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

}
