package com.example.eiga_.readingcalendar.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

        // GridView
        multipleCalendarGridView = findViewById(R.id.multipleCalendarGridView);
        mMultipleAdapter = new MultiplePlansPickerAdapter(this);
        multipleCalendarGridView.setAdapter(mMultipleAdapter);

        multipleCalendarGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);

        multipleCalendarGridView.setOnItemClickListener(new GridViewItemListener());

    }

    class GridViewItemListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//            View gridItemView = multipleCalendarGridView.getAdapter().getView(position, view, multipleCalendarGridView);

//            gridItemView.setBackgroundColor(Color.BLUE);
        }
    }
}
