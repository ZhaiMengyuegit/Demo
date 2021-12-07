package com.example.myreading.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myreading.Entity.SentenceData;

public class SentenceDataManger {
    private static final String TAG = "SentenceDataManager";
    private static final String DB_NAME = "Sentencedata.db";
    private static final String TABLE_NAME = "Collection_Sentence";
    public static final String SENTENCE_ID = "sentence_id";
    public static final String SENTENCE_Title = "sentence_title";
    public static final String SENTENCE_CONTENT = "sentence_content";

    private static final int DB_VERSION = 2;
    private Context mContext = null;
    //创建用户book表
    private static final String DB_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + SENTENCE_ID + " integer primary key,"
            + SENTENCE_Title + " varchar,"
            + SENTENCE_CONTENT + " varchar" + ");";
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataBaseManagementHelper mDatabaseHelper = null;

    private static class DataBaseManagementHelper extends SQLiteOpenHelper {

        public DataBaseManagementHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG, "db.getVersion()=" + db.getVersion());
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
     public SentenceDataManger(Context context) {
       mContext = context;
        Log.i(TAG, "SentenceDataManager construction!");
    }
    public void openDataBase() throws SQLException {
        mDatabaseHelper = new SentenceDataManger.DataBaseManagementHelper(mContext);
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void closeDataBase() throws SQLException {
        mDatabaseHelper.close();
    }

    public long insertSentenceData(SentenceData sentenceData) {
        String sentenceTitle = sentenceData.getTitle();
        String sentenceContent = sentenceData.getSentenceContent();

        ContentValues values = new ContentValues();
        values.put(SENTENCE_Title, sentenceTitle);
        values.put(SENTENCE_CONTENT, sentenceContent);

        return mSQLiteDatabase.insert(TABLE_NAME,null, values);
    }

    public boolean deleteSentenceData(int id) {
        return mSQLiteDatabase.delete(TABLE_NAME, SENTENCE_ID + "=" + id, null) > 0;
    }
    public Cursor fetchSentenceData() throws SQLException {
        Cursor mCursor = mSQLiteDatabase.query("Collection_Sentence", null, null, null, null, null, null);
        return mCursor;
    }


}
