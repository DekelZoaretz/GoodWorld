package com.example.dekel.goodworld;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dekel on 5/10/2015.
 */
public class HistoryData {

    private List<String> list;
    private HashMap<String, String> contactHistory;

    public HistoryData(){

        list = new ArrayList<String>();
        contactHistory = new HashMap<String, String>();
    }

    public void addToList(String contact, int sumOfMsg){
        list.add(contact + "(" + sumOfMsg + ")");
    }

    public List<String> getFullList(){

        return list;
    }

}
