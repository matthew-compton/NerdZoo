package com.bignerdranch.android.nerdzoo.util;

import android.os.Build;

public class BuildUtils {

    public static boolean isLollipopEnabled() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

}