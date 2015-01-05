package com.bignerdranch.android.nerdzoo.anim;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ZooItemAnimator extends BaseItemAnimator {

    private AnimationDirection mAnimationDirection;

    public ZooItemAnimator(RecyclerView recyclerView) {
        super(recyclerView);
        mAnimationDirection = AnimationDirection.RIGHT;
    }

    public enum AnimationDirection {
        LEFT,
        RIGHT;

        public int getSign() {
            return this == RIGHT ? 1 : -1;
        }
    }

    public void setAnimationDirection(AnimationDirection animationDirection) {
        mAnimationDirection = animationDirection;
    }

    protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;
        ViewCompat.animate(view).cancel();
        ViewCompat.animate(view).setDuration(getRemoveDuration()).
                translationX(mAnimationDirection.getSign() * mRecyclerView.getWidth()).setListener(new VpaListenerAdapter() {
            @Override
            public void onAnimationEnd(View view) {
                ViewCompat.setTranslationX(view, mAnimationDirection.getSign() * mRecyclerView.getWidth());
                dispatchRemoveFinished(holder);
                mRemoveAnimations.remove(holder);
                dispatchFinishedWhenDone();
            }
        }).start();
        mRemoveAnimations.add(holder);
    }

    @Override
    protected void prepareAnimateAdd(RecyclerView.ViewHolder holder) {
        ViewCompat.setTranslationX(holder.itemView, +mRecyclerView.getWidth());
    }

    protected void animateAddImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;

        ViewCompat.animate(view).cancel();
        ViewCompat.animate(view).translationX(0)
                .setDuration(getAddDuration()).
                setListener(new VpaListenerAdapter() {
                    @Override
                    public void onAnimationCancel(View view) {
                        ViewCompat.setTranslationX(view, 0);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        dispatchAddFinished(holder);
                        mAddAnimations.remove(holder);
                        dispatchFinishedWhenDone();
                    }
                }).start();
        mAddAnimations.add(holder);
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromLeft, int fromTop, int toLeft, int toTop) {
        return false;
    }

}