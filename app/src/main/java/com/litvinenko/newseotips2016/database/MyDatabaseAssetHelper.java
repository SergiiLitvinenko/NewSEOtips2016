package com.litvinenko.newseotips2016.database;

import android.content.Context;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabaseAssetHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "advice.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabaseAssetHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}