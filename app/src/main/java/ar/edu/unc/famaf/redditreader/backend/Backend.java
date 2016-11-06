package ar.edu.unc.famaf.redditreader.backend;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

public class Backend {
    private static final String TAG = "redditreader.backend";
    private static final String END_POINT = "https://www.reddit.com/";
    private static Backend ourInstance = new Backend();

    private Backend() {
    }

    public static Backend getInstance() {
        return ourInstance;
    }

}
