package com.example.eiga_.readingcalendar.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.eiga_.readingcalendar.R;
import com.example.eiga_.readingcalendar.data.PlanData;
import com.example.eiga_.readingcalendar.data.PlansPickerData;
import com.example.eiga_.readingcalendar.databases.PresetPlanDBModel;
import com.example.eiga_.readingcalendar.databinding.ActivityAddMultiplePlansBinding;
import com.example.eiga_.readingcalendar.views.adapters.MultiplePlansPickerAdapter;
import com.example.eiga_.readingcalendar.views.adapters.PlansListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddMultiplePlansActivity extends AppCompatActivity {

    public static final int PRESETLIST_REQ_CODE = 4000;
    private GridView multipleCalendarGridView;
    private MultiplePlansPickerAdapter mMultipleAdapter;
    private String month;
    private List<Date> days = new ArrayList<>();
    private PlansPickerData mPickerData = new PlansPickerData("日付を選択");
    private ArrayList<PlanData> listItems;
    private PresetPlanDBModel presetPlanDBModel;
    private PlansListAdapter plansListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_multiple_plans);

        presetPlanDBModel = new PresetPlanDBModel(AddMultiplePlansActivity.this);

        ActivityAddMultiplePlansBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_multiple_plans);
        binding.setPlansPicker(mPickerData);
        // 日付を選択するGridViewを用意。
        multipleCalendarGridView = findViewById(R.id.multipleCalendarGridView);
        mMultipleAdapter = new MultiplePlansPickerAdapter(this);
        multipleCalendarGridView.setAdapter(mMultipleAdapter);

        // 当月の月と日付を取得。
        month = mMultipleAdapter.getTitle();
        days = mMultipleAdapter.getDays();

        multipleCalendarGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
        multipleCalendarGridView.setOnItemClickListener(new GridViewItemListener());

        // リストビューを取得。
        ListView listView = findViewById(R.id.plansListView);


        // リストビューにクリックイベント設定。
        listView.setOnItemClickListener(new ListViewItemListener());

        listItems = new ArrayList<>();
        PlanData planData = new PlanData();
        listItems.add(planData);
        plansListAdapter = new PlansListAdapter(AddMultiplePlansActivity.this, R.layout.planslist_item,listItems);
        listView.setAdapter(plansListAdapter);

    }

    class GridViewItemListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//            View gridItemView = multipleCalendarGridView.getAdapter().getView(position, view, multipleCalendarGridView);

//            gridItemView.setBackgroundColor(Color.BLUE);

//          選択中の日付を取得。
            SparseBooleanArray checked = multipleCalendarGridView.getCheckedItemPositions();
            StringBuilder daysText = new StringBuilder();

            for(int i = 0; i <= days.size(); i++) {

                // 選択中の日付ならば、文字列に追加
                if(checked.get(i)){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("d日", Locale.US);
                    String str = dateFormat.format(days.get(i));
                    daysText.append(str).append(",");
                }
            }

            if(daysText.length() > 0) {
                mPickerData.setDays(daysText.substring(0, daysText.length() - 1));
            } else {
                mPickerData.setDays("日付を選択");
            }

       }
    }

    class ListViewItemListener implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
         switch (view.getId()) {
                case R.id.readPresetButton:
                    // presetListActivityを呼び出す。
                    Intent presetListIntent = new Intent(AddMultiplePlansActivity.this, PresetListActivity.class);
                    presetListIntent.putExtra("ITEM_POSITION",position);
                    startActivityForResult(presetListIntent, PRESETLIST_REQ_CODE);
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == PRESETLIST_REQ_CODE && resultCode == RESULT_OK) {
            int position = intent.getIntExtra("ITEM_POSITION", -1);
            PlanData planData = presetPlanDBModel.searchData("_id",intent.getStringExtra("PRESET_PLAN_ID"));
            listItems.set(position,planData);
            if (position == listItems.size() -1) {
                planData = new PlanData();
                listItems.add(position,planData);
            }
            plansListAdapter.notifyDataSetChanged();
        }
    }
}
