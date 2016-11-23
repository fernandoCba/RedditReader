package ar.edu.unc.famaf.redditreader.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        PostModel post = (PostModel) getIntent().getSerializableExtra(NewsActivity.EXTRA_POST_MODEL);
        NewsDetailActivityFragment detailsFrag = (NewsDetailActivityFragment)
                getSupportFragmentManager().findFragmentById(R.id.news_detail_fragment);
        detailsFrag.updateContent(post);


    }
}
