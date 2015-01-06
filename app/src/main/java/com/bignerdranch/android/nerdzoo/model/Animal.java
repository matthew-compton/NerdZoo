package com.bignerdranch.android.nerdzoo.model;

import java.io.Serializable;
import java.util.UUID;

public class Animal implements Serializable {

    private UUID mId;
    private int mNameResourceId;
    private int mDescriptionResourceId;
    private int mImageResourceId;

    private boolean mIsFavorite;

    public Animal(UUID id, int nameResourceId, int descriptionResourceId, int imageResourceId) {
        this.mId = id;
        this.mNameResourceId = nameResourceId;
        this.mDescriptionResourceId = descriptionResourceId;
        this.mImageResourceId = imageResourceId;
        mIsFavorite = false;
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

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        mIsFavorite = isFavorite;
    }

}