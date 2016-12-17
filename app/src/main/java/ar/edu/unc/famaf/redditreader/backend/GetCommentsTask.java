package ar.edu.unc.famaf.redditreader.backend;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ar.edu.unc.famaf.redditreader.model.CommentModel;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class GetCommentsTask extends AsyncTask<Void, Integer, CommentModel> {
    PostModel mPost;
    private static String TAG = "REDDIT_COMMENTSPARSER";

    public GetCommentsTask(PostModel post) {
        mPost = post;
    }

    @Override
    protected CommentModel doInBackground(Void... params) {
        CommentModel comments = null;
        try {
            String url = String.format("https://www.reddit.com/comments/%s.json", mPost.getId());
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            InputStream inputStream = conn.getInputStream();
            comments = new CommentsParser().readJsonStream(inputStream);
        } catch (IOException e) {
            Log.e(TAG, "Comments couldn't be parsed. PostId: " + mPost.getId());
        }
        return comments;
    }
}
