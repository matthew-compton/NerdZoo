package com.bignerdranch.android.nerdzoo.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.bignerdranch.android.nerdzoo.BaseApplication;
import com.bignerdranch.android.nerdzoo.R;

import butterknife.ButterKnife;
import timber.log.Timber;

public abstract class BaseNormalActivity extends BaseActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_normal);
        BaseApplication.get(BaseNormalActivity.this).inject(this);
        ButterKnife.inject(this);

        setupToolbar();
        setupOverflowButton();
        setupOverviewScreen();
        setupInitialFragment();
    }

    protected void setupInitialFragment() {
        Fragment fragment = createFragment();
        if (fragment != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.activity_base_normal_container, fragment);
            ft.commit();
        } else {
            Timber.e("Error in creating fragment.");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}