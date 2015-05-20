package com.example.dekel.goodworld;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dekel on 5/12/2015.
 */
public class DatabaseInfo {

//    Database Information
        public static final String DATABASE_NAME = "good_world.db";
        public static final int DATABASE_VERSION = 1;

//    Table Information
        public static final String TABLE_NAME = "massages";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_USER_NUM = "user_number";
        public static final String COLUMN_USER_MSG = "user_message";

//    List of Contacts with their sum of sms messages
        public static List<String> contactsData = new ArrayList<String>();
}
