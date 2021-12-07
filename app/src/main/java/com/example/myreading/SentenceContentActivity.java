package com.example.myreading;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myreading.Fragment.PoemContentFragment;
import com.example.myreading.Fragment.SentenceContentFragment;
import com.example.myreading.Utils.MessageEvent;

import org.greenrobot.eventbus.Subscribe;

public class SentenceContentActivity extends BaseActivity {
    public static void actionStart(Context context, String SentenceSource, String SentenceContent){
        SharedPreferences pref =context.getSharedPreferences("data",MODE_PRIVATE);
        int total=pref.getInt( "total",0 );
        SharedPreferences.Editor editor=context.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        editor.putInt( "total",total+1 );
        editor.commit();
        int n=pref.getInt( "total",0 );
        Intent intent=new Intent( context,SentenceContentActivity.class );
        intent.putExtra( "sentence_source",SentenceSource );
        intent.putExtra("sentence_content",SentenceContent);

        context.startActivity( intent );
    }
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.sentence_content );
        String sentencesource=getIntent().getStringExtra( "sentence_source" );
        String sentencecontent=getIntent().getStringExtra("sentence_content");
        SentenceContentFragment newsContentFragment=(SentenceContentFragment)getSupportFragmentManager().findFragmentById( R.id.sentence_content_fragment );
       newsContentFragment.refresh(sentencesource,sentencecontent);

    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (event.getCode() == 1000){
            Log.e("TAG","SentenceContentActivity");
            SentenceContentActivity.this.finish();
        }
    }
}
