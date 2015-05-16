package com.example.dekel.goodworld;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Dekel on 5/16/2015.
 */
public class HistoryContactsDataSource {

    public Context mContext;
    public DatabaseOperations mOperations;
    Set<String> set;
    private final String TAG = "Contacts DataSource";

    public HistoryContactsDataSource(Context context) {

        Log.i(TAG, "Constructor");
        mContext = context;
        mOperations = new DatabaseOperations(context);
        SQLiteDatabase db = mOperations.getReadableDatabase();
        db.close();
    }

    private SQLiteDatabase open(){
        return  mOperations.getWritableDatabase();
    }
    private void close(SQLiteDatabase database){
        database.close();
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
}
