package ar.edu.unc.famaf.redditreader.backend;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ar.edu.unc.famaf.redditreader.model.Listing;

public class GetTopPostsTask extends AsyncTask<Void, Integer, Listing> {
    final int LIMIT;
    static String after = "";
    private Listing mListing = null;

    public GetTopPostsTask(Listing listing, int limit) {
        if (listing != null) {
            after = listing.getAfter();
        }
        LIMIT = limit;
    }

    @Override
    protected Listing doInBackground(Void... params) {
        Listing l = null;
        try {
            String url = String.format("https://www.reddit.com/top.json?limit=%s&after=%s", LIMIT, after);
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            InputStream inputStream = conn.getInputStream();
            l = new Parser().readJsonStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        after = l.getAfter();
        return l;
    }
}
