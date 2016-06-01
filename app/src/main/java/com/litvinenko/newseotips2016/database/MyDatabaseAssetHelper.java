package com.litvinenko.newseotips2016.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabaseAssetHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "advices.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabaseAssetHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}