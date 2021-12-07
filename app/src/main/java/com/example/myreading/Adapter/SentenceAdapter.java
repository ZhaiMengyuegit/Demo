package com.example.myreading.Adapter;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myreading.Entity.SentenceData;
import com.example.myreading.R;
import com.example.myreading.SentenceContentActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SentenceAdapter extends RecyclerView.Adapter<SentenceAdapter.ViewHolder>{
    private List<SentenceData> SentenceDataList =new LinkedList<SentenceData>();
    private int mPosition = 0;
    int type=1;

    public SentenceAdapter(int type){//type第几个界面
        this.type=type;
        if(type==1){
            new MyFoodTask().execute();
        }

    }
    public int getPosition() {
        return mPosition;
    }

    public void removeItem(int position) {
        SentenceDataList.remove(position);
        notifyDataSetChanged();
    }
    public SentenceData getItem(int position){
        return SentenceDataList.get(position);
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView sentencesourcetext;
        public ViewHolder(View view){
            super(view);
            sentencesourcetext =(TextView)view.findViewById( R.id.sentence_item_source );

        }
    }
    public void update(List<SentenceData> mSentenceList){
        this.SentenceDataList =mSentenceList;
        //notifyDataSetChanged();
    }

    @Override
    public SentenceAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.sentence_item,parent,false );
        final SentenceAdapter.ViewHolder holder=new SentenceAdapter.ViewHolder( view );
        view.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v) {
                SentenceData sentenceData = SentenceDataList.get(holder.getAdapterPosition());
                SentenceContentActivity.actionStart(parent.getContext(), sentenceData.getTitle(), sentenceData.getSentenceContent());
            }
            });
        return holder;

    }

    @Override
    public void onBindViewHolder(final SentenceAdapter.ViewHolder holder, final int position) {
        SentenceData sentenceData= SentenceDataList.get(position);
       // String t =sentenceData.getPoemTitle();
        holder.sentencesourcetext.setText(sentenceData.getTitle());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mPosition =holder.getAdapterPosition();
                return false;
            }
        });

    }



    public int getItemCount() {
        return SentenceDataList.size();
    }

    private  class  MyFoodTask extends AsyncTask<String, Void, Map<String,Object>> {
        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Map <String, Object> doInBackground(String... params) {
            try {
                Request request = new Request.Builder()
                        .url("http://api.tianapi.com/txapi/mgjuzi/index?key=64c3913b370ab38576bc2ad730ab01bc")
                        .build();
                OkHttpClient client = new OkHttpClient();
                int i=0;
                while (i<20){
                    i++;
                    Response response = client.newCall(request).execute();
                    String t=response.body().string();
                    SentenceData list = parseJson(t);
                    SentenceDataList.add(list);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
            }
            return null;

        }

        @Override
        protected void onPostExecute(Map <String, Object> result) {
            notifyDataSetChanged();
        }

        private SentenceData parseJson(String json) throws Exception {

            JSONObject bigObj = new JSONObject( json );
            JSONArray array = bigObj.getJSONArray( "newslist" );
            SentenceData n = new SentenceData();
            JSONObject smallObj = array.getJSONObject( 0 );
            n.setTitle( smallObj.getString( "author" ) );
            n.setSentenceContent(smallObj.getString("content"));
            return n;


        }

    }
    public interface  HttpCallbackListener{
        void onFinish(String response);
        void onError(Exception e);
    }
}
