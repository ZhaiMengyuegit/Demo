package com.example.myreading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.myreading.Fragment.PoemContentFragment;
import com.example.myreading.Utils.MessageEvent;

import org.greenrobot.eventbus.Subscribe;


public class PoemContentActivity extends BaseActivity {
    public static void actionStart(Context context, String PoemTitle,String PoemAuthor,String PoemKind,String PoemContent, String PoemIntro){
        SharedPreferences pref =context.getSharedPreferences("data",MODE_PRIVATE);
        int total=pref.getInt( "total",0 );
        SharedPreferences.Editor editor=context.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        editor.putInt( "total",total+1 );
        editor.commit();
        int n=pref.getInt( "total",0 );
        Intent intent=new Intent( context,PoemContentActivity.class );
        intent.putExtra( "poem_title",PoemTitle );
        intent.putExtra("poem_author",PoemAuthor);
        intent.putExtra("poem_kind",PoemKind);
        intent.putExtra( "poem_content",PoemContent );
        intent.putExtra( "poem_intro",PoemIntro );
        context.startActivity( intent );
    }
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.poem_content );
        String poemtitle=getIntent().getStringExtra( "poem_title" );
        String poemauthor=getIntent().getStringExtra("poem_author");
        String poemkind=getIntent().getStringExtra("poem_kind");
        String poemcontent=getIntent().getStringExtra( "poem_content" );
        String poemintro=getIntent().getStringExtra( "poem_intro" );

        PoemContentFragment newsContentFragment=(PoemContentFragment)getSupportFragmentManager().findFragmentById( R.id.poem_content_fragment );
        newsContentFragment.refresh(poemtitle,poemauthor,poemkind,poemcontent,poemintro);

    }
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (event.getCode() == 1000){
            Log.e("TAG","PoemContentActivity");
            PoemContentActivity.this.finish();
        }
    }
}
