package ar.edu.unc.famaf.redditreader.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ar.edu.unc.famaf.redditreader.R;


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

    public void updateTitle(String title){
        TextView text = (TextView) getView().findViewById(R.id.news_detail_title);
        text.setText(title);
    }

}
