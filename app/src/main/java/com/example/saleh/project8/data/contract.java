package com.example.saleh.project8.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by saleh on 14/06/18.
 */
public final class contract {
    public static final String CONTENT_AUTHORITY = "com.example.saleh.project8";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH = "item";
    public static final class Entry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
        public final static String table_name = "items";
        public final static String _ID = BaseColumns._ID;
        public final static String name = "name";
        public final static String price = "price";
        public final static String quantity = "quantity";
        public final static String suppliername = "supplier_name";
        public final static String supplierphone = "supplier_phone";

    }
    }



