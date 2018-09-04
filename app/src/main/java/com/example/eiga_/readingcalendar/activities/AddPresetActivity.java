package com.example.eiga_.readingcalendar.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.eiga_.readingcalendar.R;

public class AddPresetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_preset);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("プリセットプラン作成");

    }
}
