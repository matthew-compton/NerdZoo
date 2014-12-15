package com.bignerdranch.android.nerdzoo.model;

import java.io.Serializable;
import java.util.UUID;

public class Animal implements Serializable {

    private UUID mId;
    private int mTypeResourceId;
    private int mImageResourceId;

    public Animal(UUID id, int typeResourceId, int imageResourceId) {
        this.mId = id;
        this.mTypeResourceId = typeResourceId;
        this.mImageResourceId = imageResourceId;
    }

    public int getTypeResourceId() {
        return mTypeResourceId;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

}