package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class GetTopPostsTask extends AsyncTask<Void, Integer, List<PostModel>> {
    private Context mContext;

    public GetTopPostsTask(Context context) {
        mContext = context;
    }

    @Override
    protected List<PostModel> doInBackground(Void... params) {
        Listing listing = getTopPostsListing();
        RedditDBHelper dbHelper = new RedditDBHelper(mContext);
        dbHelper.persistListing(listing);
        return dbHelper.getTopPostsFromDB();
    }

    private Listing getTopPostsListing() {
        Listing l = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL("https://www.reddit.com/top.json").openConnection();
            conn.setRequestMethod("GET");
            InputStream inputStream = conn.getInputStream();
            l = new Parser().readJsonStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return l;
    }
}
