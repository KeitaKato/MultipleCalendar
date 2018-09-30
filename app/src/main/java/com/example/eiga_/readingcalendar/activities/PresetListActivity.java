package com.example.eiga_.readingcalendar.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.eiga_.readingcalendar.R;
import com.example.eiga_.readingcalendar.data.PlanData;
import com.example.eiga_.readingcalendar.databases.PresetPlanDBModel;

import java.util.List;
import java.util.Objects;

public class PresetListActivity extends AppCompatActivity {

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preset_list);

        PresetPlanDBModel presetPlanDBModel = new PresetPlanDBModel(PresetListActivity.this);

        // 表示するカラム名
        String[] from = {"plan_title"};
        // バインドするViewリソース
        int[] to = {R.id.preset_item_title};

        cursor = presetPlanDBModel.getSearchDataCursor();
        // adapterを設定
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.preset_list_item, cursor, from, to, 0);

        ListView listView = findViewById(R.id.presetList);
        listView.setAdapter(adapter);

        // AddMultiplePlansActivityから呼ばれたら結果を返すアイテムクリックリスナーを設定。
        Intent intent = getIntent();
        int position = intent.getIntExtra("ITEM_POSITION", -1);
        if (position != -1) {
            listView.setOnItemClickListener(new ItemClickResultListener());
        }

    }

    class ItemClickResultListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = getIntent();
            intent.putExtra("PRESET_PLAN_ID", cursor.getString(cursor.getColumnIndex("_id")));
            setResult(RESULT_OK, intent);
            finish();
        }
    }


}
