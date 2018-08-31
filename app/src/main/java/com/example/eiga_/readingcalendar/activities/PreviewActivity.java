package com.example.eiga_.readingcalendar.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.eiga_.readingcalendar.R;
import com.example.eiga_.readingcalendar.views.adapters.PreviewFragmentPagerAdapter;

public class PreviewActivity extends AppCompatActivity {

    private String imageUriString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        // インテントを取得。
        Intent intent = getIntent();
        imageUriString = intent.getStringExtra("IMAGE_URI");
        setViews();

    }

    private void setViews() {
        FragmentManager manager = getSupportFragmentManager();
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        PreviewFragmentPagerAdapter adapter = new PreviewFragmentPagerAdapter(manager,imageUriString);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

}
