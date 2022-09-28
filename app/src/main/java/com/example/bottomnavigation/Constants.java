package com.example.bottomnavigation;

public class Constants {

    //db name
    public static final String DB_NAME = "MY_RECORDS_DB";

    //db version
    public static final int DB_VERSION = 1;

    //table name
    public static final String TABLE_NAME = "MY_RECORDS_TABLE";

    //columns / fields of table
    public static final String C_ID = "ID";
    public static final String C_NAME = "NAME";
    public static final String C_IMAGE = "IMAGE";
    public static final String C_TABLETS = "TABLETS";
    public static final String C_TIMES = "TIMES";
    public static final String C_FOOD = "FOOD";
    public static final String C_ADDED_TIMESTAMP = "ADDED_TIME_STAMP";
    public static final String C_UPDATED_TIMESTAMP = "UPDATED_TIME_STAMP";

    //create table query
    public static final String CREATE_TABLE = "CREATE TABLE" + TABLE_NAME + "("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_NAME + " TEXT,"
            + C_IMAGE + " TEXT,"
            + C_TABLETS + " TEXT,"
            + C_TIMES + " TEXT,"
            + C_FOOD + " TEXT,"
            + C_ADDED_TIMESTAMP + " TEXT,"
            + C_UPDATED_TIMESTAMP + " TEXT"
            + ")";

}

