package com.example.saleh.project8.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.saleh.project8.data.contract.Entry;
/**
 * Created by saleh on 14/06/18.
 */

public class dbhelper extends SQLiteOpenHelper {
        private static final String Database_name= "dsadd.db";
        private static final int Database_version = 1;

        public dbhelper(Context context) {
            super(context, Database_name, null, Database_version);
        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String SQL_CREATE_ORDERS_TABLE =  "CREATE TABLE " + Entry.table_name + " ("
                    + Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Entry.name + " TEXT NOT NULL, "
                    + Entry.price + " INTEGER NOT NULL, "
                    + Entry.quantity + " INTEGER NOT NULL DEFAULT 0,"
                    + Entry.supplierphone +" INTEGER,"
                    + Entry.suppliername+ " TEXT NOT NULL );";
            sqLiteDatabase.execSQL(SQL_CREATE_ORDERS_TABLE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }


