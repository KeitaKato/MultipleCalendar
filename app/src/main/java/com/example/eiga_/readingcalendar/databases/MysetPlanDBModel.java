package com.example.eiga_.readingcalendar.databases;

import android.content.Context;
import android.database.Cursor;

public class MysetPlanDBModel extends DBModelBase {

    final String MYSET_PLAN_TABLE_NAME = "myset_plans";

    MysetPlanDBModel(Context context) {
        super(context);
    }

    @Override
    public String searchData(String column, String keyword) {
        //SQL文
        String sql = "SELECT * FROM " + MYSET_PLAN_TABLE_NAME + " WHERE ? = ?";
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

    public void insertData(String planTitle, String planType, String timeZone, String income, String spending) {
        String sql = "INSERT INTO " + MYSET_PLAN_TABLE_NAME
                + "(plan_title, plan_type, time_zone, income, spending) values(?,?,?,?,?);";
        String[] bindStr = new String[] {
                planTitle,
                planType,
                timeZone,
                income,
                spending
        };
        super.executeSql(sql,bindStr);
    }

    public void updateData(String column, String keyword, String planTitle, String planType, String timeZone, String income, String spending) {
        String sql = "UPDATE " + MYSET_PLAN_TABLE_NAME
                + " SET plan_title = ?, plan_type = ?, time_zone = ?, income = ?, spending = ? "
                + "WHERE ? = ? ;";
        String[] bindStr = new String[] {
                planTitle,
                planType,
                timeZone,
                income,
                spending,
                column,
                keyword
        };
        super.executeSql(sql,bindStr);
    }

    @Override
    void deleteData(String column, String keyword) {
        String sql = "DELETE FROM " + MYSET_PLAN_TABLE_NAME
                + " WHERE ? = ?;";
        String[] bindStr = new String[]{
                column,
                keyword
        };
        super.executeSql(sql,bindStr);
    }
}