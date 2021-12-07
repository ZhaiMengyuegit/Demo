package com.example.myreading.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myreading.Entity.PoemData;

public class PoemDataManger {
    private static final String TAG = "PoemDataManager";
    private static final String DB_NAME = "Poemdata.db";
    private static final String TABLE_NAME = "Collection_Poem";
    public static final String POEM_ID = "POEM_ID";
    public static final String POEM_TITLE = "POEM_TITLE";
    public static final String POEM_AUTHOR = "POEM_AUTHOR";
    public static final String POEM_KIND = "POEM_KIND";
    public static final String POEM_CONTENT = "POEM_CONTENT";
    public static final String POEM_INTRO = "POEM_INTRO";
    private static final int DB_VERSION = 2;
    private Context mContext = null;
    //创建用户book表
    private static final String DB_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + POEM_ID + " integer primary key,"
            + POEM_TITLE + " varchar,"
            +POEM_AUTHOR+" varchar,"
            +POEM_KIND+" varchar,"
            +POEM_CONTENT+" varchar,"
            +POEM_INTRO + " varchar" + ");";
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataBaseManagementHelper mDatabaseHelper = null;

    private static class DataBaseManagementHelper extends SQLiteOpenHelper {

    public DataBaseManagementHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG,"db.getVersion()="+db.getVersion());
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
            db.execSQL(DB_CREATE);
            Log.i(TAG, "db.execSQL(DB_CREATE)");
            Log.e(TAG, DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "DataBaseManagementHelper onUpgrade");
            onCreate(db);
        }

        }
    public PoemDataManger(Context context) {
        mContext = context;
        Log.i(TAG, "SentenceDataManager construction!");
    }
    public void openDataBase() throws SQLException {
        mDatabaseHelper = new DataBaseManagementHelper(mContext);
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }
    public void closeDataBase() throws SQLException {
        if(mDatabaseHelper!=null){
            mDatabaseHelper.close();
        }
    }
    public long insertPoemData(PoemData poemData) {
        String poem_TITLE = poemData.getTitle();
        String poem_AUTHOR = poemData.getPoemAuthor();
        String poem_KIND = poemData.getPoemKind();
        String poem_CONTENT = poemData.getPoemContent();
        String poem_INTRO = poemData.getPoemIntro();

        ContentValues values = new ContentValues();
        values.put(POEM_TITLE, poem_TITLE);
        values.put(POEM_AUTHOR, poem_AUTHOR);
        values.put(POEM_KIND, poem_KIND);
        values.put(POEM_CONTENT, poem_CONTENT);
        values.put(POEM_INTRO, poem_INTRO);

        return mSQLiteDatabase.insert(TABLE_NAME,null, values);
    }
    public boolean deletePoemData(int id) {
        return mSQLiteDatabase.delete(TABLE_NAME, POEM_ID + "=" + id, null) > 0;
    }

    public Cursor fetchPoemData() throws SQLException {
        Cursor mCursor = mSQLiteDatabase.query("Collection_Poem", null, null, null, null, null, null);
        return mCursor;
    }
}
