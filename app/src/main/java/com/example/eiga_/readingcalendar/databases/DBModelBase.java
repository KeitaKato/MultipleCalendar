package com.example.eiga_.readingcalendar.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class DBModelBase {

    DBOpenHelper helper = null;
    SQLiteDatabase db = null;

    DBModelBase(Context context) {
        if (helper == null || db == null) {
            helper = new DBOpenHelper(context);
            db = helper.getWritableDatabase();
        }
    }

    public void createTacle() {

    }

    abstract String searchData(String column, String keyword);

    protected Cursor executeSearchSql(String sql, String[] bindStr) {
        // Cursorを確実にcloseするために、try{}～finally{}する
        Cursor cursor = null;
        try {
            //SQL文実行
            cursor = db.rawQuery(sql, bindStr);
            // 検索結果をcursorから読み込んで返す
            return cursor;
        } finally {
            // Cursorをclose
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    abstract String readCursor(Cursor cursor);

    protected void executeInsertSql(String sql, String[] bindStr) {

    }
}

