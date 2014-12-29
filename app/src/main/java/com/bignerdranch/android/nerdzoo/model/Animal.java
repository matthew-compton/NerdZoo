package com.bignerdranch.android.nerdzoo.model;

import java.io.Serializable;
import java.util.UUID;

public class Animal implements Serializable {

    private UUID mId;
    private int mNameResourceId;
    private int mDescriptionResourceId;
    private int mImageResourceId;

    public Animal(UUID id, int nameResourceId, int descriptionResourceId, int imageResourceId) {
        this.mId = id;
        this.mNameResourceId = nameResourceId;
        this.mDescriptionResourceId = descriptionResourceId;
        this.mImageResourceId = imageResourceId;
    }

    public UUID getId() {
        return mId;
    }

    public int getNameResourceId() {
        return mNameResourceId;
    }

    public int getDescriptionResourceId() {
        return mDescriptionResourceId;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

}