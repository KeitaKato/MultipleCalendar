package com.example.eiga_.readingcalendar.activities;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.eiga_.readingcalendar.R;
import com.example.eiga_.readingcalendar.data.PlanData;
import com.example.eiga_.readingcalendar.databases.PresetPlanDBModel;

import java.util.List;

public class PresetListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preset_list);

        PresetPlanDBModel presetPlanDBModel = new PresetPlanDBModel(PresetListActivity.this);

        // 表示するカラム名
        String[] from = {"_id", "plan_title"};
        // バインドするViewリソース
        int[] to = {R.id.preset_item_id, R.id.preset_item_title};

        Cursor cursor = presetPlanDBModel.getSearchDataCursor();
        // adapterを設定
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.preset_list_item, cursor, from, to, 0);

        ListView listView = findViewById(R.id.presetList);
        listView.setAdapter(adapter);

    }
}
