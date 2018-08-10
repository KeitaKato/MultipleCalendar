package com.example.eiga_.readingcalendar.views.adapters;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.eiga_.readingcalendar.fragments.CameraPreviewFragment;
import com.example.eiga_.readingcalendar.fragments.OCRPreviewFragment;

public class PreviewFragmentPagerAdapter extends FragmentPagerAdapter {

    private String imageUriString;

    public PreviewFragmentPagerAdapter(FragmentManager manager, String imageUriString) {
        super(manager);
        this.imageUriString = imageUriString;
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
}
