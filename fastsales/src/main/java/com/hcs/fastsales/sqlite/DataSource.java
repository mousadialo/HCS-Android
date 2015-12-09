package com.hcs.fastsales.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by fernando.ramirez on 22/06/2015.
 */
public class DataSource {

    // Database fields
    private SQLiteDatabase sqLiteDatabase;
    private MySQLiteHelper mySQLiteHelper;

    protected DataSource(Context context) {
        mySQLiteHelper = new MySQLiteHelper(context);
    }

    protected void open() throws SQLException {
        sqLiteDatabase = mySQLiteHelper.getWritableDatabase();
    }

    protected void close() {
        mySQLiteHelper.close();
    }

    protected SQLiteDatabase getSqLiteDatabase() {
        return sqLiteDatabase;
    }
}
