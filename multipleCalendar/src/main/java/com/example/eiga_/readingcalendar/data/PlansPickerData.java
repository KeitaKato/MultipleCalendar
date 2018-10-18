package com.example.eiga_.readingcalendar.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.eiga_.readingcalendar.BR;


public class PlansPickerData extends BaseObservable{
    // 選択中の日付
    private String days;

    public PlansPickerData(String days){
        this.days = days;
    }

    @Bindable
    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;

        notifyPropertyChanged(BR.days);
    }
}
