package com.example.myreading.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myreading.Entity.ArticleData;

public class ArticleDataManger {
    private static final String TAG = "ArticleDataManger";
    private static final String DB_NAME = "data.db";
    private static final String TABLE_NAME = "Collection_Article";
    public static final String ArticleId = "ArticleId";
    public static final String ArticleChapter = "ArticleChapter";
    public static final String ArticleTitle = "ArticleTitle";
    public static final String ArticleContent = "ArticleContent";
    public static final String ArticleNote = "ArticleNote";
    public static final String ArticleAuthor = "ArticleAuthor";
    public static final String ArticleTranslation = "ArticleTranslation";

    private static final int DB_VERSION = 2;
    private Context mContext = null;
    //创建用户book表
    private static final String DB_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + ArticleId + " integer primary key,"
            + ArticleChapter + " varchar,"
            + ArticleTitle + " varchar,"
            + ArticleContent + " varchar,"
            + ArticleNote + " varchar,"
            + ArticleAuthor + " varchar,"
            + ArticleTranslation + " varchar" + ");";
    private SQLiteDatabase mSQLiteDatabase = null;
    private ArticleDataManger.DataBaseManagementHelper mDatabaseHelper = null;

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
    public ArticleDataManger(Context context) {
        mContext = context;
        Log.i(TAG, "ArticleDataManger construction!");
    }
    public void openDataBase() throws SQLException {
        mDatabaseHelper = new ArticleDataManger.DataBaseManagementHelper(mContext);
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void closeDataBase() throws SQLException {
        mDatabaseHelper.close();
    }

    public long insertSentenceData(ArticleData articleData) {
        String articleChapter = articleData.getArticleChapter();
        String title = articleData.getTitle();
        String articleContent = articleData.getArticleContent();
        String articleNote = articleData.getArticleNote();
        String articleAuthor = articleData.getArticleAuthor();
        String articleTranslation = articleData.getArticleTranslation();

        ContentValues values = new ContentValues();
        values.put(ArticleChapter, articleChapter);
        values.put(ArticleTitle, title);
        values.put(ArticleContent, articleContent);
        values.put(ArticleNote, articleNote);
        values.put(ArticleAuthor, articleAuthor);
        values.put(ArticleTranslation, articleTranslation);

        return mSQLiteDatabase.insert(TABLE_NAME,null, values);
    }

    public boolean deleteArticleData(int id) {
        return mSQLiteDatabase.delete(TABLE_NAME, ArticleId + "=" + id, null) > 0;
    }
    public Cursor fetchArticleData() throws SQLException {
        Cursor mCursor = mSQLiteDatabase.query("Collection_Article", null, null, null, null, null, null);
        return mCursor;
    }

}
