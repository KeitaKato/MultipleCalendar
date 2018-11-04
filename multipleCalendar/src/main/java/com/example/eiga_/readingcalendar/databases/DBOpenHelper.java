package com.example.eiga_.readingcalendar.databases;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {

    // バージョン
    private static  final int DATABASE_VERSION = 1;
    // データベース名
    private static final String DATABASE_NAME = "calenderDB.db";
    private static final String CALENDER_TABLE_NAME = "calenders";
    private static final String CALENDER_CREATE_SQL = "CREATE TABLE calenders ("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "plan_day TEXT NOT NULL,"
            + "plan_title TEXT NOT NULL,"
            + "plan_type INTEGER,"
            + "start_time TEXT,"
            + "end_time TEXT,"
            + "use_time INTEGER,"
            + "notice INTEGER,"
            + "review INTEGER,"
            + "income INTEGER,"
            + "spending INTEGER,"
            + "place INTEGER,"
            + "tool INTEGER,"
            + "end_check INTEGER,"
            + "memo TEXT,"
            + "preset_id INTEGER,"
            + "reading_id INTEGER,"
            + "created_at NOT NULL,"
            + "updated_at NOT NULL DEFAULT CURRENT_TIMESTAMP"
            + ");";
    private static final String PRESET_PLAN_TABLE_NAME = "preset_plans";
    private static final String PRESET_PLAN_CREATE_SQL = "CREATE TABLE " + PRESET_PLAN_TABLE_NAME + "("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "plan_title TEXT NOT NULL,"
            + "plan_type INTEGER,"
            + "start_time TEXT,"
            + "end_time TEXT,"
            + "use_time INTEGER,"
            + "notice INTEGER,"
            + "review INTEGER,"
            + "income INTEGER,"
            + "spending INTEGER,"
            + "place INTEGER,"
            + "tool INTEGER,"
            + "end_check INTEGER,"
            + "memo TEXT,"
            + "created_at NOT NULL,"
            + "updated_at NOT NULL DEFAULT CURRENT_TIMESTAMP"
            + ");";
    private static final String PLAN_TYPES_TABLE_NAME = "plan_types";
    private static final String PLAN_TYPES_CREATE_SQL = "CREATE TABLE " + PLAN_TYPES_TABLE_NAME + "("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "type_name TEXT NOT NULL,"
            + "review_flag INTEGER NOT NULL DEFAULT 0 CHECK(review_flag IN (0, 1)),"
            + "income_flag INTEGER NOT NULL DEFAULT 0 CHECK(income_flag IN (0, 1)),"
            + "spending_flag INTEGER NOT NULL DEFAULT 0 CHECK(spending_flag IN (0, 1)),"
            + "place_flag INTEGER NOT NULL DEFAULT 0 CHECK(place_flag IN (0, 1)),"
            + "tool_flag INTEGER NOT NULL DEFAULT 0 CHECK(tool_flag IN (0, 1)),"
            + "check_flag INTEGER NOT NULL DEFAULT 0 CHECK(check_flag IN (0, 1)),"
            + "created_at NOT NULL,"
            + "updated_at NOT NULL DEFAULT CURRENT_TIMESTAMP"
            + ");";
    private static final String READING_DATA_TABLE_NAME = "reading_datas";
    private static final String READING_DATA_CREATE_SQL = "CREATE TABLE reading_datas ("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "data_title TEXT,"
            + "data_url TEXT,"
            + "data_days TEXT,"
            + "created_at NOT NULL,"
            + "updated_at NOT NULL DEFAULT CURRENT_TIMESTAMP"
            + ");";

    DBOpenHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // テーブル作成
        db.execSQL(CALENDER_CREATE_SQL);
        db.execSQL(PRESET_PLAN_CREATE_SQL);
        db.execSQL(PLAN_TYPES_CREATE_SQL);
        db.execSQL(READING_DATA_CREATE_SQL);

        // plan_typesに初期カテゴリをインサート
        String sql = "INSERT INTO plan_types (type_name, review_flag, income_flag, spending_flag, place_flag, tool_flag, check_flag, created_at, updated_at)"
                + " VALUES ('仕事', 0, 1, 0, 1, 0, 0, datetime('now', 'utc'), datetime('now', 'utc')),"
                + " ('学習', 1, 0, 0, 0, 1, 1, datetime('now', 'utc'), datetime('now', 'utc')),"
                + " ('買い物', 0, 0, 1, 1, 0, 0, datetime('now', 'utc'), datetime('now', 'utc'));";

        db.beginTransaction();
        try{
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e("ERROR", e.toString());
        }finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // アップデート
        db.execSQL("DROP TABLE IF EXISTS " + CALENDER_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + PRESET_PLAN_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + PLAN_TYPES_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + READING_DATA_TABLE_NAME + ";");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
