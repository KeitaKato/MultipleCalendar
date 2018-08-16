package com.example.eiga_.readingcalendar.views.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.eiga_.readingcalendar.fragments.CameraPreviewFragment;
import com.example.eiga_.readingcalendar.fragments.OCRPreviewFragment;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class PreviewFragmentPagerAdapter extends FragmentPagerAdapter {

    private String imageUriString;
    private String OCRData;

    public PreviewFragmentPagerAdapter(FragmentManager manager, String imageUriString) {
        super(manager);
        this.imageUriString = imageUriString;
        this.OCRData = getOCRData(imageUriString);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return OCRPreviewFragment.newInstance();
            case 1:
                return CameraPreviewFragment.newInstance(imageUriString);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public  CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "読み取りデータ";
            case 1:
                return "元画像";

        }
        return null;
    }

    private String getOCRData(String imageUriString) {
        // UriをInputStreamを生成。
        InputStream stream = context.getContentResolver().openInputStream(Uri.parse(imageUriString));
        Bitmap bitmap = BitmapFactory.decodeStream(new BufferedInputStream(stream));
    }
}
