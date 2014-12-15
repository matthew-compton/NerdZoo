package com.bignerdranch.android.nerdzoo;

import com.bignerdranch.android.nerdzoo.controller.AnimalActivity;
import com.bignerdranch.android.nerdzoo.controller.AnimalFragment;
import com.bignerdranch.android.nerdzoo.controller.ZooActivity;
import com.bignerdranch.android.nerdzoo.controller.ZooFragment;
import com.bignerdranch.android.nerdzoo.model.Zoo;

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
public class BaseModule {

    private final BaseApplication mApplication;

    public BaseModule(BaseApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    public Zoo provideZoo() {
        return new Zoo();
    }

}