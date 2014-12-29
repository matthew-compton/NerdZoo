package com.bignerdranch.android.nerdzoo.controller;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.bignerdranch.android.nerdzoo.BaseApplication;
import com.bignerdranch.android.nerdzoo.R;
import com.bignerdranch.android.nerdzoo.drawer.DrawerAdapter;
import com.bignerdranch.android.nerdzoo.drawer.DrawerItem;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;

public abstract class BaseDrawerActivity extends BaseActivity {

    @InjectView(R.id.drawer_layout) public DrawerLayout mDrawerLayout;
    @InjectView(R.id.drawer_list) public ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] mNavMenuTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_drawer);
        BaseApplication.get(BaseDrawerActivity.this).inject(this);
        ButterKnife.inject(this);

        setupToolbar();
        setupOverflowButton();
        setupOverviewScreen();
        setupNavigationDrawer();
        setupInitialFragment();
    }

    private void setupNavigationDrawer() {
        mNavMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        TypedArray navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        ArrayList<DrawerItem> drawerItems = new ArrayList<>();
        for (int i = 0; i < mNavMenuTitles.length; i++) {
            drawerItems.add(new DrawerItem(mNavMenuTitles[i], navMenuIcons.getResourceId(i, 0)));
        }
        mDrawerList.setAdapter(new DrawerAdapter(getApplicationContext(), drawerItems));
        mDrawerList.setOnItemClickListener((parent, view, position, id) -> updateFragment(position));
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void setupInitialFragment() {
        updateFragment(0);
    }

    private void updateFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ZooFragment();
                break;
            case 1:
                fragment = new AboutFragment();
                break;
            default:
                fragment = new ZooFragment();
                break;
        }

        setTitle(mNavMenuTitles[position]);
        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
        mDrawerLayout.closeDrawer(mDrawerList);

        if (fragment != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.activity_base_drawer_container, fragment);
            ft.commit();
        } else {
            Timber.e("Error in creating fragment.");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        mDrawerToggle.onConfigurationChanged(configuration);
    }

}