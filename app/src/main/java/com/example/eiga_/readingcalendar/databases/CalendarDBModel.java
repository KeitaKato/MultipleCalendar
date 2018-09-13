package com.example.eiga_.readingcalendar.databases;

import android.content.Context;
import android.database.Cursor;

import com.example.eiga_.readingcalendar.data.PlanData;

import java.util.ArrayList;
import java.util.List;

public class CalendarDBModel extends DBModelBase{

    final String CALENDER_TABLE_NAME = "calenders";
    private final Context context;

    CalendarDBModel(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public String searchData(String column, String keyword) {
        Cursor cursor = null;
        try {
            //SQL文
            String sql = "SELECT * FROM " + CALENDER_TABLE_NAME + " WHERE ? = ?";
            //SQL文実行
            String[] bindStr = new String[]{column, keyword};

            cursor = db.rawQuery(sql, bindStr);
            return readCursor(cursor);
        } finally {
            if( cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    String readCursor(Cursor cursor) {
        //カーソル開始位置を先頭にする
        cursor.moveToFirst();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= cursor.getCount(); i++) {
            //SQL文の結果から、必要な値を取り出す。
            sb.append(cursor.getString(1));//処理
            cursor.moveToNext();
        }
        return sb.toString();
    }

    @Override
    List<PlanData> readCursorAll(Cursor cursor) {
        return null;
    }

    public  void insertData(String day, String planTitle){

        PresetPlanDBModel PresetPlanDBModel = new PresetPlanDBModel(context);
        String mysetPlanId = PresetPlanDBModel.searchData("plan_title", planTitle);

        String sql;
        List<String> bindStr = new ArrayList<>();
        if (mysetPlanId != null){
            sql = "INSERT INTO " + CALENDER_TABLE_NAME + " (day, plan_title, myset_plan_id) values(?,?,?);";
            bindStr.add(day);
            bindStr.add(planTitle);
            bindStr.add(mysetPlanId);
        } else {
            sql = "INSERT INTO " + CALENDER_TABLE_NAME + " (day, plan_title) values(?,?);";
            bindStr.add(day);
            bindStr.add(planTitle);
        }


        super.executeSql(sql,bindStr.toArray(new String[bindStr.size()]));
    }

    public void insertData(String day, String planTitle, String planType, String timeZone,String income, String spending){
        String sql = "INSERT INTO " + CALENDER_TABLE_NAME + " (day, plan_title, plan_type, time_zone, income, spending) values(?,?,?,?,?,?);";
        String[] bindStr = new String[]{
                day,
                planTitle,
                planType,
                timeZone,
                income,
                spending
        };

        super.executeSql(sql,bindStr);

    }

    public void updateData(String column, String keyword, String day, String planTitle, String planType, String timeZone,String income, String spending) {
        String sql = "UPDATE " + CALENDER_TABLE_NAME
                + " SET day = ?, plan_title = ?, plan_type = ?, time_zone = ?, income = ?, spending = ?, myset_plan_id = ?, reading_data_id = ? "
                + "WHERE ? = ? ;";
        String[] bindStr = new String[]{
                day,
                planTitle,
                planType,
                timeZone,
                income,
                spending,
                column,
                keyword
        };

        super.executeSql(sql, bindStr);
    }

    @Override
    public void deleteData(String column, String keyword) {
        String sql = "DELETE FROM " + CALENDER_TABLE_NAME
                + " WHERE ? = ?;";
        String[] bindStr = new String[]{
                column,
                keyword
        };

        super.executeSql(sql, bindStr);
    }
}
