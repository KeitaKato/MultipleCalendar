package com.example.eiga_.readingcalendar.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eiga_.readingcalendar.R;
import com.example.eiga_.readingcalendar.data.PlanData;
import com.example.eiga_.readingcalendar.data.PlansPickerData;
import com.example.eiga_.readingcalendar.databases.CalendarDBModel;
import com.example.eiga_.readingcalendar.databases.PresetPlanDBModel;
import com.example.eiga_.readingcalendar.databinding.ActivityAddMultiplePlansBinding;
import com.example.eiga_.readingcalendar.databinding.ActivityDeleteMultiplePlansBinding;
import com.example.eiga_.readingcalendar.views.adapters.MultiplePlansPickerAdapter;
import com.example.eiga_.readingcalendar.views.adapters.PlansListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.eiga_.readingcalendar.R.id.delete_button;

public class DeleteMultiplePlansActivity extends AppCompatActivity {
    private GridView multipleCalendarGridView;
    private MultiplePlansPickerAdapter mMultipleAdapter;
    private String month;
    private List<Date> days = new ArrayList<>();
    private PlansPickerData mPickerData = new PlansPickerData("日付を選択して絞り込む");
    private List<PlanData> plansListItems;
    private PresetPlanDBModel presetPlanDBModel;
    private PlansListAdapter plansListAdapter;
    private CalendarDBModel calendarDBModel;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_multiple_plans);
        // calendarDBModelを使用
        calendarDBModel = new CalendarDBModel(DeleteMultiplePlansActivity.this);

        ActivityDeleteMultiplePlansBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_delete_multiple_plans);
        binding.setPlansPicker(mPickerData);
        // 日付を選択するGridViewを用意。
        multipleCalendarGridView = findViewById(R.id.multipleCalendarGridView);
        mMultipleAdapter = new MultiplePlansPickerAdapter(this);
        multipleCalendarGridView.setAdapter(mMultipleAdapter);

        // 当月の月と日付を取得。
        month = mMultipleAdapter.getTitle();
        days = mMultipleAdapter.getDays();

        // GridViewに選択モードを設定
        multipleCalendarGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
        multipleCalendarGridView.setOnItemClickListener(new DeleteMultiplePlansActivity.GridViewItemListener());

        // リストビューを取得。
        listView = findViewById(R.id.plansListView);

        // リストビューにクリックイベント設定。
        listView.setOnItemClickListener(new DeleteMultiplePlansActivity.ListViewItemListener());
        // listViewに選択モードを設定。
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // リストにアダプターを設定
        plansListItems = calendarDBModel.searchGroupData("plan_title",null, null);
        plansListAdapter = new PlansListAdapter(DeleteMultiplePlansActivity.this, R.layout.deletelist_item, plansListItems);
        listView.setAdapter(plansListAdapter);
        View footer = (View)getLayoutInflater().inflate(R.layout.deletelist_footer,null);
        listView.addFooterView(footer);

        TextView cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new DeleteMultiplePlansActivity.cancelButtonListener());

        TextView delete_button = findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new DeleteMultiplePlansActivity.deleteButtonListener());
    }

    class GridViewItemListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//            View gridItemView = multipleCalendarGridView.getAdapter().getView(position, view, multipleCalendarGridView);

//            gridItemView.setBackgroundColor(Color.BLUE);

            // listViewの選択状態を初期化
            listView.clearChoices();
//          選択中の日付を取得。
            SparseBooleanArray checked = multipleCalendarGridView.getCheckedItemPositions();
            StringBuilder daysText = new StringBuilder();
            List<String> daysList = new ArrayList<>();

            SimpleDateFormat daysFormat = new SimpleDateFormat("d日", Locale.US);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            for(int i = 0; i <= days.size(); i++) {
                // 選択中の日付ならば、文字列に追加
                if(checked.get(i)){
                    String str = daysFormat.format(days.get(i));
                    daysText.append(str).append(",");
                    str = dateFormat.format(days.get(i));
                    daysList.add(str);
                }
            }

            if(daysText.length() > 0) {
                daysText.deleteCharAt(daysText.lastIndexOf(","));
                mPickerData.setDays(daysText.toString());
                plansListItems = calendarDBModel.searchGroupData("plan_title","plan_day",daysList);
                plansListAdapter.setItem(plansListItems);
            } else {
                mPickerData.setDays("対象日付を絞り込む");
                plansListItems = calendarDBModel.searchGroupData("plan_title",null, null);
                plansListAdapter.setItem(plansListItems);
            }
            plansListAdapter.notifyDataSetChanged();

        }
    }

    class ListViewItemListener implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Intent intent;
            if (view.getId() == R.id.readPresetButton) {

            }
        }
    }

    class cancelButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }

    }

    class deleteButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 選択中の日付を取得。
            SparseBooleanArray checked = multipleCalendarGridView.getCheckedItemPositions();
            List<String> checkedList = new ArrayList<>();
            for(int i = 0; i <= days.size(); i++) {

                // 選択中の日付ならば、文字列に追加
                if(checked.get(i)){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    String str = dateFormat.format(days.get(i));
                    checkedList.add(str);
                }
            }

            if (checkedList.size() == 0) {
            }
            finish();
        }
    }
}
