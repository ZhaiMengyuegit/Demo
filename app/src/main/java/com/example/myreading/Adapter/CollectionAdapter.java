package com.example.myreading.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myreading.ArticleContentActivity;
import com.example.myreading.Entity.ArticleData;
import com.example.myreading.Entity.BaseData;
import com.example.myreading.Entity.PoemData;
import com.example.myreading.Entity.SentenceData;
import com.example.myreading.PoemContentActivity;
import com.example.myreading.R;
import com.example.myreading.SentenceContentActivity;


import java.util.LinkedList;
import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder>{
    private List<BaseData> baseDataList =new LinkedList<BaseData>();
    private int mPosition = 0;
    int type=1;

    public CollectionAdapter(int type){//type第几个界面
        this.type=type;

    }
    public int getPosition() {
        return mPosition;
    }

    public void removeItem(int position) {
        baseDataList.remove(position);
        notifyDataSetChanged();
    }
    public BaseData getItem(int position){
        return baseDataList.get(position);
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView collectiontext;
        public ViewHolder(View view){
            super(view);
            collectiontext =(TextView)view.findViewById( R.id.collection_item_source );

        }
    }
    public void update(List<BaseData> baseDataList){
        this.baseDataList =baseDataList;
        //notifyDataSetChanged();
    }

    @Override
    public CollectionAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.collection_item,parent,false );
        final CollectionAdapter.ViewHolder holder=new CollectionAdapter.ViewHolder( view );
        view.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v) {
                BaseData baseData = baseDataList.get(holder.getAdapterPosition());
                int Type= baseData.getType();
                if(Type==1){
                    SentenceData sentenceData=(SentenceData)baseData;
                    SentenceContentActivity.actionStart(parent.getContext(), sentenceData.getTitle(),sentenceData.getSentenceContent());
                }else if(Type==2){
                    PoemData poemData=(PoemData)baseData;
                    PoemContentActivity.actionStart( parent.getContext(),poemData.getTitle(),poemData.getPoemAuthor(),poemData.getPoemKind(),poemData.getPoemContent(),poemData.getPoemIntro());
                }else{
                    ArticleData articleData=(ArticleData)baseData;
                    ArticleContentActivity.actionStart(parent.getContext(),articleData.getTitle(),articleData.getArticleAuthor(),articleData.getArticleChapter(),articleData.getArticleContent(),articleData.getArticleNote(),articleData.getArticleTranslation());
                }
            }
        });
        return holder;

    }

    @Override
    public void onBindViewHolder(final CollectionAdapter.ViewHolder holder, final int position) {
        BaseData baseData= baseDataList.get(position);
        holder.collectiontext.setText(baseData.getTitle());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mPosition =holder.getAdapterPosition();
                return false;
            }
        });

    }

    public int getItemCount() {
        return baseDataList.size();
    }

    public interface  HttpCallbackListener{
        void onFinish(String response);
        void onError(Exception e);
    }
}
