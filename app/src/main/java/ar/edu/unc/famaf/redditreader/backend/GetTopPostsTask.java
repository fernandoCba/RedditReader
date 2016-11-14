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
import ar.edu.unc.famaf.redditreader.utils.Utils;

public class GetTopPostsTask extends AsyncTask<Void, Integer, Listing> {
    final int LIMIT = 25;
    int count = 0;
    static String after = "";
    private Context mContext;
    private Listing mListing = null;

    public GetTopPostsTask(Context context, Listing listing) {
        if(listing != null){
            count = listing.getCount();
            after = listing.getAfter();
        }
        mContext = context;
    }

    @Override
    protected Listing doInBackground(Void... params) {
        RedditDBHelper dbHelper = new RedditDBHelper(mContext);
        Listing listing = null;
        if (new Utils(mContext).checkInternetConnection()) {
            listing = getTopPostsListing();
            dbHelper.persistListing(listing);
        }
        else
            listing = new Listing();

        listing.setPosts(dbHelper.getTopPostsFromDB());
        return listing;
    }

    private Listing getTopPostsListing() {
        Listing l = null;
        try {
            String url = String.format("https://www.reddit.com/top.json?limit=%s&count=%s&after=%s", LIMIT, count, after);
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            InputStream inputStream = conn.getInputStream();
            l = new Parser().readJsonStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        count += LIMIT;
        after = l.getAfter();
        return l;
    }


    public Listing getListing() {
        return mListing;
    }
}
