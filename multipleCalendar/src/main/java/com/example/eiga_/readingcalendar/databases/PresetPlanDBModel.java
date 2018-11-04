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
            planData.setPresetId(String.valueOf(cursor.getInt(cursor.getColumnIndex("_id"))));
        }
        return planData;
    }

    private List<PlanData> readCursorAll(Cursor cursor) {
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
                planData.setPresetId(String.valueOf(cursor.getInt(cursor.getColumnIndex("_id"))));
                // Listに追加
                planDataList.add(planData);
            }
        return planDataList;
    }

    public void insertData(String planTitle, int planType, String startTime, String endTime, int useTime, int notice,
                           String review, String income, String spending, String place, String tool, boolean endCheck,
                           String memo){
        String sql = "INSERT INTO " + PRESET_PLAN_TABLE_NAME
                + " (plan_title, plan_type, start_time, end_time, use_time, notice,"
                + " review, income, spending, place, tool, end_check, memo,"
                + " created_at, updated_at)"
                + " values('" + planTitle + "',"
                + "" + planType + ","
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
                + "datetime('now', 'utc')"
                + ",datetime('now', 'utc'))"
                +";";

        super.executeSql(sql,null);

    }

    public void updateData(String column, String keyword, String planTitle,
                           int planType, String startTime, String endTime, int useTime, int notice,
                           String review, String income, String spending, String place, String tool, boolean endCheck,
                           String memo) {
        String sql = "UPDATE " + PRESET_PLAN_TABLE_NAME
                + " SET plan_title = '" + planTitle + "',"
                + " plan_type = " + planType + ","
                + " start_time = '" + startTime + "',"
                + " end_time = '" + endTime + "',"
                + " use_time = " + useTime + ","
                + " notice = '" + notice + "',"
                + " review = '" + review + "',"
                + " income = '" + income + "',"
                + " spending = '" + spending + "',"
                + " place = '" + place + "',"
                + " tool = '" + tool + "',"
                + " endCheck = " + String.valueOf(endCheck ? 1 : 0) + ","
                + " memo = '" + memo + "',"
                + "WHERE " + column + " = " + keyword + ";";
        super.executeSql(sql, null);
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
