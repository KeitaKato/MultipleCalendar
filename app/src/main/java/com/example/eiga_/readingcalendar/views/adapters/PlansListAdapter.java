package com.example.eiga_.readingcalendar.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eiga_.readingcalendar.R;
import com.example.eiga_.readingcalendar.activities.AddMultiplePlansActivity;
import com.example.eiga_.readingcalendar.activities.PresetListActivity;
import com.example.eiga_.readingcalendar.data.PlanData;

import java.util.List;

import static com.example.eiga_.readingcalendar.activities.AddMultiplePlansActivity.PRESETLIST_REQ_CODE;

public class PlansListAdapter extends BaseAdapter {
    private int mResource;
    private List<PlanData> mItems;
    private LayoutInflater mInflater;
    private Context mContext;

    static class ViewHolder {
        Button readButton;
        TextView id;
        EditText title;
        TextView startTime;
        TextView endTime;
        TextView useTime;
        TextView type;
        TextView income;
        TextView spending;
        TextView memo;

    }

    public PlansListAdapter(@NonNull Context context, int resource, List<PlanData> items) {

        mContext = context;
        mResource = resource;
        mItems = items;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = mInflater.inflate(mResource, parent, false);

            holder = new ViewHolder();
            // 各要素を設定。
            holder.readButton = convertView.findViewById(R.id.readPresetButton);
            holder.id = convertView.findViewById(R.id.planId);
            holder.title = convertView.findViewById(R.id.titleEdit);
            holder.startTime = convertView.findViewById(R.id.startTimeText);
            holder.endTime = convertView.findViewById(R.id.endTimeText);
            holder.useTime = convertView.findViewById(R.id.useTimeText);
            holder.type = convertView.findViewById(R.id.typeText);
            holder.income = convertView.findViewById(R.id.incomePriceText);
            holder.spending = convertView.findViewById(R.id.spendingPriceText);
            holder. memo = convertView.findViewById(R.id.memoText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // リストビューに表示する要素を取得。
        PlanData item = mItems.get(position);
        holder.id.setText(String.valueOf(position + 1));
        holder.title.setText(item.getTitle());
        holder.startTime.setText(item.getStartTime());
        holder.endTime.setText(item.getEndTime());
        holder.useTime.setText(String.valueOf(item.getUseTime()));
        holder.type.setText(item.getType());
        holder.income.setText(String.valueOf(item.getIncome()));
        holder.spending.setText(String.valueOf(item.getSpending()));
        holder.memo.setText(item.getMemo());

        // ボタンにクリックリスナーを設定。
        holder.readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((ListView) parent).performItemClick(view, position, R.id.readPresetButton);
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



}
