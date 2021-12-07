package com.example.myreading.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.myreading.R;


public class PoemContentFragment extends Fragment {
    private View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        view = inflater.inflate(R.layout.poem_content_frag, container, false);
        return view;
    }
    public void refresh(String poemtitle,String poemauthor,String poemkind,String poemcontent,String poemintro){
        TextView poemtitleText=view.findViewById( R.id.poem_content_title);
        TextView poemauthorText=view.findViewById(R.id.poem_content_author);
        TextView poemkindText=view.findViewById(R.id.poem_content_kind);
        TextView poemcontentText=view.findViewById( R.id.poem_content_content );
        TextView poemintroText=view.findViewById( R.id.poem_content_intro );
        poemtitleText.setText( poemtitle );
        poemauthorText.setText(poemauthor);
        poemkindText.setText(poemkind);
        poemcontentText.setText( poemcontent );
        poemintroText.setText( poemintro );
    }
}
