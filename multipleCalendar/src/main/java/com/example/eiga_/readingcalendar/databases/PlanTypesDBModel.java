package com.example.eiga_.readingcalendar.databases;

import android.content.Context;
import android.database.Cursor;

import com.example.eiga_.readingcalendar.data.PlanData;
import com.example.eiga_.readingcalendar.data.PlanTypeData;

import java.util.ArrayList;
import java.util.List;

public class PlanTypesDBModel extends DBModelBase{

    private final String PLAN_TYPES_TABLE_NAME = "plan_types";
    private final Context context;

    public PlanTypesDBModel(Context context) {
        super(context);
        this.context = context;
    }

    public PlanTypeData searchData(String column, String keyword) {
        Cursor cursor = null;
        try {
            //SQL文
            String sql = "SELECT * FROM " + PLAN_TYPES_TABLE_NAME + " WHERE " + column + "=" + keyword; // selectionArgsがなぜか使えない
            //SQL文実行
            cursor = db.rawQuery(sql, null);
            return readCursor(cursor);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public List<PlanTypeData> searchDataAll(String column, String keyword) {
        Cursor cursor = null;
        try {
            //SQL文
            String sql = "SELECT * FROM " + PLAN_TYPES_TABLE_NAME + " WHERE " + column + "=" + keyword; // selectionArgsがなぜか使えない
            //SQL文実行
            cursor = db.rawQuery(sql, null);
            return readCursorAll(cursor);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Cursor getSearchDataCursor(String column, String keyword) {
        // SQL
        String sql = "SELECT * FROM " + PLAN_TYPES_TABLE_NAME + " WHERE " + column + " = " + "date('" + keyword + "');";
        // SQL実行
        Cursor cursor = db.rawQuery(sql, null);

        return cursor;
    }

    private PlanTypeData readCursor(Cursor cursor) {
        //カーソル開始位置を先頭にする
        PlanTypeData planType = new PlanTypeData();
        // 各データを格納
        while (cursor.moveToNext()) {
            planType.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            planType.setTypeName(cursor.getString(cursor.getColumnIndex("type_name")));
            planType.setReviewFlag(cursor.getInt(cursor.getColumnIndex("review_flag")) != 0);
            planType.setIncomeFlag(cursor.getInt(cursor.getColumnIndex("income_flag")) != 0);
            planType.setSpendingFlag(cursor.getInt(cursor.getColumnIndex("spending_flag")) != 0);
            planType.setPlaceFlag(cursor.getInt(cursor.getColumnIndex("place_flag")) != 0);
            planType.setToolFlag(cursor.getInt(cursor.getColumnIndex("tool_flag")) != 0);
            planType.setCheckFlag(cursor.getInt(cursor.getColumnIndex("check_flag")) != 0);
        }
        return planType;
    }

    private List<PlanTypeData> readCursorAll (Cursor cursor) {
        // 返すlistを生成。
        List<PlanTypeData> planTypeList = new ArrayList<>();
        while (cursor.moveToNext()) {
            // PlanDataオブジェクトを生成。
            PlanTypeData planType = new PlanTypeData();
            planType.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            planType.setTypeName(cursor.getString(cursor.getColumnIndex("type_name")));
            planType.setReviewFlag(cursor.getInt(cursor.getColumnIndex("review_flag")) != 0);
            planType.setIncomeFlag(cursor.getInt(cursor.getColumnIndex("income_flag")) != 0);
            planType.setSpendingFlag(cursor.getInt(cursor.getColumnIndex("spending_flag")) != 0);
            planType.setPlaceFlag(cursor.getInt(cursor.getColumnIndex("place_flag")) != 0);
            planType.setToolFlag(cursor.getInt(cursor.getColumnIndex("tool_flag")) != 0);
            planType.setCheckFlag(cursor.getInt(cursor.getColumnIndex("check_flag")) != 0);
            // Listに追加
            planTypeList.add(planType);
        }
        return planTypeList;
    }

    public void insertData(String typeName, int reviewFlag, int incomeFlag, int spendingFlag, int placeFlag, int toolFlag, int checkFlag){
        String sql = "INSERT INTO " + PLAN_TYPES_TABLE_NAME
                + " (type_name,"
                + " review_flag,"
                + " income_flag,"
                + " spending_flag,"
                + " placeFlag,"
                + " toolFlag,"
                + " check_flag,"
                + " created_at,"
                + " updated_at"
                + ") values("
                + " '" + typeName + "',"
                + " " + reviewFlag + ","
                + " " + incomeFlag + ","
                + " " + spendingFlag + ","
                + " " + placeFlag + ","
                + " " + toolFlag + ","
                + " " + checkFlag + ","
                + " datetime('now', 'utc'),"
                + " datetime('now', 'utc') "
                +");";

        super.executeSql(sql, null);

    }

    public void updateData(String column, String keyword, String day, String planTitle, String planType, String timeZone,String income, String spending) {
        String sql = "UPDATE " + PLAN_TYPES_TABLE_NAME
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
        StringBuilder sql = new StringBuilder("DELETE FROM " + PLAN_TYPES_TABLE_NAME
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
        StringBuilder sql = new StringBuilder("DELETE FROM " + PLAN_TYPES_TABLE_NAME
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
