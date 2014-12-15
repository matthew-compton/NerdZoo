package com.bignerdranch.android.nerdzoo;

import com.bignerdranch.android.nerdroll.controller.DieFragment;
import com.bignerdranch.android.nerdroll.controller.DieListFragment;
import com.bignerdranch.android.nerdroll.model.DieList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                BaseActivity.class,
                BaseFragment.class,
                ZooActivity.class,
                ZooFragment.class,
                AnimalActivity.class,
                AnimalFragment.class
        },
        complete = true)
public class MainApplicationModule {

    private final MainApplication mApplication;

    public MainApplicationModule(MainApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    public Zoo provideZoo() {
        return new Zoo();
    }

    @Provides
    @Singleton
    public MainApplicationPreference providePreference() {
        return new MainApplicationPreference();
    }

}
