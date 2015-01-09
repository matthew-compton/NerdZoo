package com.bignerdranch.android.nerdzoo.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.bignerdranch.android.nerdzoo.R;
import com.bignerdranch.android.nerdzoo.util.BuildUtils;

public class PathAnimator {

    public static void showFromRight(Context context, View view, int width, int height) {
        if (BuildUtils.isLollipopEnabled()) {
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
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setDuration(1000);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setClickable(true);
                }
            });
            view.setVisibility(View.VISIBLE);
            animator.start();
        } else {
            view.setClickable(true);
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hideToRight(Context context, View view, int width, int height) {
        if (BuildUtils.isLollipopEnabled()) {
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
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setDuration(1000);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.INVISIBLE);
                }
            });
            view.setClickable(false);
            animator.start();
        } else {
            view.setClickable(false);
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void curveInFromRight(Context context, View view, int width, int height) {
        if (BuildUtils.isLollipopEnabled()) {
            int startingX = width;
            int startingY = 2 * (view.getHeight() + context.getResources().getDimensionPixelSize(R.dimen.padding_normal));
            int endingX = width - view.getWidth() - context.getResources().getDimensionPixelSize(R.dimen.padding_normal);
            int endingY = context.getResources().getDimensionPixelSize(R.dimen.padding_normal);

            int x1 = width - view.getWidth() - context.getResources().getDimensionPixelSize(R.dimen.padding_normal);
            int y1 = 0;
            int x2 = width + view.getWidth() + context.getResources().getDimensionPixelSize(R.dimen.padding_normal);
            int y2 = 2 * context.getResources().getDimensionPixelSize(R.dimen.padding_normal);
            RectF rect = new RectF(x1, y1, x2, y2);

            Path path = new Path();
            path.moveTo(startingX, startingY);
            path.addArc(rect, 90, 90);
            path.moveTo(endingX, endingY);
            path.close();

            ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setDuration(1000);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setClickable(true);
                }
            });
            view.setVisibility(View.VISIBLE);
            animator.start();
        } else {
            view.setClickable(true);
            view.setVisibility(View.VISIBLE);
        }
    }

}
