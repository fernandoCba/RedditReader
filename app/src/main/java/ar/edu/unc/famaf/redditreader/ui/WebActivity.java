package ar.edu.unc.famaf.redditreader.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        PostModel post = (PostModel) getIntent().getSerializableExtra(NewsDetailActivity.EXTRA_POST_MODEL);
        WebView web = (WebView) findViewById(R.id.web_component);
        web.loadUrl(post.getUrl());
    }
}
