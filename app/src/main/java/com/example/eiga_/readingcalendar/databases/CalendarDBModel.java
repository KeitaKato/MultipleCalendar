package com.example.eiga_.readingcalendar.databases;

import android.content.Context;
import android.database.Cursor;

public class CalendarDBModel extends DBModelBase{

    final String CALENDER_TABLE_NAME = "calenders";
    CalendarDBModel(Context context) {
        super(context);
    }

    @Override
    public String searchData(String column, String keyword){
            //SQL文
            String sql = "SELECT * FROM " + CALENDER_TABLE_NAME + " WHERE ? = ?";
            //SQL文実行
            String[] bindStr = new String[]{column, keyword};
        Cursor cursor = super.executeSearchSql(sql,bindStr);
        return readCursor(cursor);
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

    public void insertData(String day, String planTitle, String planType, String timeZone,String income, String spending, String mysetPlanId, String readingDataId){
        String sql = "INSERT INTO " + CALENDER_TABLE_NAME + " (day, plan_title, plan_type, time_zone, income, spending, myset_plan_id, reading_data_id) values(?,?,?,?,?,?,?,?);";
        String[] bindStr = new String[]{
                day,
                planTitle,
                planType,
                timeZone,
                income,
                spending,
                mysetPlanId,
                readingDataId
        };

        super.executeInsertSql(sql,bindStr);

    }
}
