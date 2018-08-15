package com.example.eiga_.readingcalendar.databases;

import android.content.Context;
import android.database.Cursor;

public class RedingDataDBModel extends DBModelBase {

    final String READING_DATA_TABLE_NAME = "reading_datas";

    RedingDataDBModel(Context context) {
        super(context);
    }

    @Override
    public String searchData(String column, String keyword) {
        String sql = "SELECT * FROM " + READING_DATA_TABLE_NAME + " WHERE ? = ?";
        // 検索結果をcursorから読み込んで返す

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
}
