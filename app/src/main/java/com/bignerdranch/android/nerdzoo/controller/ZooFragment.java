package com.bignerdranch.android.nerdzoo.controller;

import android.content.Intent;
import android.graphics.Outline;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bignerdranch.android.nerdzoo.BaseApplication;
import com.bignerdranch.android.nerdzoo.R;
import com.bignerdranch.android.nerdzoo.model.Animal;
import com.bignerdranch.android.nerdzoo.model.Zoo;
import com.bignerdranch.android.nerdzoo.library.SlideInOutRightItemAnimator;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ZooFragment extends Fragment {

    public static final String EXTRA_ANIMAL_ID = "EXTRA_ANIMAL_ID";

    @InjectView(R.id.fragment_zoo_recycler_view) RecyclerView mRecyclerView;
    @InjectView(R.id.fragment_zoo_fab) ImageButton mFloatingActionButton;

    @Inject Zoo mZoo;

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
        mRecyclerView.setItemAnimator(new SlideInOutRightItemAnimator(mRecyclerView));

        mFloatingActionButton.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int diameter = getResources().getDimensionPixelSize(R.dimen.fab_diameter);
                outline.setOval(0, 0, diameter, diameter);
            }
        });
        mFloatingActionButton.setClipToOutline(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView.getAdapter().notifyDataSetChanged();
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
            view.setOnClickListener(this);
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