package com.example.app.project.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pavel on 07/06/2015.
 */
public class LocalModel {

    final static int VERSION = 1;
    Helper sqlDb;

    private final static LocalModel instance = new LocalModel();

    public static LocalModel getInstance() {
        return instance;
    }

    public void init(Context context) {
        if (sqlDb == null) {
            sqlDb = new Helper(context);
        }
    }

    class Helper extends SQLiteOpenHelper{
        public Helper(Context context) {
            super(context,"database.db",null,VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
