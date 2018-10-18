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
import com.example.eiga_.readingcalendar.data.PlanData;

import java.util.List;

public class PlansListAdapter extends BaseAdapter {
    private int mResource;
    private List<PlanData> mItems;
    private LayoutInflater mInflater;
    private Context mContext;

    static class ViewHolder {
        Button readButton;
        TextView id;
        TextView title;

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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // リストビューに表示する要素を取得。
        PlanData item = mItems.get(position);
        holder.id.setText(String.valueOf(position + 1));
        holder.title.setText(item.getTitle());

        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position,-1);
            }
        });

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

    public void setItem(List<PlanData> items) {
        mItems = items;
    }


}
