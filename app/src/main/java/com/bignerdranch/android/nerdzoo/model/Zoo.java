package com.bignerdranch.android.nerdzoo.model;

import com.bignerdranch.android.nerdzoo.BuildConfig;
import com.bignerdranch.android.nerdzoo.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Zoo {

    private final List<Animal> mAnimalList;

    @Inject
    public Zoo() {
        if (BuildConfig.DEBUG) {
            mAnimalList = setupDebugAnimalList();
        } else {
            mAnimalList = setupAnimalList();
        }
    }

    private List<Animal> setupDebugAnimalList() {
        return Collections.unmodifiableList(Arrays.asList(
                new Animal(UUID.randomUUID(), R.string.animal_lion, R.string.animal_lion_description, R.drawable.animal_lion),
                new Animal(UUID.randomUUID(), R.string.animal_tiger, R.string.animal_tiger_description, R.drawable.animal_tiger),
                new Animal(UUID.randomUUID(), R.string.animal_cheetah, R.string.animal_cheetah_description, R.drawable.animal_cheetah),
                new Animal(UUID.randomUUID(), R.string.animal_leopard, R.string.animal_leopard_description, R.drawable.animal_leopard),
                new Animal(UUID.randomUUID(), R.string.animal_lynx, R.string.animal_lynx_description, R.drawable.animal_lynx),
                new Animal(UUID.randomUUID(), R.string.animal_cat, R.string.animal_cat_description, R.drawable.animal_cat)
        ));
    }

    private List<Animal> setupAnimalList() {
        // In a production app, we would implement web-service fetching and population
        return null;
    }

    public List<Animal> asList() {
        return mAnimalList;
    }

}