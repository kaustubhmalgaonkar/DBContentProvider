package com.examples.dbcontentproviderdemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import com.examples.dbcontentproviderdemo.model.Demo;
import com.examples.dbcontentproviderdemo.provider.DemoContract;

public class DemoDB {

    private static final Uri URI = DemoContract.Demo.CONTENT_URI;

    private static final String[] PROJECTIONS = {
            DemoContract.Demo._ID,
            DemoContract.Demo.NAME,
            DemoContract.Demo.MOBILE,
            DemoContract.Demo.EMAIL
    };

    public static final String ORDER_BY_ID_DESC = PROJECTIONS[0] + " DESC";
    public static final String ORDER_BY_ID_ASC = PROJECTIONS[0] + " ASC";

    private DemoDB() {
    }

    public static int insert(Context context,Demo demo){
        ContentValues values = new ContentValues();
        values.put(PROJECTIONS[1],demo.getName());
        values.put(PROJECTIONS[2],demo.getMobile_no());
        values.put(PROJECTIONS[3],demo.getEmail());

        Uri uri = context.getContentResolver().insert(URI,values);

        if(uri != null){
            return Integer.parseInt(uri.getLastPathSegment());
        }

        return -1;
    }

    public static Demo getDemo(Context context,int id){
        Demo demo = new Demo();
        Cursor cursor = context.getContentResolver().query(URI, PROJECTIONS, PROJECTIONS[0] + " = ? ", new String[]{Integer.toString(id)}, null);

        try{
            if(cursor != null){
                if(cursor.moveToFirst()){
                    int idIndex = cursor.getColumnIndex(PROJECTIONS[0]);
                    int nameIndex = cursor.getColumnIndex(PROJECTIONS[1]);
                    int mobileIndex = cursor.getColumnIndex(PROJECTIONS[2]);
                    int emailIndex = cursor.getColumnIndex(PROJECTIONS[3]);

                    demo.setId(cursor.getInt(idIndex));
                    demo.setName(cursor.getString(nameIndex));
                    demo.setMobile_no(cursor.getString(mobileIndex));
                    demo.setEmail(cursor.getString(emailIndex));
                }
            }
        } finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return demo;
    }

    public static ArrayList<Demo> getAllDemoList(Context context,String orderBy){
        ArrayList<Demo> demoList = new ArrayList<Demo>();

        Cursor cursor = context.getContentResolver().query(URI,PROJECTIONS,null,null,orderBy);

        try {
            if(cursor != null){
                int idIndex = cursor.getColumnIndex(PROJECTIONS[0]);
                int nameIndex = cursor.getColumnIndex(PROJECTIONS[1]);
                int mobileIndex = cursor.getColumnIndex(PROJECTIONS[2]);
                int emailIndex = cursor.getColumnIndex(PROJECTIONS[3]);

                while (cursor.moveToNext()) {
                    Demo demo = new Demo();
                    demo.setId(cursor.getInt(idIndex));
                    demo.setName(cursor.getString(nameIndex));
                    demo.setMobile_no(cursor.getString(mobileIndex));
                    demo.setEmail(cursor.getString(emailIndex));

                    demoList.add(demo);
                }
            }
        } finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return demoList;
    }

    public static int update(Context context,Demo demo){
        ContentValues values = new ContentValues();
        values.put(PROJECTIONS[1],demo.getName());
        values.put(PROJECTIONS[2], demo.getMobile_no());
        values.put(PROJECTIONS[3],demo.getEmail());

        int count = context.getContentResolver().update(URI, values,
                PROJECTIONS[0] + " = ?", new String[]{Integer.toString(demo.getId())});
        return count;
    }

    public static int delete(Context context,int id){
        int count = context.getContentResolver().delete(URI,
                PROJECTIONS[0] + " = ?", new String[]{Integer.toString(id)});
        return count;
    }

}
