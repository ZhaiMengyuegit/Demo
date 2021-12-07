package com.example.myreading.Utils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myreading.Entity.UserData;

public class UserDataManager {
    private static final String TAG = "UserDataManager";
    private static final String DB_NAME = "user.db";
    private static final String TABLE_NAME = "users";
    public static final String ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PWD = "user_pwd";
    private static final int DB_VERSION = 2;
    private Context mContext = null;
    private Encryp encryp =new Encryp();
    //创建用户book表
    private static final String DB_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + ID + " integer primary key,"
            + USER_NAME + " varchar,"
            + USER_PWD + " varchar" + ");";
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataBaseManagementHelper mDatabaseHelper = null;

    private static class DataBaseManagementHelper extends SQLiteOpenHelper {

        DataBaseManagementHelper(Context context) {
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

    public UserDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "UserDataManager construction!");
    }
    //打开数据库
    public void openDataBase() throws SQLException {
        mDatabaseHelper = new DataBaseManagementHelper(mContext);
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }
    //关闭数据库
    public void closeDataBase() throws SQLException {
        mDatabaseHelper.close();
    }
    //添加新用户，即注册
    public long insertUserData(UserData userData) {
        String userName=userData.getUserName();
        String userPwd=userData.getUserPwd();

        ContentValues values = new ContentValues();
        String password= encryp.encode(userPwd);
        values.put(USER_NAME, userName);
        //values.put(USER_PWD, encryuserPwd);
        values.put(USER_PWD,password);
        Log.i(TAG, "tttttttttttttttttttt     "+userPwd+"     "+password);
        return mSQLiteDatabase.insert(TABLE_NAME, ID, values);
    }
    //更新用户信息，如修改密码
    public boolean updateUserData(UserData userData) {
        //int id = userData.getUserId();
        String userName = userData.getUserName();
        String userPwd = userData.getUserPwd();

        String password= encryp.encode(userPwd);
        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName);
        values.put(USER_PWD, password);
        return mSQLiteDatabase.update(TABLE_NAME, values,null, null) > 0;
        //return mSQLiteDatabase.update(TABLE_NAME, values, ID + "=" + id, null) > 0;
    }


    //
    public Cursor fetchUserData(int id) throws SQLException {
        Cursor mCursor = mSQLiteDatabase.query(false, TABLE_NAME, null, ID
                + "=" + id, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //
    public Cursor fetchAllUserDatas() {
        return mSQLiteDatabase.query(TABLE_NAME, null, null, null, null, null,
                null);
    }
    /*根据id删除用户
    public boolean deleteUserData(int id) {
        return mSQLiteDatabase.delete(TABLE_NAME, ID + "=" + id, null) > 0;
    }*/
    /*根据用户名注销
    public boolean deleteUserDatabyname(String name) {
        return mSQLiteDatabase.delete(TABLE_NAME, USER_NAME + "=" + name, null) > 0;
    }
    //删除所有用户
    public boolean deleteAllUserDatas() {
        return mSQLiteDatabase.delete(TABLE_NAME, null, null) > 0;
    }*/

    //
    public String getStringByColumnName(String columnName, int id) {
        Cursor mCursor = fetchUserData(id);
        int columnIndex = mCursor.getColumnIndex(columnName);
        String columnValue = mCursor.getString(columnIndex);
        mCursor.close();
        return columnValue;
    }
    //根据id号更新用户信息
    public boolean updateUserDataById(String columnName, int id,
                                      String columnValue) {
        ContentValues values = new ContentValues();
        values.put(columnName, columnValue);
        return mSQLiteDatabase.update(TABLE_NAME, values, ID + "=" + id, null) > 0;
    }
    //根据用户名找用户，可以判断注册时用户名是否已经存在
    public int findUserByName(String userName){
        Log.i(TAG,"findUserByName , userName="+userName);
        int result=0;
        Cursor mCursor=mSQLiteDatabase.query(TABLE_NAME, null, USER_NAME+"="+userName, null, null, null, null);
        if(mCursor!=null){
            result=mCursor.getCount();
            mCursor.close();
            Log.i(TAG,"findUserByName , result="+result);
        }
        return result;
    }
    //根据用户名和密码找用户，用于登录
    public int findUserByNameAndPwd(String userName,String pwd){
        Log.i(TAG,"findUserByNameAndPwd");
        int result=0;
        /*
        if(0!=pwd.length()){
            pwd=mAes.EncryptorString(pwd);
        }*/
        /*
        Cursor mCursor=mSQLiteDatabase.query(TABLE_NAME, null, USER_NAME+"="+userName,
                null, null, null, null);
        if(mCursor!=null){
            mCursor.moveToFirst();//必须写，否则读不到数据，将Index移动到第一位上
            int valueIndex =mCursor.getColumnIndexOrThrow("user_pwd");//如上图，valueIndex = 1;
            String tt = mCursor.getString(valueIndex);//注意value值是什么类型用合适的get，如果是String就得用getString!!否则会有异常
            //mCursor.getColumnIndex("USER_PWD");
            String ts=mAes.DecryptorString(tt);
            int k=0;
            if(ts.equals(pwd)){
                result= 1;
            }else{
                result= -1;
            }
            mCursor.close();
            Log.i(TAG,"findUserByNameAndPwd , result="+result);
        }
        return result;
        */
        if(0!=pwd.length()){
            pwd= encryp.encode(pwd);
            //pwd=mAes.EncryptorString(pwd);
        }
        Cursor mCursor=mSQLiteDatabase.query(TABLE_NAME, null, USER_NAME+"="+userName,
                null, null, null, null);
        if(mCursor!=null){
            mCursor.moveToFirst();
            result=mCursor.getCount();
            if(result==0){
                return 0;
            }
            int valueIndex1 =mCursor.getColumnIndexOrThrow("user_id");//如上图，valueIndex = 1;
            int id = mCursor.getInt(valueIndex1);//注意value值是什么类型用合适的get，如果是String就得用getString!!否则会有异常
            int valueIndex2 =mCursor.getColumnIndexOrThrow("user_pwd");//如上图，valueIndex = 1;
            String pwd_db = mCursor.getString(valueIndex2);//注意value值是什么类型用合适的get，如果是String就得用getString!!否则会有异常
            int valueIndex3 =mCursor.getColumnIndexOrThrow("user_name");//如上图，valueIndex = 1;
            String name = mCursor.getString(valueIndex3);//注意value值是什么类型用合适的get，如果是String就得用getString!!否则会有异常
            mCursor.close();
            if(pwd.equals(pwd_db)){
                return 1;
            }else{
                return 0;
            }

        }
        return result;
    }

}

