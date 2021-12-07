package com.example.myreading.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myreading.R;

public class ArticleContentFragment extends Fragment {
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        view = inflater.inflate(R.layout.article_content_frag, container, false);
        return view;
    }

    public void refresh(String articletitle, String articleauthor, String articlechapter, String articlecontent, String articlenote, String articletanlation) {
        TextView articletitleText = view.findViewById(R.id.article_content_title);
        TextView articleauthorText = view.findViewById(R.id.article_content_author);
        TextView articlechapterText = view.findViewById(R.id.article_content_chapter);
        TextView articlecontentText = view.findViewById(R.id.article_content_content);
        TextView articlenoteText = view.findViewById(R.id.article_content_note);
        TextView articletanslationText = view.findViewById(R.id.article_content_tanslation);
        // int t=0;
        articletitleText.setText(articletitle);
        articleauthorText.setText(articleauthor);
        articlechapterText.setText(articlechapter);
        articlecontentText.setText(articlecontent);
        articlenoteText.setText(articlenote);
        articletanslationText.setText(articletanlation);
    }
}
