package com.example.myreading;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myreading.Adapter.CollectionAdapter;
import com.example.myreading.Entity.ArticleData;
import com.example.myreading.Utils.ArticleDataManger;
import com.example.myreading.Entity.BaseData;
import com.example.myreading.Entity.PoemData;
import com.example.myreading.Utils.PoemDataManger;
import com.example.myreading.Entity.SentenceData;
import com.example.myreading.Utils.SentenceDataManger;
import com.example.myreading.Utils.MessageEvent;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends BaseActivity {
    private List<BaseData> basedataList = new ArrayList<>();

  //  SentenceDataManger mSentenceDataManger;
    private CollectionAdapter collectionAdapter;
    //private MyDatabaseHelper helper;
    SentenceDataManger mSentenceDataManger;
    PoemDataManger mPoemDataManger;
    ArticleDataManger mArticleDataManger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect);
        //helper = new MyDatabaseHelper(this, "data.db", null, 2);
        mSentenceDataManger=new SentenceDataManger(this);
        mPoemDataManger =new PoemDataManger(this);
        mArticleDataManger=new ArticleDataManger(this);
        //mSentenceDataManger = new SentenceDataManger(this);
        //initView();


        RecyclerView readTitleRecyclerView = (RecyclerView) findViewById(R.id.read_title_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        readTitleRecyclerView.setLayoutManager(layoutManager);
        collectionAdapter = new CollectionAdapter(0);

        initNews();
        readTitleRecyclerView.setAdapter(collectionAdapter);
        //setHasOptionsMenu(true);
        registerForContextMenu(readTitleRecyclerView);//注册上下文菜单

    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CollectActivity.class);
        context.startActivity(intent);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        new MenuInflater(this).inflate(R.menu.menu_notcollect, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        //BaseData baseData;
        int id;
        switch (item.getItemId()) {
            case R.id.not_collect:
                BaseData getdata = collectionAdapter.getItem(collectionAdapter.getPosition());
                int Type= getdata.getType();
                if(Type==1){
                    SentenceData sentenceData=(SentenceData)getdata;
                    mSentenceDataManger.deleteSentenceData(sentenceData.getSentenceId());
                }else if(Type==2){
                    PoemData poemData=(PoemData)getdata;
                    mPoemDataManger.deletePoemData(poemData.getPoemId());
                }else{
                    ArticleData articleData=(ArticleData)getdata;
                    mArticleDataManger.deleteArticleData(articleData.getArticleId());
                }
                basedataList.remove(collectionAdapter.getPosition());
                collectionAdapter.notifyDataSetChanged();
                break;
        }
        return true;
    }

    private void initNews() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //SQLiteDatabase db = helper.getReadableDatabase();
                //Cursor cursor = db.rawQuery("select * from Collection_Sentence", null);
                mSentenceDataManger.openDataBase();
                mPoemDataManger.openDataBase();
                mArticleDataManger.openDataBase();

                Cursor cursor1 = mSentenceDataManger.fetchSentenceData();
                Cursor cursor2 = mPoemDataManger.fetchPoemData();
                Cursor cursor3 = mArticleDataManger.fetchArticleData();

                if (cursor1.getCount() != 0||cursor2.getCount() != 0||cursor3.getCount() != 0) {
                    if (cursor1.moveToFirst()) {
                        do {
                            //遍历Cursor对象，取出数据并打印
                            SentenceData sentenceData = new SentenceData();
                            sentenceData.setSentenceId(cursor1.getInt(cursor1.getColumnIndex("sentence_id")));
                            sentenceData.setTitle(cursor1.getString(cursor1.getColumnIndex("sentence_title")));
                            sentenceData.setSentenceContent(cursor1.getString(cursor1.getColumnIndex("sentence_content")));
                            basedataList.add(sentenceData);

                        } while (cursor1.moveToNext());
                    }
                    if (cursor2.moveToFirst()) {
                        do {
                            //遍历Cursor对象，取出数据并打印
                            PoemData poemData = new PoemData();
                            poemData.setPoemId(cursor2.getInt(cursor2.getColumnIndex("POEM_ID")));
                            poemData.setTitle(cursor2.getString(cursor2.getColumnIndex("POEM_TITLE")));
                            poemData.setPoemAuthor(cursor2.getString(cursor2.getColumnIndex("POEM_AUTHOR")));
                            poemData.setPoemKind(cursor2.getString(cursor2.getColumnIndex("POEM_KIND")));
                            poemData.setPoemContent(cursor2.getString(cursor2.getColumnIndex("POEM_CONTENT")));
                            poemData.setPoemIntro(cursor2.getString(cursor2.getColumnIndex("POEM_INTRO")));
                            //poemData.setSentenceContent(cursor2.getString(cursor2.getColumnIndex("sentence_content")));
                            basedataList.add(poemData);

                        } while (cursor2.moveToNext());
                    }
                    if (cursor3.moveToFirst()) {
                        do {
                            //遍历Cursor对象，取出数据并打印
                            ArticleData articleData = new ArticleData();
                            articleData.setArticleId(cursor3.getInt(cursor3.getColumnIndex("ArticleId")));
                            articleData.setArticleChapter(cursor3.getString(cursor3.getColumnIndex("ArticleChapter")));
                            articleData.setTitle(cursor3.getString(cursor3.getColumnIndex("ArticleTitle")));
                            articleData.setArticleContent(cursor3.getString(cursor3.getColumnIndex("ArticleContent")));
                            articleData.setArticleNote(cursor3.getString(cursor3.getColumnIndex("ArticleNote")));
                            articleData.setArticleAuthor(cursor3.getString(cursor3.getColumnIndex("ArticleAuthor")));
                            articleData.setArticleTranslation(cursor3.getString(cursor3.getColumnIndex("ArticleTranslation")));
                            basedataList.add(articleData);

                        } while (cursor3.moveToNext());
                    }
                    collectionAdapter.update(basedataList);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "收藏夹为空！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        collectionAdapter.notifyDataSetChanged();
                    }
                });

                cursor1.close();
            }
        }).start();

    }
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (event.getCode() == 1000){
            Log.e("TAG","CollectActivity");
           CollectActivity.this.finish();
        }
    }
}
