package com.example.myreading.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    private static final String TAG = "ttttttttttttDataManager";

    public static final String CREATE_COLLECTION_SENTENCE = "create table Collection_Sentence ("
            + "id integer primary key autoincrement,"
            + "sentence_source text,"
            + "sentence_content text)";


    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG,"db.getVersion()="+sqLiteDatabase.getVersion());
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "Collection_Sentence" + ";");
        sqLiteDatabase.execSQL(CREATE_COLLECTION_SENTENCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }
}
