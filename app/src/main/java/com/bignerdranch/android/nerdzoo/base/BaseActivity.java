package com.bignerdranch.android.nerdzoo.base;

import android.app.ActivityManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bignerdranch.android.nerdzoo.R;

import java.lang.reflect.Field;

import butterknife.InjectView;
import timber.log.Timber;

public abstract class BaseActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar) public Toolbar mToolbar;

    protected abstract void setupInitialFragment();

    protected void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    protected void setupOverflowButton() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (NoSuchFieldException e) {
            Timber.e("Error with displaying overflow menu.", e);
        } catch (IllegalAccessException e) {
            Timber.e("Error with displaying overflow menu.", e);
        }
    }

    protected void setupOverviewScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = getTheme();
            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
            int color = typedValue.data;

            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_overview);
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, bm, color);

            setTaskDescription(td);
            bm.recycle();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_base, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_base_about:
                new MaterialDialog.Builder(this)
                        .title(R.string.dialog_about_title)
                        .content(R.string.dialog_about_content)
                        .positiveText(android.R.string.ok).
                        show();
                return true;
            default:
                return super.

                        onOptionsItemSelected(item);
        }
    }

}