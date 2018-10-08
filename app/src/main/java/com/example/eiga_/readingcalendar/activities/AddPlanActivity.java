package com.example.eiga_.readingcalendar.activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.eiga_.readingcalendar.R;
import com.example.eiga_.readingcalendar.data.PlanData;
import com.example.eiga_.readingcalendar.databases.PresetPlanDBModel;

public class AddPlanActivity extends FragmentActivity implements TimePickerDialog.OnTimeSetListener {

    private LinearLayout startTimeLayout;
    private LinearLayout endTimeLayout;
    private LinearLayout useTimeLayout;
    private LinearLayout typeLayout;
    private LinearLayout incomePriceLayout;
    private LinearLayout spendingPriceLayout;

    private EditText planTitle;
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
        setContentView(R.layout.activity_add_plan);

        // UIオブジェクトを取得。
        startTimeLayout = findViewById(R.id.startTimeLayout);
        endTimeLayout = findViewById(R.id.endTimeLayout);
        useTimeLayout = findViewById(R.id.useTimeLayout);
        typeLayout = findViewById(R.id.typeLayout);
        incomePriceLayout = findViewById(R.id.incomePriceLayout);
        spendingPriceLayout = findViewById(R.id.spendingPriceLayout);

        planTitle = findViewById(R.id.planTitle);
        startTimeText = findViewById(R.id.startTimeText);
        endTimeText = findViewById(R.id.endTimeText);
        useTimeText = findViewById(R.id.useTimeText);
        typeText = findViewById(R.id.typeText);
        incomePriceText = findViewById(R.id.incomePriceText);
        spendingPriceText = findViewById(R.id.spendingPriceText);
        memoText = findViewById(R.id.memoText);

        startTimeLayout.setOnClickListener(new StartTimeListener());
        endTimeLayout.setOnClickListener(new EndTimeListener());
        useTimeLayout.setOnClickListener(new UseTimeListener());
        typeLayout.setOnClickListener(new TypeListener());
        incomePriceLayout.setOnClickListener(new IncomePriceListener());
        spendingPriceLayout.setOnClickListener(new SpendingPriceListener());

        TextView toolbarTitle = findViewById(R.id.toolbar_title);

        Intent intent = getIntent();
        TextView storage_button = findViewById(R.id.storage_button);
        // activityの動作を決める（。。。くそ実装後で直したい
        if (intent.getStringExtra("ACTIVITY_MODE").equals("preset")) {
            toolbarTitle.setText("プリセットプラン作成");
            storage_button.setOnClickListener(new AddPresetButtonListener());
        } else if(intent.getStringExtra("ACTIVITY_MODE").equals("multiple")){
            PlanData planData = (PlanData)intent.getSerializableExtra(PlanData.SERIAL_NAME);
            planTitle.setText(planData.getTitle());
            startTimeText.setText(planData.getStartTime());
            endTimeText.setText(planData.getEndTime());
            useTimeText.setText(planData.getUseTime());
            typeText.setText(planData.getType());
            incomePriceText.setText(planData.getIncome());
            spendingPriceText.setText(planData.getSpending());
            memoText.setText(planData.getMemo());
            storage_button.setOnClickListener(new AddMultipleButtonListener());
        }

        TextView cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new cancelButtonListener());

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }

    class cancelButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
            overridePendingTransition(0, 0);
        }

    }

    class AddPresetButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            PresetPlanDBModel presetPlanDBModel = new PresetPlanDBModel(AddPlanActivity.this);
            // データベースに登録するデータを取得。
            String title = planTitle.getText().toString();
            String startTime = startTimeText.getText().toString();
            String endTime = endTimeText.getText().toString();
            String useTime = useTimeText.getText().toString();
            String type = typeText.getText().toString();
            String incomePrice = incomePriceText.getText().toString();
            String spendingPrice = spendingPriceText.getText().toString();
            String memo = memoText.getText().toString();
            presetPlanDBModel.insertData(title,startTime,endTime,useTime,type,incomePrice,spendingPrice,memo);

            Intent intent = new Intent(AddPlanActivity.this, PresetListActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
        }
    }

    class AddMultipleButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            PlanData planData = new PlanData();
            planData.setTitle(planTitle.getText().toString());
            planData.setStartTime(startTimeText.getText().toString());
            planData.setEndTime(endTimeText.getText().toString());
            planData.setUseTime(useTimeText.getText().toString());
            planData.setType(typeText.getText().toString());
            planData.setIncome(incomePriceText.getText().toString());
            planData.setSpending(spendingPriceText.getText().toString());
            planData.setMemo(memoText.getText().toString());
            intent.putExtra(PlanData.SERIAL_NAME, planData);
            setResult(RESULT_OK, intent);
            finish();
            overridePendingTransition(0, 0);
        }
    }

    private class StartTimeListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    private class EndTimeListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    private class UseTimeListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    private class TypeListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    private class IncomePriceListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    private class SpendingPriceListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }
}
