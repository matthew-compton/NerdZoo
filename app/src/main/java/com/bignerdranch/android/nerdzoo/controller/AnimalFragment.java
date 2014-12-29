package com.bignerdranch.android.nerdzoo.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bignerdranch.android.nerdzoo.BaseApplication;
import com.bignerdranch.android.nerdzoo.R;
import com.bignerdranch.android.nerdzoo.model.Animal;
import com.bignerdranch.android.nerdzoo.model.Zoo;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AnimalFragment extends Fragment {

    public static final String EXTRA_ANIMAL_ID = "EXTRA_ANIMAL_ID";
    public static final String EXTRA_ANIMAL_IS_REMOVED = "EXTRA_ANIMAL_IS_REMOVED";

    @InjectView(R.id.fragment_animal_image) ImageView mImageView;
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

        mImageView.setImageResource(mAnimal.getImageResourceId());

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
            case R.id.menu_animal_remove:
                Intent data = new Intent();
                data.putExtra(EXTRA_ANIMAL_ID, mAnimal.getId());
                data.putExtra(EXTRA_ANIMAL_IS_REMOVED, true);
                getActivity().setResult(Activity.RESULT_OK, data);
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}