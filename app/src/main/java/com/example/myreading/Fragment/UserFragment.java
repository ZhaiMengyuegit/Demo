package com.example.myreading.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.example.myreading.CollectActivity;
import com.example.myreading.EditUserActivity;
import com.example.myreading.LoginActivity;
import com.example.myreading.R;
import com.example.myreading.Utils.TimeCount;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class UserFragment extends Fragment implements View.OnClickListener {
    TextView jinriyuedu,edit,collect,addition,total,exit;


    Spinner spinner;
    //List<String> data_list;
    //ArrayAdapter<String> arr_adapter;
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                //获取旧时间
                long time_old = TimeCount.getInstance().getTime();
                //得到新时间
                long time_new = System.currentTimeMillis();
                TimeCount.getInstance().setTime(time_new);

                int time = (int) (time_new - time_old) / 1000 / 60;

                jinriyuedu.setText("今日阅读" + time + "分钟");

            }
        }
    };
    final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
                try {
                    Thread.sleep(60000);
                    //每分钟更新一次
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user, container, false);

        edit = (TextView)view.findViewById(R.id.user_edit_mine);
        collect = (TextView) view.findViewById(R.id.user_collect);
        addition = (TextView) view.findViewById(R.id.user_addition);
        total = (TextView) view.findViewById(R.id.user_total);
        exit =(TextView)view.findViewById(R.id.user_exit_login);
        jinriyuedu =(TextView) view.findViewById(R.id.jinriyuedu);

        SharedPreferences pref = getContext().getSharedPreferences("data", MODE_PRIVATE);
        int n = pref.getInt("total", 0);
        total.setText(pref.getInt("total", n) + "");

        edit.setOnClickListener(this);
        addition.setOnClickListener(this);
        collect.setOnClickListener(this);
        exit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_edit_mine:
                Intent edit_intent = new Intent(getContext(), EditUserActivity.class);
                startActivity(edit_intent);
                break;
             case R.id.user_collect:
              CollectActivity.actionStart(getContext());
               break;
            case R.id.user_addition:
                InsertDialog();
                break;
            case R.id.user_exit_login:
                Intent login_intent = new Intent(getContext(), LoginActivity.class);
                startActivity(login_intent);
                break;

            default:
                break;
        }
    }

    private void InsertDialog() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.insert, null);
        new AlertDialog.Builder(getContext())
                .setTitle("新增文章")
                .setView(tableLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strTitle = ((EditText) tableLayout.findViewById(R.id.insert_edit_title)).getText().toString();
                        String strContent = ((EditText) tableLayout.findViewById(R.id.insert_edit_content)).getText().toString();
                        String strtype = "";
                        spinner = (Spinner) tableLayout.findViewById(R.id.spinner);

                        String t = spinner.getSelectedItem().toString();
                        switch (t) {
                            case "诗":
                                strtype = 1 + "";
                                break;
                            case "句":
                                strtype = 2 + "";
                                break;
                            case "文":
                                strtype = 3 + "";
                                break;
                        }

                        Insert(strTitle, strContent, strtype);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()
                .show();
    }

    private void Insert(String strtitle, String strcontent, String strtype) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        int strtypes = Integer.parseInt(strtype);
        String path = "http://192.168.43.93:8080/Web/Test3";
     /*  HttpUtil.addition(strtitle,strcontent,strtypes,path,new ReadOneFragment.HttpCallbackListener(){
            @Override
            public void onFinish(String response) {//成功时的处理方法
                Intent intent=new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
            @Override
            public void onError(Exception e){//失败时的处理方法

            }
        });

      */




    }
}