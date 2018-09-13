package com.example.eiga_.readingcalendar.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eiga_.readingcalendar.R;
import com.example.eiga_.readingcalendar.databases.PresetPlanDBModel;

public class AddPresetActivity extends AppCompatActivity {

    private EditText presetTitle;
    private TextView startTimeText;
    private TextView endTimeText;
    private TextView useTimeText;
    private TextView typeText;
    private TextView incomePriceText;
    private TextView spendingPriceText;
    private EditText memoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_preset);

        // UIオブジェクトを取得。
        presetTitle = findViewById(R.id.presetTitle);
        startTimeText = findViewById(R.id.startTimeText);
        endTimeText = findViewById(R.id.endTimeText);
        useTimeText = findViewById(R.id.useTimeText);
        typeText = findViewById(R.id.typeText);
        incomePriceText = findViewById(R.id.incomePriceText);
        spendingPriceText = findViewById(R.id.spendingPriceText);
        memoText = findViewById(R.id.memoText);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("プリセットプラン作成");

        TextView cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(cancelButtonListener);

        TextView storage_button = findViewById(R.id.storage_button);
        storage_button.setOnClickListener(storageButtonListener);

    }
    View.OnClickListener cancelButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }

    };

    View.OnClickListener storageButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PresetPlanDBModel presetPlanDBModel = new PresetPlanDBModel(AddPresetActivity.this);
            // データベースに登録するデータを取得。
            String title = presetTitle.getText().toString();
            String startTime = startTimeText.getText().toString();
            String endTime = endTimeText.getText().toString();
            String useTime = useTimeText.getText().toString();
            String type = typeText.getText().toString();
            String incomePrice = incomePriceText.getText().toString();
            String spendingPrice = spendingPriceText.getText().toString();
            String memo = memoText.getText().toString();
            presetPlanDBModel.insertData(title,startTime,endTime,useTime,type,incomePrice,spendingPrice,memo);

            Intent intent = new Intent(AddPresetActivity.this, PresetListActivity.class);
            startActivity(intent);
        }
    };
}
