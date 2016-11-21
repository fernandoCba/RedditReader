package ar.edu.unc.famaf.redditreader.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ar.edu.unc.famaf.redditreader.R;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        String title = getIntent().getStringExtra(NewsActivity.EXTRA_POST_MODEL);
        NewsDetailActivityFragment detailsFrag = (NewsDetailActivityFragment)
                getSupportFragmentManager().findFragmentById(R.id.news_detail_fragment);
        detailsFrag.updateTitle(title);


    }
}
