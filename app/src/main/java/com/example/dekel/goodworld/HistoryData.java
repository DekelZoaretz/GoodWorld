package com.example.dekel.goodworld;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dekel on 5/10/2015.
 */
public class HistoryData {

    private List<String> list;
    private String[] contactsHistory;

    public HistoryData(){

        list = new ArrayList<String>();
    }

    public void addToList(String contact, int sumOfMsg){
        list.add(contact + "(" + sumOfMsg + ")");
    }

    public List<String> getFullList(){

        return list;
    }
    public String[] getContactArray(){
        contactsHistory = new String[getFullList().size()];
        contactsHistory = getFullList().toArray(contactsHistory);
        return contactsHistory;
    }

}
