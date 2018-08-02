package com.example.eiga_.readingcalendar.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.eiga_.readingcalendar.R;

public class OCRPreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_preview);

        //TabLayoutを作る
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("読み込みデータ"));
        tabLayout.addTab(tabLayout.newTab().setText("元画像"));

        //Tabごとのfragmentを作る
        FragmentManager manager = getSupportFragmentManager();
        ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(manager) {
            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public Fragment getItem(int position) {
                return null;
            }
        };
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
