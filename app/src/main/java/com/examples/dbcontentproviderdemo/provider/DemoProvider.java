package com.examples.dbcontentproviderdemo.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class DemoProvider extends ContentProvider {

    private static final UriMatcher uriMatcher = buildUriMatcher();
    private static final int ROUTE_DEMO = 1;
    private static final int ROUTE_DEMO_ID = 2;

    private DemoDatabase demoDatabaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DemoContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, "demo", ROUTE_DEMO);
        matcher.addURI(authority, "demo/#", ROUTE_DEMO_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        demoDatabaseHelper = new DemoDatabase(getContext());
        if(demoDatabaseHelper == null)
            return false;

        sqLiteDatabase = demoDatabaseHelper.getReadableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        Cursor cursor = null;

        switch (uriMatcher.match(uri)){
            case ROUTE_DEMO:
                queryBuilder.setTables(DemoDatabase.Tables.Demo);
                cursor = queryBuilder.query(sqLiteDatabase, projection, selection,
                        selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                break;
        }

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case ROUTE_DEMO:
                return DemoContract.Demo.CONTENT_TYPE;
            case ROUTE_DEMO_ID:
                return DemoContract.Demo.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = uriMatcher.match(uri);
        Uri returnUri = null;
        long row = -1;
        switch (match) {
            case ROUTE_DEMO:
                row = sqLiteDatabase.insert(DemoDatabase.Tables.Demo, "", values);
                if (row > 0) {
                    returnUri = ContentUris.withAppendedId(DemoContract.Demo.CONTENT_URI, row);
                }
                break;
        }
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case ROUTE_DEMO:
                // delete all the records of the table
                count = sqLiteDatabase.delete(DemoDatabase.Tables.Demo, selection, selectionArgs);
                break;
            case ROUTE_DEMO_ID:
                String id = uri.getLastPathSegment(); //gets the id
                count = sqLiteDatabase.delete(DemoDatabase.Tables.Demo, DemoContract.Demo._ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        final int match = uriMatcher.match(uri);
        switch (match) {
            case ROUTE_DEMO:
                count = sqLiteDatabase.update(DemoDatabase.Tables.Demo, values, selection, selectionArgs);
                break;
        }
        return count;
    }
}
