package com.bignerdranch.android.nerdzoo.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Zoo {

    private final List<Animal> mAnimalList;

    @Inject
    public Zoo() {
        mAnimalList = Collections.unmodifiableList(Arrays.asList(
                new Animal(this),
                new Animal(this),
                new Animal(this)
        ));
    }

    public List<Animal> asList() {
        return mAnimalList;
    }

}