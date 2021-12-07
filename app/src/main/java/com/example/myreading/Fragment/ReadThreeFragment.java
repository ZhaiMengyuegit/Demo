package com.example.myreading.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myreading.Adapter.ArticleAdapter;
import com.example.myreading.Entity.ArticleData;
import com.example.myreading.Utils.ArticleDataManger;
import com.example.myreading.R;

public class ReadThreeFragment extends Fragment {

    ArticleDataManger mArticleDataManger;
    ArticleAdapter articleadapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate( R.layout.read_three_fragment, container, false );

        RecyclerView articleTitleRecyclerView = (RecyclerView)view.findViewById( R.id.article_title_recycler_view );
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        articleTitleRecyclerView.setLayoutManager(layoutManager);
        articleadapter=new ArticleAdapter(3);

        articleTitleRecyclerView.setAdapter(articleadapter);

        setHasOptionsMenu(true);
        registerForContextMenu( articleTitleRecyclerView );//注册上下文菜单
        mArticleDataManger=new ArticleDataManger(getActivity());
        return view;
    }
    public void onDestroy(){
        super.onDestroy();
        //mPoemDataManger.closeDataBase();
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        new MenuInflater(getActivity()).inflate(R.menu.menu_read, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    public boolean onContextItemSelected(MenuItem item){
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        ArticleData articledata;
        int id;
        if (getUserVisibleHint()) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    int r = articleadapter.getPosition();
                    articledata =articleadapter.getItem( articleadapter.getPosition() );
                    id = articledata.getArticleId();
                    DeleteDialog( r, id );
                    break;
                case R.id.action_collect:
                    articledata = articleadapter.getItem( articleadapter.getPosition() );
                    mArticleDataManger.openDataBase();
                    mArticleDataManger.insertSentenceData(articledata);
                    break;
            }
            return true;
        }

        return super.onContextItemSelected(item);
    }
    private void DeleteDialog(final int r,final int strid){
        new AlertDialog.Builder(getContext())
                .setTitle("不感兴趣")
                .setMessage("不再推荐此类文章？")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {            //既可以使用Sql语句删除，也可以使用使用delete方法删除
                                articleadapter.removeItem( r );
                            }
                        })
                .setNegativeButton( "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                } )
                .create()
                .show();

    }
    private void changecollect(int id,boolean collect){
        String path = "http://192.168.43.93:8080/Web/Test2";
           /* HttpUtil.changecollect(id,collect,path,new HttpCallbackListener(){
                @Override
                public void onFinish(String response) {//成功时的处理方法
                }
                @Override
                public void onError(Exception e){//失败时的处理方法
                }
            });

            */
    }



    public interface HttpCallbackListener{
        void onFinish(String response);
        void onError(Exception e);
    }
}
