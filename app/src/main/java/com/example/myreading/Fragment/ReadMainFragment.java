package com.example.myreading.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.myreading.R;

import java.util.ArrayList;
import java.util.List;

public class ReadMainFragment extends Fragment implements View.OnClickListener {

    ViewPager read_vp;
    TextView read_one;
    TextView read_two;
    TextView read_three;


    //static ProgressDialog progressDialog ;

   // PullToRefreshView pullToRefreshView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);


        View view = inflater.inflate(R.layout.read, container, false);
        read_vp = (ViewPager) view.findViewById(R.id.read_vp);
        read_one=(TextView) view.findViewById( R.id.read_one );
        read_two=(TextView) view.findViewById( R.id.read_two );
        read_three=(TextView) view.findViewById( R.id.read_three );

        initias();
        class MyAdapter extends FragmentPagerAdapter {
            List<Fragment> fragments;

            public MyAdapter(FragmentManager fm, List<Fragment> fragments) {
                super(fm);
                this.fragments = fragments;
                // TODO Auto-generated constructor stub
            }

            @Override
            public Fragment getItem(int position) {
                // TODO Auto-generated method stub
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return fragments.size();
            }

        }

        List<Fragment> fragments = new ArrayList<Fragment>();

        fragments.add(new ReadOneFragment());
        fragments.add(new ReadTwoFragment());
        fragments.add(new ReadThreeFragment());

        PagerAdapter adapter = new MyAdapter(getChildFragmentManager(),
                fragments);
        read_vp.setAdapter(adapter);
        read_vp.setCurrentItem( 0 );
        read_vp.setOffscreenPageLimit( 10 );
        read_vp.setOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                int currentItem=read_vp.getCurrentItem();
                switch(currentItem){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        } );
        return view;
    }

    public void initias(){
        read_one.setOnClickListener( this );
        read_two.setOnClickListener( this );
        read_three.setOnClickListener( this );
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.read_one:
                read_vp.setCurrentItem( 0 );
                break;
            case R.id.read_two:
                read_vp.setCurrentItem( 1 );
                break;
            case R.id.read_three:
                read_vp.setCurrentItem( 2 );
                break;
            default:
                break;
        }


    }
    // pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {             public void onRefresh() {         switch (tagPointer){                  case 0:                      fragment1.GetNews();                      break;                  case 1:                      fragment2.GetNews();                      break;                  case 2:                      fragment3.GetNews();                      break;                  case 3:                      fragment4.GetNews();                      break;                  case 4:                      fragment5.GetNews();                      break;                  case 5:                      break;              }              pullToRefreshView.setRefreshing(false);            }        });    } }

}
