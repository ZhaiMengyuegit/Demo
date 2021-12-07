package com.example.myreading.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import com.example.myreading.Adapter.SentenceAdapter;
import com.example.myreading.Entity.SentenceData;
import com.example.myreading.Utils.SentenceDataManger;
import com.example.myreading.R;

public class ReadOneFragment extends Fragment{

    SentenceDataManger mSentenceDataManger;
    SentenceAdapter sentenceadapter;
    private ProgressDialog mDialog;
   //private List<SentenceData> sentenceDataList;
    //private MyDatabaseHelper helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate( R.layout.read_one_fragment, container, false );


        RecyclerView sentenceSourceRecyclerView = (RecyclerView)view.findViewById( R.id.sentence_source_recycler_view );
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        sentenceSourceRecyclerView.setLayoutManager(layoutManager);
        sentenceadapter=new SentenceAdapter(1);

        sentenceSourceRecyclerView.setAdapter(sentenceadapter);

        setHasOptionsMenu(true);
        registerForContextMenu( sentenceSourceRecyclerView );//注册上下文菜单
        mSentenceDataManger=new SentenceDataManger(getActivity());




        //helper = new MyDatabaseHelper(getContext(), "data.db", null, 2);
        return view;
    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        new MenuInflater(getActivity()).inflate(R.menu.menu_read, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    public boolean onContextItemSelected(MenuItem item){
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        SentenceData sentencedata;
        int id;
        if (getUserVisibleHint()) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    int r = sentenceadapter.getPosition();
                    sentencedata = sentenceadapter.getItem( sentenceadapter.getPosition() );
                    id = sentencedata.getSentenceId();
                      DeleteDialog( r, id );
                    break;
                case R.id.action_collect:
                    sentencedata = sentenceadapter.getItem( sentenceadapter.getPosition() );
                    mSentenceDataManger.openDataBase();
                    mSentenceDataManger.insertSentenceData(sentencedata);
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
                                sentenceadapter.removeItem( r );
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



}
