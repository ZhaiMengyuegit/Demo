package com.example.myreading.Adapter;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myreading.ArticleContentActivity;
import com.example.myreading.Entity.ArticleData;
import com.example.myreading.Entity.PoemData;
import com.example.myreading.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder>{
    private List<ArticleData> ArticleDataList =new LinkedList<ArticleData>();
    private int mPosition = 0;
    int type=2;

    public ArticleAdapter(int type){//type第几个界面
        this.type=type;
        new MyFoodTask().execute();
    }
    public int getPosition() {
        return mPosition;
    }

    public void removeItem(int position) {
        ArticleDataList.remove(position);
        notifyDataSetChanged();
    }
    public ArticleData getItem(int position){
        return ArticleDataList.get(position);
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView articletitletext;
        TextView articleauthortext;
        public ViewHolder(View view){
            super(view);
            articletitletext =(TextView)view.findViewById( R.id.article_item_title );
            articleauthortext =(TextView)view.findViewById( R.id.article_item_author );
        }
    }
    public void update(List<ArticleData> mArticleList){
        this.ArticleDataList =mArticleList;
        notifyDataSetChanged();
    }

    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.article_item,parent,false );
        final ArticleAdapter.ViewHolder holder=new ArticleAdapter.ViewHolder( view );
        view.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                ArticleData articleData= ArticleDataList.get( holder.getAdapterPosition() );
                ArticleContentActivity.actionStart(parent.getContext(),articleData.getTitle(),articleData.getArticleAuthor(),articleData.getArticleChapter(),articleData.getArticleContent(),articleData.getArticleNote(),articleData.getArticleTranslation());
            }
        } );
        return holder;

    }

    @Override
    public void onBindViewHolder(final ArticleAdapter.ViewHolder holder, final int position) {
        ArticleData articleData= ArticleDataList.get(position);
       // String t =articleData.getPoemTitle();
        holder.articletitletext.setText(articleData.getTitle());
        holder.articleauthortext.setText(articleData.getArticleAuthor());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mPosition =holder.getAdapterPosition();
                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return ArticleDataList.size();
    }

    private class   MyFoodTask extends AsyncTask<String, Void, Map<String,Object>> {
        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Map <String, Object> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                int i = 0;
                URL mUrl = new URL("http://api.tianapi.com/txapi/yuanqu/index?key=64c3913b370ab38576bc2ad730ab01bc");
               while(i<20) {
                   i++;
                   connection = (HttpURLConnection) mUrl.openConnection();
                   connection.setRequestMethod("GET");
                   InputStream is = connection.getInputStream();
                   reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                   StringBuilder response = new StringBuilder();
                   String line;
                   while ((line = reader.readLine()) != null) {
                       response.append(line);
                   }
                   ArrayList<ArticleData> list = parseJson(response.toString());
                   ArticleDataList.addAll(list);
               }
            } catch (Exception e) {

                e.printStackTrace();
            }finally {
                if (reader != null){
                    try {
                        reader.close();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
                if(connection != null){
                    connection.disconnect();
                }
            }

            PoemData pd2=new PoemData();
            return null;

        }

        @Override
        protected void onPostExecute(Map <String, Object> result) {
            //adapter.mNewsList=(List<News>)result.get("foodList");
            notifyDataSetChanged();

        }

        private ArrayList<ArticleData> parseJson(String json) throws Exception {

            ArrayList<ArticleData> lists = new ArrayList <ArticleData>();
            JSONObject bigObj = new JSONObject( json );
            JSONArray array = bigObj.getJSONArray( "newslist" );
            ArticleData n = null;
            for (int i = 0; i < array.length(); i++) {
                n = new ArticleData();
                JSONObject smallObj = array.getJSONObject( i );
                n.setTitle( smallObj.getString( "title" ) );
                n.setArticleAuthor(smallObj.getString("author"));
                n.setArticleChapter(smallObj.getString("chapter"));
                n.setArticleContent(smallObj.getString("content"));
                n.setArticleNote( smallObj.getString( "note" ) );
                n.setArticleTranslation(smallObj.getString("translation"));

                lists.add( n );
            }
            return lists;


        }
    }
    public interface  HttpCallbackListener{
        void onFinish(String response);
        void onError(Exception e);
    }
}
