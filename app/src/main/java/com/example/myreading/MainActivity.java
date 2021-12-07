package com.example.myreading;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myreading.Fragment.ReadMainFragment;
import com.example.myreading.Fragment.UserFragment;
import com.example.myreading.Utils.MessageEvent;

import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    ReadMainFragment fragment1=new ReadMainFragment();
    UserFragment fragment2=new UserFragment();
    Fragment mContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getSupportActionBar().hide();
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        TextView one=(TextView) findViewById( R.id.bottom_one );
        TextView two=(TextView) findViewById( R.id.bottom_two );
        one.setOnClickListener( this );
        two.setOnClickListener( this );
        mContent=fragment1;
        switchContent(fragment1);
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.bottom_one:
                //replaceFragment( fragment1 );
                switchContent(fragment1);
                break;
            case R.id.bottom_two:
                //replaceFragment( fragment2 );
                switchContent(fragment2);
                break;
            default:
                break;
        }
    }
    private void switchContent(Fragment to) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(to!=mContent){
            if (!to.isAdded()) { // 判断是否被add过
                // 隐藏当前的fragment，将 下一个fragment 添加进去
                transaction.hide(mContent).add(R.id.main_layout, to).show(to).commit();
            } else {
                // 隐藏当前的fragment，显示下一个fragment
                transaction.hide(mContent).show(to).commit();
            }
            mContent = to;
        }else{
            if (!to.isAdded()) { // 判断是否被add过
                // 隐藏当前的fragment，将 下一个fragment 添加进去
                transaction.add(R.id.main_layout, to).show(to).commit();
            }

        }

    }
    /*public void replaceFragment(Fragment fragment){//会重新过一遍activity的周期，推荐用上面那个
        FragmentManager fragmentManager=getSupportFragmentManager();
        //Right_Fragment rightFragment=(Right_Fragment)getSupportFragmentManager().findFragmentById( R.id.fragment_right_ ); //从布局文件当中获取碎片
        FragmentTransaction transaction=fragmentManager.beginTransaction();//开启事件
        //transaction.show( fragment );
        transaction.replace( R.id.main_layout,fragment );
        transaction.addToBackStack( null );//设置可返回
        transaction.commit();//提交事件
    }

     */
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (event.getCode() == 1000){
            Log.e("TAG","MainActivity");
            MainActivity.this.finish();
        }
    }
}

