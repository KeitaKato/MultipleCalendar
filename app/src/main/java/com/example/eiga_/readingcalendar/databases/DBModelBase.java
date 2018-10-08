package com.example.eiga_.readingcalendar.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eiga_.readingcalendar.data.PlanData;

import java.util.ArrayList;
import java.util.List;

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
    abstract List<PlanData> readCursorAll (Cursor cursor);

    void executeSql(String sql, String[] bindStr) {
        db.beginTransaction();
        try{
            db.execSQL(sql, bindStr);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e("ERROR", e.toString());
        }finally {
            db.endTransaction();
        }
    }
}

