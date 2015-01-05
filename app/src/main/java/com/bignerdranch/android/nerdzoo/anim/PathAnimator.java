package com.bignerdranch.android.nerdzoo.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Path;
import android.view.View;

import com.bignerdranch.android.nerdzoo.R;

public class PathAnimator {

    public static void showFromRight(Context context, View view, int width, int height) {
        int startingX = width;
        int startingY = height - view.getHeight() - context.getResources().getDimensionPixelSize(R.dimen.fab_remove_margin_bottom);
        int endingX = width - view.getWidth() - context.getResources().getDimensionPixelSize(R.dimen.padding_large);
        int endingY = startingY;

        Path path = new Path();
        path.moveTo(startingX, startingY);
        path.lineTo(endingX, endingY);
        path.moveTo(endingX, endingY);
        path.close();

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);
        animator.setDuration(1000);
        view.setVisibility(View.VISIBLE);
        animator.start();
    }

    public static void hideToRight(Context context, View view, int width, int height) {
        int startingX = width - view.getWidth() - context.getResources().getDimensionPixelSize(R.dimen.padding_large);
        int startingY = height - view.getHeight() - context.getResources().getDimensionPixelSize(R.dimen.fab_remove_margin_bottom);
        int endingX = width;
        int endingY = startingY;

        Path path = new Path();
        path.moveTo(startingX, startingY);
        path.lineTo(endingX, endingY);
        path.moveTo(endingX, endingY);
        path.close();

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);
        animator.setDuration(1000);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });
        animator.start();
    }

}
