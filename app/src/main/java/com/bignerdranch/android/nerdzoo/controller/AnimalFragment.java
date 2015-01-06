package com.bignerdranch.android.nerdzoo.controller;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bignerdranch.android.nerdzoo.BaseApplication;
import com.bignerdranch.android.nerdzoo.R;
import com.bignerdranch.android.nerdzoo.anim.PathAnimator;
import com.bignerdranch.android.nerdzoo.model.Animal;
import com.bignerdranch.android.nerdzoo.model.Zoo;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AnimalFragment extends Fragment {

    @InjectView(R.id.fragment_animal_layout) public RelativeLayout mLayout;
    @InjectView(R.id.fragment_animal_image) public ImageView mImageView;
    @InjectView(R.id.fragment_animal_description) public TextView mDescriptionTextView;
    @InjectView(R.id.fragment_animal_heart) public ImageButton mHeartImageButton;

    @Inject Zoo mZoo;

    private Animal mAnimal;

    public static AnimalFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(ZooFragment.EXTRA_ANIMAL_ID, id);

        AnimalFragment fragment = new AnimalFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.get(getActivity()).inject(this);
        setHasOptionsMenu(true);

        UUID id = (UUID) getArguments().getSerializable(ZooFragment.EXTRA_ANIMAL_ID);
        mAnimal = mZoo.findAnimalById(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animal, container, false);
        ButterKnife.inject(this, view);
        setHasOptionsMenu(true);

        setupLayoutListener();
        updateUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_animal, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_base_refresh:
                updateUI();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fragment_animal_heart)
    public void onClickHeartButton() {
        mAnimal.setFavorite(!mAnimal.isFavorite());
        startHeartAnimation();
    }

    private void setupLayoutListener() {
        mLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                PathAnimator.curveInFromRight(getActivity(), mHeartImageButton, mLayout.getWidth(), mLayout.getHeight());
            }
        });
    }

    private void updateUI() {
        getActivity().setTitle(mAnimal.getNameResourceId());
        mDescriptionTextView.setText(mAnimal.getDescriptionResourceId());
        mHeartImageButton.setBackgroundResource(mAnimal.isFavorite() ? R.drawable.animation_heart_emptying : R.drawable.animation_heart_filling);
        Picasso.with(getActivity())
                .load(mAnimal.getImageResourceId())
                .into(mImageView);
    }

    private void startHeartAnimation() {
        mHeartImageButton.setClickable(false);
        AnimationDrawable heartAnimation = (AnimationDrawable) mHeartImageButton.getBackground();
        heartAnimation.start();
        int time = heartAnimation.getDuration(0) * heartAnimation.getNumberOfFrames();
        new Handler().postDelayed(() -> {
            mHeartImageButton.setClickable(true);
            updateUI();
        }, time);
    }

}