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

import com.example.myreading.Adapter.PoemAdapter;
import com.example.myreading.R;
import com.example.myreading.Entity.PoemData;
import com.example.myreading.Utils.PoemDataManger;

public class ReadTwoFragment extends Fragment{

    PoemDataManger mPoemDataManger;
    PoemAdapter poemadapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate( R.layout.read_two_fragment, container, false );

        RecyclerView poemTitleRecyclerView = (RecyclerView)view.findViewById( R.id.poem_title_recycler_view );
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        poemTitleRecyclerView.setLayoutManager(layoutManager);
        poemadapter=new PoemAdapter(2);

        poemTitleRecyclerView.setAdapter(poemadapter);

        setHasOptionsMenu(true);
        registerForContextMenu( poemTitleRecyclerView );//注册上下文菜单
        mPoemDataManger=new PoemDataManger(getActivity());
        return view;
    }
    public void onDestroy(){
        super.onDestroy();
        mPoemDataManger.closeDataBase();
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        new MenuInflater(getActivity()).inflate(R.menu.menu_read, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    public boolean onContextItemSelected(MenuItem item){
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        PoemData poemdata;
        int id;
        if (getUserVisibleHint()) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    int r = poemadapter.getPosition();
                    poemdata = poemadapter.getItem( poemadapter.getPosition() );
                    id = poemdata.getPoemId();
                    DeleteDialog( r, id );
                    break;
                case R.id.action_collect:
                    poemdata = poemadapter.getItem( poemadapter.getPosition() );
                    mPoemDataManger.openDataBase();
                    mPoemDataManger.insertPoemData(poemdata);
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
                                poemadapter.removeItem( r );
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
