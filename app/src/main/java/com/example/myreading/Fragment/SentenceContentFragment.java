package com.example.myreading.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myreading.R;

public class SentenceContentFragment extends Fragment {
    private View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        view = inflater.inflate(R.layout.sentence_content_frag, container, false);
        return view;
    }
    public void refresh(String sentencesource,String sentencecontent){
        TextView sentencesourceText=view.findViewById( R.id.sentence_content_source);
        TextView sentencecontentText=view.findViewById(R.id.sentence_content_content);
        int t=0;
        sentencesourceText.setText( sentencesource);
        sentencecontentText.setText(sentencecontent);


    }
}
