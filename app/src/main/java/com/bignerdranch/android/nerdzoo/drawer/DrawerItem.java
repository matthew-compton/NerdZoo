package com.bignerdranch.android.nerdzoo.drawer;

public class DrawerItem {

    private String mTitle;
    private int mIcon;

    public DrawerItem(String title, int icon) {
        mTitle = title;
        mIcon = icon;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

}