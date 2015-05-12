package com.example.dekel.goodworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dekel on 5/12/2015.
 */
public class DatabaseOperations extends SQLiteOpenHelper {

    private final String TAG = "Database operations";
    private final String createDatabaseQuery = "CREATE TABLE " + DatabaseInfo.TABLE_NAME + "(" + DatabaseInfo.USER_NAME + " TEXT," + DatabaseInfo.USER_NUM + " TEXT,"  + DatabaseInfo.USER_MSG + "TEXT);";


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

    public void putInformation(DatabaseOperations dop, String userName, int userNumber, String msg){
        String phoneNumber = String.valueOf(userNumber);
        SQLiteDatabase database = dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseInfo.USER_NAME, userName);
        contentValues.put(DatabaseInfo.USER_NUM, phoneNumber);
        contentValues.put(DatabaseInfo.USER_MSG, msg);

        long res = database.insert(DatabaseInfo.TABLE_NAME, null, contentValues);
        Log.i(TAG, "Data was insert successfully!");

    }
}
