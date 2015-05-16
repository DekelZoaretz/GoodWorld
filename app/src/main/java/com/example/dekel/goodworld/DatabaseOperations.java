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
    Set<String> set;
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

    public void loadContactsAndSumFromDatabase(){
        List<String> mList = new ArrayList<String>();
        set = new HashSet<>();
        String currentName = "";
//        Getting the data (USERNAME) from the database and load it to contactsData
        SQLiteDatabase db = open();
        Cursor cursor = db.query(DatabaseInfo.TABLE_NAME,
                                new String[] {DatabaseInfo.COLUMN_USER_NAME},
                                null, //selection
                                null, //selection args
                                null, //group by
                                null, //having
                                null); //order by
        if(cursor.moveToFirst()){
            do{
                currentName = getStringFromColumnName(cursor, DatabaseInfo.COLUMN_USER_NAME);
                mList.add(currentName);
                if (!set.contains(currentName)) {
                    set.add(currentName);
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        close(db);

        int occurrences;
        for(String name : set){
            occurrences = Collections.frequency(mList, name);
            DatabaseInfo.contactsData.add(name + " (" + String.valueOf(occurrences) + ")");
        }
    }

//    Get the data from the database with the position of the cursor
    private String getStringFromColumnName(Cursor cursor, String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }

    public SQLiteDatabase open() {
        return this.getWritableDatabase();
    }

    public void close(SQLiteDatabase database){
        database.close();
    }
}
