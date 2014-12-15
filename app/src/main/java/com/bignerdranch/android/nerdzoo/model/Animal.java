package com.bignerdranch.android.nerdzoo.model;

public class Animal {

    private Zoo mZoo;
    private int mTypeResourceId;
    private int mImageResourceId;

    public Animal(Zoo zoo, int typeResourceId, int imageResourceId) {
        this.mZoo = zoo;
        this.mTypeResourceId = typeResourceId;
        this.mImageResourceId = imageResourceId;
    }

}
