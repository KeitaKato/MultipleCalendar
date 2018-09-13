package com.example.eiga_.readingcalendar.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.eiga_.readingcalendar.R;
import com.example.eiga_.readingcalendar.views.adapters.MultiplePlansPickerAdapter;

public class AddMultiplePlansActivity extends AppCompatActivity {

    private GridView multipleCalendarGridView;
    private MultiplePlansPickerAdapter mMultipleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_multiple_plans);

        multipleCalendarGridView = findViewById(R.id.multipleCalendarGridView);
        mMultipleAdapter = new MultiplePlansPickerAdapter(this);
        multipleCalendarGridView.setAdapter(mMultipleAdapter);
    }
}
