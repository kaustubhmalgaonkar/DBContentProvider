package com.examples.dbcontentproviderdemo.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class DemoContract {

    private DemoContract(){
    }

    public static final String CONTENT_AUTHORITY = "com.examples.provider.demo_app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ CONTENT_AUTHORITY);

    public static final String PATH_DEMO = "demo";

    interface ColumnsDemo{
        String NAME = "name";
        String MOBILE = "mobile";
        String EMAIL = "email";
    }

    public static class Demo implements BaseColumns,ColumnsDemo{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DEMO).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.demo_app.demo";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.demo_app.demo";

        public static Uri buildAlarmId(int Id){
            return CONTENT_URI.buildUpon().appendPath(Integer.toString(Id)).build();
        }

    }

}
