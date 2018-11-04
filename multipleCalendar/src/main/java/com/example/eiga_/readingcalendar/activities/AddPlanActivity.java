package com.example.eiga_.readingcalendar.activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.eiga_.readingcalendar.R;
import com.example.eiga_.readingcalendar.data.PlanData;
import com.example.eiga_.readingcalendar.databases.PresetPlanDBModel;
import com.example.eiga_.readingcalendar.fragments.TimePick;

import java.util.Locale;

public class AddPlanActivity extends FragmentActivity implements TimePickerDialog.OnTimeSetListener {

    private LinearLayout startTimeLayout;
    private LinearLayout endTimeLayout;
    private LinearLayout useTimeLayout;
    private LinearLayout typeLayout;
    private LinearLayout reviewLayout;
    private LinearLayout incomePriceLayout;
    private LinearLayout spendingPriceLayout;
    private LinearLayout placeLayout;
    private LinearLayout toolLayout;
    private LinearLayout checkLayout;

    private EditText planTitle;
    private TextView startTimeText;
    private TextView endTimeText;
    private Spinner useTimeSpinner;
    private Spinner typeSpinner;
    private TextView incomePriceText;
    private TextView spendingPriceText;
    private EditText memoText;

    private String selectTimeText;
    private Spinner reviewSpinner;
    private TextView placeText;
    private TextView toolText;
    private Switch checkSwitch;
    private Spinner noticeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

        // UIオブジェクトを取得。
        startTimeLayout = findViewById(R.id.startTimeLayout);
        endTimeLayout = findViewById(R.id.endTimeLayout);
        useTimeLayout = findViewById(R.id.useTimeLayout);
        typeLayout = findViewById(R.id.typeLayout);
        reviewLayout = findViewById(R.id.reviewLayout);
        incomePriceLayout = findViewById(R.id.incomePriceLayout);
        spendingPriceLayout = findViewById(R.id.spendingPriceLayout);
        placeLayout = findViewById(R.id.placeLayout);
        toolLayout = findViewById(R.id.toolLayout);
        checkLayout = findViewById(R.id.checkLayout);

        planTitle = findViewById(R.id.planTitle);
        startTimeText = findViewById(R.id.startTimeText);
        endTimeText = findViewById(R.id.endTimeText);
        useTimeSpinner = findViewById(R.id.useTimeSpinner);
        noticeSpinner = findViewById(R.id.noticeSpinner);
        typeSpinner = findViewById(R.id.typeSpinner);
        reviewSpinner = findViewById(R.id.reviewSpinner);
        incomePriceText = findViewById(R.id.incomePriceText);
        spendingPriceText = findViewById(R.id.spendingPriceText);
        placeText = findViewById(R.id.placeText);
        toolText = findViewById(R.id.toolText);
        checkSwitch = findViewById(R.id.checkSwitch);
        memoText = findViewById(R.id.memoText);

        startTimeLayout.setOnClickListener(new StartTimeListener());
        endTimeLayout.setOnClickListener(new EndTimeListener());
        useTimeLayout.setOnClickListener(new UseTimeListener());
        typeLayout.setOnClickListener(new TypeListener());
        reviewLayout.setOnClickListener(new ReviewListener());
        incomePriceLayout.setOnClickListener(new IncomePriceListener());
        spendingPriceLayout.setOnClickListener(new SpendingPriceListener());
        placeLayout.setOnClickListener(new PlaceListener());
        toolLayout.setOnClickListener(new ToolListener());
        checkLayout.setOnClickListener(new CheckListener());

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
//            useTimeSpinner.setText(planData.getUseTime());
//            typeSpinner.set(planData.getType());
            incomePriceText.setText(planData.getIncome());
            spendingPriceText.setText(planData.getSpending());
            memoText.setText(planData.getMemo());
            storage_button.setOnClickListener(new AddMultipleButtonListener());
        }

        TextView cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new cancelButtonListener());

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String str = String.format(Locale.US, "%d時%d分", hourOfDay, minute);
        switch (selectTimeText){
            case "start":
                startTimeText.setText(str);
                break;
            case "end":
                endTimeText.setText(str);
                break;
        }

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePick();
        newFragment.show(getSupportFragmentManager(), "timePicker");

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
            // 開始時間
            String startTimeObject = startTimeText.getText().toString();
            String startTime = startTimeObject.equals("開始時間を設定") ? null : startTimeObject;
            // 終了時間
            String endTime = endTimeText.getText().toString();
            // 所要時間
            Integer useTimeObject = (Integer)useTimeSpinner.getSelectedItem();
            int useTime = useTimeObject == null ? 0 : useTimeObject;
            // 通知
            Integer noticeObject = (Integer)noticeSpinner.getSelectedItem();
            int notice = noticeObject == null ? 0 : noticeObject;
            // カテゴリー
            Integer typeObject = (Integer)typeSpinner.getSelectedItem();
            int type = typeObject == null ? 0 : typeObject;
            String review = (String)reviewSpinner.getSelectedItem();
            String incomePrice = incomePriceText.getText().toString();
            String spendingPrice = spendingPriceText.getText().toString();
            String place = placeText.getText().toString();
            String tool = toolText.getText().toString();
            boolean check = checkSwitch.isChecked();
            String memo = memoText.getText().toString();
            presetPlanDBModel.insertData(title, type, startTime,endTime, useTime, notice, review, incomePrice,spendingPrice, place, tool, check, memo);

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
            planData.setUseTime((int)useTimeSpinner.getSelectedItem());
            planData.setType((int)typeSpinner.getSelectedItem());
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
            selectTimeText = "start";
            showTimePickerDialog(view);
        }
    }

    private class EndTimeListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            selectTimeText = "end";
            showTimePickerDialog(view);
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

    private class ReviewListener implements View.OnClickListener {
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

    private class PlaceListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    private class ToolListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    private class CheckListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }
}
