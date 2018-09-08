package com.example.eiga_.readingcalendar.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.eiga_.readingcalendar.R;
import com.example.eiga_.readingcalendar.databases.PresetPlanDBModel;

public class PresetListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preset_list);

        PresetPlanDBModel presetPlanDBModel = new PresetPlanDBModel(PresetListActivity.this);

    }
}
