package com.bignerdranch.android.nerdzoo;

import android.app.Application;
import android.content.Context;

import dagger.ObjectGraph;
import timber.log.Timber;

public class BaseApplication extends Application {

    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        mObjectGraph = ObjectGraph.create(new BaseModule(this));
        Timber.plant(new Timber.DebugTree());
    }

    public static BaseApplication get(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    public final void inject(Object object) {
        mObjectGraph.inject(object);
    }

}
