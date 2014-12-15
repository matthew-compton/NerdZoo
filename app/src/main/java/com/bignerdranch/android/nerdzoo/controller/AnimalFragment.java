package com.bignerdranch.android.nerdzoo.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bignerdranch.android.nerdzoo.BaseApplication;
import com.bignerdranch.android.nerdzoo.R;
import com.bignerdranch.android.nerdzoo.model.Animal;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AnimalFragment extends Fragment {

    @InjectView(R.id.fragment_animal_image) ImageView mImageView;

    private Animal mAnimal;

    public static AnimalFragment newInstance(Animal animal) {
        Bundle args = new Bundle();
        args.putSerializable(ZooFragment.EXTRA_ANIMAL, animal);

        AnimalFragment fragment = new AnimalFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.get(getActivity()).inject(this);
        mAnimal = (Animal) getArguments().getSerializable(ZooFragment.EXTRA_ANIMAL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animal, container, false);
        ButterKnife.inject(this, view);
        setHasOptionsMenu(true);

        mImageView.setImageResource(mAnimal.getImageResourceId());

        return view;
    }

}