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
        if (cursor.moveToNext()) {
            planData.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            planData.setTitle(cursor.getString(cursor.getColumnIndex("plan_title")));
            planData.setStartTime(cursor.getString(cursor.getColumnIndex("start_time")));
            planData.setEndTime(cursor.getString(cursor.getColumnIndex("end_time")));
            planData.setUseTime(cursor.getInt(cursor.getColumnIndex("use_time")));
            planData.setNotice(cursor.getInt(cursor.getColumnIndex("notice")));
            planData.setType(cursor.getInt(cursor.getColumnIndex("plan_type")));
            planData.setReview(cursor.getString(cursor.getColumnIndex("review")));
            planData.setIncome(String.valueOf(cursor.getInt(cursor.getColumnIndex("income"))));
            planData.setSpending(String.valueOf(cursor.getInt(cursor.getColumnIndex("spending"))));
            planData.setPlace(cursor.getString(cursor.getColumnIndex("place")));
            planData.setTool(cursor.getString(cursor.getColumnIndex("tool")));
            planData.setEndCheck(cursor.getInt(cursor.getColumnIndex("end_check")) != 0);
            planData.setMemo(cursor.getString(cursor.getColumnIndex("memo")));
            planData.setPresetId(String.valueOf(cursor.getInt(cursor.getColumnIndex("preset_id"))));
        }
        return planData;
    }

    private List<PlanData> readCursorAll(Cursor cursor) {
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
            planData.setUseTime(cursor.getInt(cursor.getColumnIndex("use_time")));
            planData.setNotice(cursor.getInt(cursor.getColumnIndex("notice")));
            planData.setType(cursor.getInt(cursor.getColumnIndex("plan_type")));
            planData.setReview(cursor.getString(cursor.getColumnIndex("review")));
            planData.setIncome(String.valueOf(cursor.getInt(cursor.getColumnIndex("income"))));
            planData.setSpending(String.valueOf(cursor.getInt(cursor.getColumnIndex("spending"))));
            planData.setPlace(cursor.getString(cursor.getColumnIndex("place")));
            planData.setTool(cursor.getString(cursor.getColumnIndex("tool")));
            planData.setEndCheck(cursor.getInt(cursor.getColumnIndex("end_check")) != 0);
            planData.setMemo(cursor.getString(cursor.getColumnIndex("memo")));
            planData.setPresetId(String.valueOf(cursor.getInt(cursor.getColumnIndex("preset_id"))));
            // Listに追加
            planDataList.add(planData);
        }
        return planDataList;
    }

    public void insertData(String planDay, String planTitle, int planType, String startTime, String endTime, int useTime, int notice,
                           String review, String income, String spending, String place, String tool, boolean endCheck,
                           String memo, String presetId, String readingId){
        String sql = "INSERT INTO " + CALENDER_TABLE_NAME
                + " (plan_day, plan_title, plan_type, start_time, end_time, use_time, notice,"
                + " review, income, spending, place, tool, end_check, memo,"
                + " preset_id, reading_id, created_at, updated_at)"
                + " values(date('" + planDay + "'),"
                + "'" + planTitle + "',"
                + planType + ","
                + "'" + startTime + "',"
                + "'" + endTime + "',"
                + useTime + ","
                + notice + ","
                + "'" + review + "',"
                + "'" + income + "',"
                + "'" + spending + "',"
                + "'" + place + "',"
                + "'" + tool + "',"
                + String.valueOf(endCheck ? 1 : 0) + ","
                + "'" + memo + "',"
                + "'" + presetId + "',"
                + "'" + readingId + "',"
                + "datetime('now', 'utc')"
                + ",datetime('now', 'utc'))"
                +";";

        super.executeSql(sql,null);

    }

    public void updateData(String column, String keyword, String day, String planTitle,
                           int planType, String startTime, String endTime, int useTime, int notice,
                           String review, String income, String spending, String place, String tool, boolean endCheck,
                           String memo, String presetId, String readingId) {
        String sql = "UPDATE " + CALENDER_TABLE_NAME
                + " SET day = + (date('" + day + "'),"
                + " plan_title = '" + planTitle + "',"
                + " plan_type = " + planType + ","
                + " start_time = '" + startTime + "',"
                + " end_time = '" + endTime + "',"
                + " use_time = " + useTime + ","
                + " notice = " + notice + ","
                + " review = '" + review + "',"
                + " income = '" + income + "',"
                + " spending = '" + spending + "',"
                + " place = '" + place + "',"
                + " tool = '" + tool + "',"
                + " endCheck = " + String.valueOf(endCheck ? 1 : 0) + ","
                + " memo = '" + memo + "',"
                + " myset_plan_id = '" + presetId + "',"
                + " reading_data_id = '" + readingId + "'"
                + "WHERE " + column + " = " + keyword + ";";
        super.executeSql(sql, null);
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
