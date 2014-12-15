package com.bignerdranch.android.nerdzoo.controller;

import android.support.v4.app.Fragment;

import com.bignerdranch.android.nerdzoo.BaseActivity;

public class ZooActivity extends BaseActivity {

    @Override
    protected Fragment createFragment() {
        return new ZooFragment();
    }

}