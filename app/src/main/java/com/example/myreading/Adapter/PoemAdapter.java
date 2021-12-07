package com.example.myreading.Adapter;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myreading.Entity.PoemData;
import com.example.myreading.PoemContentActivity;
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

public class PoemAdapter extends RecyclerView.Adapter<PoemAdapter.ViewHolder>{
    private List<PoemData> PoemDataList =new LinkedList<PoemData>();
    private int mPosition = 0;
    int type=2;

    public PoemAdapter(int type){//type第几个界面
        this.type=type;
        new MyFoodTask().execute();
    }
    public int getPosition() {
        return mPosition;
    }

    public void removeItem(int position) {
        PoemDataList.remove(position);
        notifyDataSetChanged();
    }
    public PoemData getItem(int position){
        return PoemDataList.get(position);
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView poemtitletext;
        TextView poemauthortext;
        public ViewHolder(View view){
            super(view);
            poemtitletext =(TextView)view.findViewById( R.id.poem_item_title );
            poemauthortext =(TextView)view.findViewById( R.id.poem_item_author );
        }
    }
    public void update(List<PoemData> mPoemList){
        this.PoemDataList =mPoemList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.poem_item,parent,false );
        final ViewHolder holder=new ViewHolder( view );
        view.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                PoemData poemData= PoemDataList.get( holder.getAdapterPosition() );
                PoemContentActivity.actionStart( parent.getContext(),poemData.getTitle(),poemData.getPoemAuthor(),poemData.getPoemKind(),poemData.getPoemContent(),poemData.getPoemIntro());
            }
        } );
        return holder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PoemData poemData= PoemDataList.get(position);
        String t =poemData.getTitle();
        holder.poemtitletext.setText(poemData.getTitle());
        holder.poemauthortext.setText(poemData.getPoemAuthor());
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
        return PoemDataList.size();
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
                URL mUrl = new URL("http://api.tianapi.com/txapi/poetry/index?key=64c3913b370ab38576bc2ad730ab01bc");
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
                   ArrayList<PoemData> list = parseJson(response.toString());
                   PoemDataList.addAll(list);
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

        private ArrayList<PoemData> parseJson(String json) throws Exception {

            ArrayList<PoemData> lists = new ArrayList <PoemData>();
            JSONObject bigObj = new JSONObject( json );
            JSONArray array = bigObj.getJSONArray( "newslist" );
            PoemData n = null;
            for (int i = 0; i < array.length(); i++) {
                n = new PoemData();
                JSONObject smallObj = array.getJSONObject( i );
                n.setTitle( smallObj.getString( "title" ) );
                n.setPoemAuthor(smallObj.getString("author"));
                n.setPoemKind(smallObj.getString("kind"));
                n.setPoemContent( smallObj.getString( "content" ) );
                n.setPoemIntro(smallObj.getString("intro"));

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
