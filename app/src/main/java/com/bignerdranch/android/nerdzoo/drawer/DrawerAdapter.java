package com.bignerdranch.android.nerdzoo.drawer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.nerdzoo.R;

import java.util.ArrayList;

public class DrawerAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<DrawerItem> mDrawerItems;
    private int mSelection;

    public DrawerAdapter(Context context, ArrayList<DrawerItem> drawerItems) {
        this.mContext = context;
        this.mDrawerItems = drawerItems;
        this.mSelection = 0;
    }

    @Override
    public int getCount() {
        return mDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_drawer, null);
        }
        DrawerItem drawerItem = mDrawerItems.get(position);

        ImageView icon = (ImageView) convertView.findViewById(R.id.list_item_drawer_icon);
        TextView title = (TextView) convertView.findViewById(R.id.list_item_drawer_title);

        icon.setImageResource(drawerItem.getIcon());
        title.setText(drawerItem.getTitle());
        title.setTextColor((position == mSelection) ? mContext.getResources().getColor(R.color.text_toolbar_primary) : mContext.getResources().getColor(R.color.text_primary));

        return convertView;
    }

    public void setSelection(int position) {
        mSelection = position;
    }

}