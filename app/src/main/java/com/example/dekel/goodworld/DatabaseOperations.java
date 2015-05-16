package com.example.dekel.goodworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Dekel on 5/12/2015.
 */
public class DatabaseOperations extends SQLiteOpenHelper {

    private static final String TAG = "Database operations";
    private SQLiteDatabase database;
    private final String createDatabaseQuery =
            "CREATE TABLE " + DatabaseInfo.TABLE_NAME + "("
                    + BaseColumns._ID +  " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DatabaseInfo.COLUMN_USER_NAME + " TEXT,"
                    + DatabaseInfo.COLUMN_USER_NUM  + " TEXT,"
                    + DatabaseInfo.COLUMN_USER_MSG  + " TEXT);";


    public DatabaseOperations(Context context) {
        super(context, DatabaseInfo.DATABASE_NAME, null, DatabaseInfo.DATABASE_VERSION);
        Log.i(TAG, "Database was created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDatabaseQuery);
        Log.i(TAG, "Table was created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
