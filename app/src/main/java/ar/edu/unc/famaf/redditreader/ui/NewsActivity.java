package ar.edu.unc.famaf.redditreader.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.GetTopPostsTask;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class NewsActivity extends AppCompatActivity {
    static final int LOGIN_REQUEST = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!checkInternetConnection()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.error_no_internet_connection));
            builder.create().show();
            return;
        }

        GetTopPostsTask topPostsTask = new GetTopPostsTask(this) {
            @Override
            protected void onPostExecute(List<PostModel> postModels) {
                if (postModels != null) {
                    PostAdapter ad = new PostAdapter(NewsActivity.this, R.layout.fragment_news, postModels);
                    ListView postsLV = (ListView) findViewById(R.id.postsListView);
                    postsLV.setAdapter(ad);
                }
            }
        };

        topPostsTask.execute();
    }

    private boolean checkInternetConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sign_in) {
            Intent newIntent = new Intent(this, LoginActivity.class);
            startActivityForResult(newIntent, LOGIN_REQUEST);
            //NewsActivityFragment newsfragment = (NewsActivityFragment)
            //        getSupportFragmentManager().findFragmentById(R.id.news_activity_fragment_id);
            //TextView textView = (TextView) findViewById(R.id.loginStatusTextView);
            //textView.setText("User XXXX logged in");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If the request went well (OK) and the request was PICK_CONTACT_REQUEST
        if (requestCode == LOGIN_REQUEST) {
            NewsActivityFragment newsFragment = (NewsActivityFragment)
                    getSupportFragmentManager().findFragmentById(R.id.news_activity_fragment_id);
            TextView textView = (TextView) findViewById(R.id.loginStatusTextView);

            if (resultCode == Activity.RESULT_OK) {
                String userName = data.getStringExtra(LoginActivity.USERNAME);
                textView.setText("User " + userName + " logged in");
            } else
                textView.setText("User is not logged in");
        }
    }
}
