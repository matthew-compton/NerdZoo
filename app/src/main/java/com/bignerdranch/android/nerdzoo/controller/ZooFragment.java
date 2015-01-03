package com.bignerdranch.android.nerdzoo.controller;

import android.content.Intent;
import android.graphics.Outline;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bignerdranch.android.nerdzoo.BaseApplication;
import com.bignerdranch.android.nerdzoo.R;
import com.bignerdranch.android.nerdzoo.anim.ZooItemAnimator;
import com.bignerdranch.android.nerdzoo.model.Animal;
import com.bignerdranch.android.nerdzoo.model.Zoo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import timber.log.Timber;

public class ZooFragment extends Fragment {

    public static final String EXTRA_ANIMAL_ID = "EXTRA_ANIMAL_ID";

    @InjectView(R.id.fragment_zoo_recycler_view) RecyclerView mRecyclerView;
    @InjectView(R.id.fragment_zoo_fab) ImageButton mFloatingActionButton;

    @Inject Zoo mZoo;

    private ZooItemAnimator mZooItemAnimator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.get(getActivity()).inject(this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zoo, container, false);
        ButterKnife.inject(this, view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new ZooAdapter());

        mZooItemAnimator = new ZooItemAnimator(mRecyclerView);
        mRecyclerView.setItemAnimator(mZooItemAnimator);

        setupFloatingActionButton();

        return view;
    }

    private void setupFloatingActionButton() {
        mFloatingActionButton.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int diameter = getResources().getDimensionPixelSize(R.dimen.fab_diameter);
                outline.setOval(0, 0, diameter, diameter);
            }
        });
        mFloatingActionButton.setClipToOutline(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_zoo, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_zoo_clear:
                clearAnimals();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fragment_zoo_fab)
    public void onClickFAB() {
        addAnimal();
    }

    private void addAnimal() {
        mZoo.add();
        mRecyclerView.getAdapter().notifyItemInserted(mZoo.size());
    }

    private void removeAnimal(int position) {
        mZoo.remove(position);
        mRecyclerView.getAdapter().notifyItemRemoved(position);
    }

    private void clearAnimals() {
        mZoo.clear();
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    public class ZooHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @InjectView(R.id.list_item_animal_image) public ImageView mImageView;
        @InjectView(R.id.list_item_animal_progress) public ProgressBar mProgressBar;
        @InjectView(R.id.list_item_animal_title) public TextView mTitleTextView;
        @InjectView(R.id.list_item_animal_description) public TextView mDescriptionTextView;

        private Animal mAnimal;
        private boolean mIsLoading;

        public ZooHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
            setupOnClickListener(view);
            setupOnGestureListener(view);
        }

        private void setupOnClickListener(View view) {
            view.setOnClickListener(ZooHolder.this);
        }

        private void setupOnGestureListener(View view) {
            GestureDetector gestureDetector = new GestureDetector(new ZooOnGestureListener());
            View.OnTouchListener gestureListener = (v, event) -> gestureDetector.onTouchEvent(event);
            view.setOnTouchListener(gestureListener);
        }

        public void bindCrime(Animal animal) {
            mAnimal = animal;
            mIsLoading = true;
            mTitleTextView.setText(mAnimal.getNameResourceId());
            mDescriptionTextView.setText(mAnimal.getDescriptionResourceId());
            Picasso.with(getActivity())
                    .load(mAnimal.getImageResourceId())
                    .into(mImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            mIsLoading = false;
                            updateUI();
                        }

                        @Override
                        public void onError() {
                            mIsLoading = false;
                            updateUI();
                        }
                    });
            updateUI();
        }

        private void updateUI() {
            if (mIsLoading) {
                mImageView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
            } else {
                mProgressBar.setVisibility(View.GONE);
                mImageView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            if (mAnimal != null) {
                Intent intent = new Intent(getActivity(), AnimalActivity.class);
                intent.putExtra(EXTRA_ANIMAL_ID, mAnimal.getId());
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        mImageView,
                        getString(R.string.transition_animal_image)
                );
                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
            }
        }

        private class ZooOnGestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_MIN_DISTANCE = 120;
            private static final int SWIPE_MAX_OFF_PATH = 250;
            private static final int SWIPE_THRESHOLD_VELOCITY = 200;

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Timber.i("Left Swipe");
                    mZooItemAnimator.setSwipeDirection(ZooItemAnimator.SwipeDirection.LEFT);
                    removeAnimal(mZoo.findPositionById(mAnimal.getId()));
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Timber.i("Right Swipe");
                    mZooItemAnimator.setSwipeDirection(ZooItemAnimator.SwipeDirection.RIGHT);
                    removeAnimal(mZoo.findPositionById(mAnimal.getId()));
                }
                return false;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        }

    }

    public class ZooAdapter extends RecyclerView.Adapter<ZooHolder> {

        @Override
        public ZooHolder onCreateViewHolder(ViewGroup parent, int position) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_animal, parent, false);
            return new ZooHolder(view);
        }

        @Override
        public void onBindViewHolder(ZooHolder holder, int position) {
            Animal animal = mZoo.get(position);
            holder.bindCrime(animal);
        }

        @Override
        public int getItemCount() {
            return mZoo.size();
        }

    }

}