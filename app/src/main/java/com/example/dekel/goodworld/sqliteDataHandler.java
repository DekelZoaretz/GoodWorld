package com.example.dekel.goodworld;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dekel on 5/11/2015.
 */
public class SqliteDataHandler {

    private HistoryData mHistoryData;
    private List<String> contactData;


    public SqliteDataHandler() {

        mHistoryData = new HistoryData();

       /* if(!hasDatabase()){
            //create database
        }*/
        loadContactsAndSumFromDatabase();
    }

    private void loadContactsAndSumFromDatabase() {
        //Loading the contact data from the database to the list in HistoryData
        /*mHistoryData.addToList("Dekel", 10);
        mHistoryData.addToList("Shmuel", 20);
        mHistoryData.addToList("Oz", 40);*/

    }

    private boolean hasDatabase(){
        if(true){
            return true;
        }else{
            return false;
        }
    }

    public void createDatabase(){
        //create database if not exist
    }

    public String[] getContactArray(){
        return mHistoryData.getContactArray();
    }

}
