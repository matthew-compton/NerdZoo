package com.bignerdranch.android.nerdzoo.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bignerdranch.android.nerdzoo.BaseApplication;
import com.bignerdranch.android.nerdzoo.BaseFragment;
import com.bignerdranch.android.nerdzoo.R;
import com.bignerdranch.android.nerdzoo.model.Animal;
import com.bignerdranch.android.nerdzoo.model.Zoo;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ZooFragment extends BaseFragment {

    public static final String EXTRA_ANIMAL = "EXTRA_ANIMAL";

    @InjectView(R.id.fragment_zoo_recycler_view) RecyclerView mRecyclerView;
    @Inject Zoo mZoo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.get(getActivity()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zoo, container, false);
        ButterKnife.inject(this, view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new ZooAdapter());

        return view;
    }

    public class ZooHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @InjectView(R.id.card_animal_image) public ImageView mImageView;

        private Animal mAnimal;

        public ZooHolder(View view) {
            super(view);
            if (!view.isInEditMode()) {
                ButterKnife.inject(this, view);
            }
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mAnimal != null) {
                Intent intent = new Intent(getActivity(), AnimalActivity.class);
                intent.putExtra(EXTRA_ANIMAL, mAnimal);
                startActivity(intent);
            }
        }

        public void bindCrime(Animal animal) {
            mAnimal = animal;
            Picasso.with(getActivity()).load(mAnimal.getImageResourceId()).into(mImageView);
        }

    }

    public class ZooAdapter extends RecyclerView.Adapter<ZooHolder> {

        @Override
        public ZooHolder onCreateViewHolder(ViewGroup parent, int position) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_animal, parent, false);
            return new ZooHolder(view);
        }

        @Override
        public void onBindViewHolder(ZooHolder holder, int position) {
            Animal animal = mZoo.asList().get(position);
            holder.bindCrime(animal);
        }

        @Override
        public int getItemCount() {
            return mZoo.asList().size();
        }

    }

}