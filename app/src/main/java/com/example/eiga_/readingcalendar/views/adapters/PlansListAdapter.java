package com.example.eiga_.readingcalendar.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import com.example.eiga_.readingcalendar.data.PlanData;

import java.util.List;

public class PlansListAdapter extends ArrayAdapter<PlanData> {

    private int mResource;
    private List<PlanData> mItems;
    private LayoutInflater mInflater;

    /**
     *
     * @param context
     * @param resource
     */

    public PlansListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
