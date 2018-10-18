package com.example.eiga_.readingcalendar.databases;

import android.content.Context;
import android.database.Cursor;

import com.example.eiga_.readingcalendar.data.PlanData;

import java.util.ArrayList;
import java.util.List;

public class CalendarDBModel extends DBModelBase{

    private final String CALENDER_TABLE_NAME = "calenders";
    private final Context context;

    public CalendarDBModel(Context context) {
        super(context);
        this.context = context;
    }

    public PlanData searchData(String column, String keyword) {
        Cursor cursor = null;
        PlanData planData;
        try {
            //SQL文
            String sql = "SELECT * FROM " + CALENDER_TABLE_NAME + " WHERE " + column + "=" + keyword; // selectionArgsがなぜか使えない
            //SQL文実行
            cursor = db.rawQuery(sql, null);
            planData = readCursor(cursor);
            return planData;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public List<PlanData> searchGroupData(String groupColumn, String whereColumn, List<String> keywords) {
        Cursor cursor = null;
        List<PlanData> planDataList;
        // StringBuilderでsql文を作る
        StringBuilder sql = new StringBuilder("SELECT * FROM " + CALENDER_TABLE_NAME);
        if (whereColumn == null && keywords == null){
            sql.append(" GROUP BY ").append(groupColumn).append(" ;");
        } else if ( keywords != null && whereColumn != null){

            sql.append(" WHERE ").append(whereColumn).append( " IN (");

            for (String keyword : keywords) {
                sql.append(keyword).append(",");
            }
            sql.deleteCharAt(sql.lastIndexOf(","));
            sql.append(") GROUP BY ").append(groupColumn).append(" ;");

        }
        try {
            cursor = db.rawQuery(sql.toString(), null);
            planDataList = readCursorAll(cursor);
            return  planDataList;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Cursor getSearchDataCursor(String column, String keyword) {
        // SQL
        String sql = "SELECT * FROM " + CALENDER_TABLE_NAME + " WHERE " + column + " = " + "date('" + keyword + "');";
        // SQL実行
        Cursor cursor = db.rawQuery(sql, null);

        return cursor;
    }

    private PlanData readCursor(Cursor cursor) {
        //カーソル開始位置を先頭にする
        PlanData planData = new PlanData();
        // 各データを格納
        while (cursor.moveToNext()) {
            planData.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            planData.setTitle(cursor.getString(cursor.getColumnIndex("plan_title")));
            planData.setStartTime(cursor.getString(cursor.getColumnIndex("start_time")));
            planData.setEndTime(cursor.getString(cursor.getColumnIndex("end_time")));
            planData.setUseTime(String.valueOf(cursor.getInt(cursor.getColumnIndex("use_time"))));
            planData.setType(cursor.getString(cursor.getColumnIndex("plan_type")));
            planData.setIncome(String.valueOf(cursor.getInt(cursor.getColumnIndex("income"))));
            planData.setSpending(String.valueOf(cursor.getInt(cursor.getColumnIndex("spending"))));
            planData.setMemo(cursor.getString(cursor.getColumnIndex("memo")));
            planData.setPresetId(String.valueOf(cursor.getInt(cursor.getColumnIndex("preset_id"))));
        }
        return planData;
    }

    @Override
    List<PlanData> readCursorAll (Cursor cursor) {
        // 返すlistを生成。
        List<PlanData> planDataList = new ArrayList<>();
        while (cursor.moveToNext()) {
            // PlanDataオブジェクトを生成。
            PlanData planData = new PlanData();
            // 各データを格納
            planData.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            planData.setTitle(cursor.getString(cursor.getColumnIndex("plan_title")));
            planData.setStartTime(cursor.getString(cursor.getColumnIndex("start_time")));
            planData.setEndTime(cursor.getString(cursor.getColumnIndex("end_time")));
            planData.setUseTime(String.valueOf(cursor.getInt(cursor.getColumnIndex("use_time"))));
            planData.setType(cursor.getString(cursor.getColumnIndex("plan_type")));
            planData.setIncome(String.valueOf(cursor.getInt(cursor.getColumnIndex("income"))));
            planData.setSpending(String.valueOf(cursor.getInt(cursor.getColumnIndex("spending"))));
            planData.setMemo(cursor.getString(cursor.getColumnIndex("memo")));
            planData.setPresetId(String.valueOf(cursor.getInt(cursor.getColumnIndex("preset_id"))));
            // Listに追加
            planDataList.add(planData);
        }
        return planDataList;
    }

    public  void insertData(String day, String planTitle){

        PresetPlanDBModel PresetPlanDBModel = new PresetPlanDBModel(context);

        String sql;
        List<String> bindStr = new ArrayList<>();
            sql = "INSERT INTO " + CALENDER_TABLE_NAME + " (day, plan_title) values(?,?);";
            bindStr.add(day);
            bindStr.add(planTitle);


        super.executeSql(sql,bindStr.toArray(new String[bindStr.size()]));
    }

    public void insertData(String planDay, String planTitle, String planType, String startTime, String endTime, String useTime, String income, String spending,String memo, String presetId, String readingId){
        String sql = "INSERT INTO " + CALENDER_TABLE_NAME
                + " (plan_day, plan_title, plan_type, start_time, end_time, use_time, income, spending, memo, preset_id, reading_id) values(date('" + planDay + "'),?,?,?,?,?,?,?,?,?,?);";
        String[] bindStr = new String[]{
                planTitle,
                planType,
                startTime,
                endTime,
                useTime,
                income,
                spending,
                memo,
                presetId,
                readingId,
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

    public void deleteData(String column, List<String> keywords) {
        StringBuilder sql = new StringBuilder("DELETE FROM " + CALENDER_TABLE_NAME
                + " WHERE "+ column +" IN ("); // カラムにはselectionArgsは使えない？
        for (String keyword : keywords) {
            sql.append(keyword).append(",");
        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append(");");

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql.toString(), null);
            cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void deleteAndData(String column1, String column2, List<String> keywords1, List<String> keywords2) {
        StringBuilder sql = new StringBuilder("DELETE FROM " + CALENDER_TABLE_NAME
                + " WHERE " + column1 +" IN (");
        for (String keyword : keywords1) {
            sql.append(keyword).append(",");
        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append(") AND ").append(column2).append(" IN (");
        for (String keyword : keywords2) {
            sql.append(keyword).append(",");
        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append(");");

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql.toString(), null);
            cursor.moveToFirst();
            System.out.println(cursor.getCount());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
