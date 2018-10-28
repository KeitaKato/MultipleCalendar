package com.example.eiga_.readingcalendar.databases;

import android.content.Context;
import android.database.Cursor;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;

import com.example.eiga_.readingcalendar.data.PlanData;

import java.util.ArrayList;
import java.util.List;

public class PresetPlanDBModel extends DBModelBase {

    private final String PRESET_PLAN_TABLE_NAME = "preset_plans";

    public PresetPlanDBModel(Context context) {
        super(context);
    }

    public PlanData searchData(String column, String keyword) {
        Cursor cursor = null;
        PlanData planData;
        try {
            //SQL文
            String sql = "SELECT * FROM " + PRESET_PLAN_TABLE_NAME + " WHERE " + column + "=" + keyword; // sessionArgsがなぜか使えない
            //SQL文実行
            cursor = db.rawQuery(sql, null);
            planData = readCursor(cursor);
            return planData;
        } finally {
            if( cursor != null) {
                cursor.close();
            }
        }
    }

    public List<PlanData> searchDataAll() {
        Cursor cursor = null;
        try {
            //SQL
            String sql = "SELECT * FROM " + PRESET_PLAN_TABLE_NAME;
            //SQL実行
            cursor = db.rawQuery(sql, null);
            return readCursorAll(cursor);
        } finally {
            if(cursor != null) {
                cursor.close();
            }
        }
    }

    public Cursor getSearchDataCursor() {
            // SQL
            String sql = "SELECT * FROM " + PRESET_PLAN_TABLE_NAME;
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
            planData.setPresetId(String.valueOf(cursor.getInt(cursor.getColumnIndex("_id"))));
        }
        return planData;
    }

    @Override
    List<PlanData> readCursorAll (Cursor cursor) {
        // カーソル位置を先頭に
        cursor.moveToFirst();
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
                planData.setPresetId(String.valueOf(cursor.getInt(cursor.getColumnIndex("_id"))));
                // Listに追加
                planDataList.add(planData);
            }
        return planDataList;
    }

    public void insertData(String planTitle, String planType, String startTime, String endTime, String useTime, String income, String spending, String memoText) {
        String sql = "INSERT INTO " + PRESET_PLAN_TABLE_NAME
                + "(plan_title, plan_type, start_time,end_time,use_time,income,spending,memo, created_at, updated_at) values(?,?,?,?,?,?,?,?, datetime('now', 'utc'),datetime('now', 'utc'));";
        String[] bindStr = new String[] {
                planTitle,
                planType,
                startTime,
                endTime,
                useTime,
                income,
                spending,
                memoText
        };
        super.executeSql(sql,bindStr);
    }

    public void updateData(String column, String keyword, String planTitle, String planType, String startTime, String endTime, String useTime, String income, String spending, String memoText) {
        String sql = "UPDATE " + PRESET_PLAN_TABLE_NAME
                + " SET plan_title = ?, plan_type = ?, time_zone = ?, income = ?, spending = ? "
                + "WHERE ? = ? ;";
        String[] bindStr = new String[] {
                planTitle,
                planType,
                startTime,
                endTime,
                useTime,
                income,
                spending,
                memoText,
                column,
                keyword
        };
        super.executeSql(sql,bindStr);
    }

    void deleteData(String column, String keyword) {
        String sql = "DELETE FROM " + PRESET_PLAN_TABLE_NAME
                + " WHERE ? = ?;";
        String[] bindStr = new String[]{
                column,
                keyword
        };
        super.executeSql(sql,bindStr);
    }
}
