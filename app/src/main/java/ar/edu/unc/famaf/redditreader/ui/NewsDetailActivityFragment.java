package ar.edu.unc.famaf.redditreader.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsDetailActivityFragment extends Fragment {


    public NewsDetailActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_detail_activity, container, false);
    }

    public void updateContent(PostModel post) {
        TextView subreddit = (TextView) getView().findViewById(R.id.details_subreddit);
        subreddit.setText("/r/" + post.getSubreddit());

        TextView title = (TextView) getView().findViewById(R.id.details_title);
        title.setText(post.getTitle());

        TextView author = (TextView) getView().findViewById(R.id.details_author);
        author.setText(post.getAuthor());

        TextView created = (TextView) getView().findViewById(R.id.details_date);
        created.setText(post.getElapsedTime());

    }

}
