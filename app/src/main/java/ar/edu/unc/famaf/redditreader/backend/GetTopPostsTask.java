package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;
import ar.edu.unc.famaf.redditreader.utils.Utils;

public class GetTopPostsTask extends AsyncTask<Void, Integer, List<PostModel>> {
    private Context mContext;

    public GetTopPostsTask(Context context) {
        mContext = context;
    }

    @Override
    protected List<PostModel> doInBackground(Void... params) {
        RedditDBHelper dbHelper = new RedditDBHelper(mContext);
        if (new Utils(mContext).checkInternetConnection()){
            Listing listing = getTopPostsListing();
            dbHelper.persistListing(listing);
        }
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
