package com.example.myreading;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myreading.Fragment.ArticleContentFragment;
import com.example.myreading.Fragment.SentenceContentFragment;
import com.example.myreading.Utils.MessageEvent;

import org.greenrobot.eventbus.Subscribe;

public class ArticleContentActivity extends BaseActivity {
    public static void actionStart(Context context, String ArticleTitle, String ArticleAuthor,String ArticleChapter,String ArticleContent,String ArticleNote,String ArticleTranslation){
        SharedPreferences pref =context.getSharedPreferences("data",MODE_PRIVATE);
        int total=pref.getInt( "total",0 );
        SharedPreferences.Editor editor=context.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        editor.putInt( "total",total+1 );
        Log.v("ArticleContentActivity",total+"");
        editor.commit();
        int n=pref.getInt( "total",0 );
        Intent intent=new Intent( context,ArticleContentActivity.class );
        intent.putExtra( "article_title",ArticleTitle );
        intent.putExtra("article_author",ArticleAuthor);
        intent.putExtra("article_chapter",ArticleChapter);
        intent.putExtra("article_content",ArticleContent);
        intent.putExtra("article_note",ArticleNote);
        intent.putExtra("article_tanslation",ArticleTranslation);

        context.startActivity( intent );
    }
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.article_content );
        String articletitle=getIntent().getStringExtra( "article_title" );
        String articleauthor=getIntent().getStringExtra("article_author");
        String articlechapter=getIntent().getStringExtra("article_chapter");
        String articlecontent=getIntent().getStringExtra("article_content");
        String articlenote=getIntent().getStringExtra("article_note");
        String articleatanslation=getIntent().getStringExtra("article_tanslation");
        ArticleContentFragment newsContentFragment=(ArticleContentFragment)getSupportFragmentManager().findFragmentById( R.id.article_content_fragment );
        newsContentFragment.refresh(articletitle,articleauthor,articlechapter,articlecontent,articlenote,articleatanslation);

    }
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (event.getCode() == 1000){
            Log.e("TAG","ArticleContentActivity");
           ArticleContentActivity.this.finish();
        }
    }
}
