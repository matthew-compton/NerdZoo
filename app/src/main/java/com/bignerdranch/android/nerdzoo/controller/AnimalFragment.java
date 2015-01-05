package com.bignerdranch.android.nerdzoo.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.nerdzoo.BaseApplication;
import com.bignerdranch.android.nerdzoo.R;
import com.bignerdranch.android.nerdzoo.model.Animal;
import com.bignerdranch.android.nerdzoo.model.Zoo;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AnimalFragment extends Fragment {

    @InjectView(R.id.fragment_animal_image) public ImageView mImageView;
    @InjectView(R.id.fragment_animal_description) public TextView mDescriptionTextView;

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
        getActivity().setTitle(mAnimal.getNameResourceId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animal, container, false);
        ButterKnife.inject(this, view);
        setHasOptionsMenu(true);

        mDescriptionTextView.setText(mAnimal.getDescriptionResourceId());
        loadImage();

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
                loadImage();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadImage() {
        Picasso.with(getActivity())
                .load(mAnimal.getImageResourceId())
                .into(mImageView);
    }

}