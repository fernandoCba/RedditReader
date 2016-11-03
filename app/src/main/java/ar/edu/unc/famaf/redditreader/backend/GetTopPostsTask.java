package ar.edu.unc.famaf.redditreader.backend;

import android.os.AsyncTask;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class GetTopPostsTask extends AsyncTask<Void, Integer, List<PostModel>> {
    @Override
    protected List<PostModel> doInBackground(Void... params) {
        Listing l = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL("https://www.reddit.com/top.json").openConnection();
            conn.setRequestMethod("GET");
            InputStream inputStream = conn.getInputStream();
             l = new Parser().readJsonStream(inputStream);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        if(l!= null)
            return l.getPosts();
        else
            return null;
    }
}
