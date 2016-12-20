package ar.edu.unc.famaf.redditreader.backend;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class Parser {
    private final String TAG = "REDDITREADER_PARSER";
    JsonReader mReader;

    public Listing readJsonStream(InputStream in) throws IOException {
        mReader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        Listing listing = null;
        try {
            mReader.beginObject();
            while (mReader.hasNext()) {
                if (mReader.nextName().equalsIgnoreCase("data"))
                    listing = readListing();
                else
                    mReader.skipValue();
            }
            mReader.endObject();

        } catch (IOException e) {
            Log.e(TAG, "Wrong formatted json");
            listing = null;
        } finally {
            mReader.close();
        }
        return listing;
    }

    private Listing readListing() throws IOException {
        Listing listing = new Listing();
        mReader.beginObject();
        while (mReader.hasNext()) {
            String name = mReader.nextName();
            if (name.equalsIgnoreCase("after") && mReader.peek() != JsonToken.NULL)
                listing.setAfter(mReader.nextString());
            else if (name.equalsIgnoreCase("children"))
                readPostsArray(listing);
            else
                mReader.skipValue();
        }

        mReader.endObject();
        return listing;
    }

    private void readPostsArray(Listing listing) throws IOException {
        mReader.beginArray();
        while (mReader.hasNext()) {
            mReader.beginObject();
            while (mReader.hasNext()) {
                String name = mReader.nextName();
                if (name.equalsIgnoreCase("data"))
                    readPost(listing);
                else
                    mReader.skipValue();
            }
            mReader.endObject();
        }
        mReader.endArray();
    }

    private void readPost(Listing listing) throws IOException {
        PostModel p = new PostModel();
        mReader.beginObject();
        while (mReader.hasNext()) {
            String name = mReader.nextName();
            if (name.equalsIgnoreCase("author"))
                p.setAuthor(mReader.nextString());
            else if (name.equalsIgnoreCase("created"))
                p.setCreatedOn(mReader.nextLong());
            else if (name.equalsIgnoreCase("id"))
                p.setId(mReader.nextString());
            else if (name.equalsIgnoreCase("title"))
                p.setTitle(mReader.nextString());
            else if (name.equalsIgnoreCase("thumbnail"))
                p.setImageUrl(mReader.nextString());
            else if (name.equalsIgnoreCase("num_comments"))
                p.setComments(mReader.nextInt());
            else if (name.equalsIgnoreCase("subreddit"))
                p.setSubreddit(mReader.nextString());
            else if (name.equalsIgnoreCase("url"))
                p.setUrl(mReader.nextString());
            else if (name.equalsIgnoreCase("preview"))
                readPreview(p);
            else
                mReader.skipValue();
        }
        mReader.endObject();
        Log.i(TAG, p.toString());
        listing.getPosts().add(p);

    }

    private void readPreview(PostModel p) throws IOException {
        mReader.beginObject();//preview
        while (mReader.hasNext()) {
            String name = mReader.nextName();
            if (name.equalsIgnoreCase("images"))
                readPreviewImages(p);
            else
                mReader.skipValue();
        }
        mReader.endObject();
    }

    private void readPreviewImages(PostModel p) throws IOException {
        mReader.beginArray();
        while (mReader.hasNext()) {
            mReader.beginObject();
            while (mReader.hasNext()) {
                if (mReader.nextName().equalsIgnoreCase("source")) {
                    mReader.beginObject();
                    while (mReader.hasNext()) {
                        if (mReader.nextName().equalsIgnoreCase("url"))
                            p.setPreview(mReader.nextString());
                        else
                            mReader.skipValue();
                    }
                    mReader.endObject();
                } else
                    mReader.skipValue();
            }
            mReader.endObject();
        }

        mReader.endArray();
    }
}