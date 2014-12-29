package com.bignerdranch.android.nerdzoo.controller;

import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class AnimalActivity extends BaseNormalActivity {

    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        UUID id = (UUID) intent.getSerializableExtra(ZooFragment.EXTRA_ANIMAL_ID);
        return AnimalFragment.newInstance(id);
    }

}