package com.bignerdranch.android.nerdzoo.controller;

import android.support.v4.app.Fragment;

public class ZooActivity extends BaseDrawerActivity {

    @Override
    protected Fragment createFragment() {
        return new ZooFragment();
    }

}