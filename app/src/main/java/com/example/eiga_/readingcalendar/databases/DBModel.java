package com.example.eiga_.readingcalendar.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBModel {

    DBOpenHelper helper = null;
    SQLiteDatabase db = null;
    final String CALENDER_TABLE_NAME = "calenders";
    final String MYSET_PLAN_TABLE_NAME = "myset_plans";
    final String READING_DATA_TABLE_NAME = "reading_datas";

    DBModel(Context context){
            if(helper == null || db == null) {
                helper = new DBOpenHelper(context);
                db = helper.getWritableDatabase();
            }
    }

    public void createTacle() {

    }

    public String calenderSearchData(String column, String keyword){
        // Cursorを確実にcloseするために、try{}～finally{}する
        Cursor cursor = null;
        try{
            //SQL文
            String sql = "SELECT * FROM " + CALENDER_TABLE_NAME + " WHERE ? = ?";
            //SQL文実行
            cursor = db.rawQuery(sql , new String[]{column,keyword});
            // 検索結果をcursorから読み込んで返す
            return readCursor(cursor);
        }
        finally {
            // Cursorをclose
            if( cursor != null) {
                cursor.close();
            }
        }
    }
    public String mysetPlanSearchData(String column, String keyword){
        // Cursorを確実にcloseするために、try{}～finally{}する
        Cursor cursor = null;
        try{
            //SQL文
            String sql = "SELECT * FROM " + MYSET_PLAN_TABLE_NAME + " WHERE ? = ?";
            //SQL文実行
            cursor = db.rawQuery(sql , new String[]{column,keyword});
            // 検索結果をcursorから読み込んで返す
            return readCursor(cursor);
        }
        finally {
            // Cursorをclose
            if( cursor != null) {
                cursor.close();
            }
        }
    }
    public String redingDataSearchData(String column, String keyword){
        // Cursorを確実にcloseするために、try{}～finally{}する
        Cursor cursor = null;
        try{
            //SQL文
            String sql = "SELECT * FROM " + READING_DATA_TABLE_NAME + " WHERE ? = ?";
            //SQL文実行
            cursor = db.rawQuery(sql , new String[]{column,keyword});
            // 検索結果をcursorから読み込んで返す
            return readCursor(cursor);
        }
        finally {
            // Cursorをclose
            if( cursor != null) {
                cursor.close();
            }
        }
    }

    private String readCursor(Cursor cursor) {
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

    public void CalenderInsertData(String day, String planTitle, String planType, String timeZone,int income, int spending, int mysetPlanId, int readingDataId){

    }
}
