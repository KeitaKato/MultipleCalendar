package com.example.eiga_.readingcalendar.utils;

import android.content.Context;

public class PxDpUtil {

    public static int dpToPx(int dp, Context context){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)((float)dp * scale + 0.5f);
    }

    public static int pxToDp(int px, Context context){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)((float)px / scale + 0.5f);
    }

}
