package com.bignerdranch.android.nerdzoo.controller;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bignerdranch.android.nerdzoo.BaseActivity;
import com.bignerdranch.android.nerdzoo.model.Animal;

public class AnimalActivity extends BaseActivity{

    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        Animal animal = (Animal) intent.getSerializableExtra(ZooFragment.EXTRA_ANIMAL);
        return AnimalFragment.newInstance(animal);
    }

}
