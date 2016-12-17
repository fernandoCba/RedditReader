package ar.edu.unc.famaf.redditreader.backend;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ar.edu.unc.famaf.redditreader.model.CommentModel;


public class CommentsParser {
    private final String TAG = "REDDITREADER_PARSER";
    JsonReader mReader;

    public CommentModel readJsonStream(InputStream in) throws IOException {
        mReader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        CommentModel comments = null;
        try {
            mReader.beginArray();
            //skip the first element of the array.
            mReader.beginObject();
            while (mReader.hasNext())
                mReader.skipValue();
            mReader.endObject();

            comments = new CommentModel();
            parseListing(comments);
            mReader.endArray();
        } catch (IOException e) {
            Log.e(TAG, "JSon Wrong formatted");
            comments = null;
        } finally {
            mReader.close();
        }
        return comments;
    }

    private void parseListing(CommentModel comments) throws IOException {
        mReader.beginObject();
        while (mReader.hasNext()) {
            String name = mReader.nextName();
            if (!name.equalsIgnoreCase("data"))
                mReader.skipValue();
            else //get comments data
            {
                mReader.beginObject();
                while (mReader.hasNext()) {
                    if (!mReader.nextName().equalsIgnoreCase("children"))
                        mReader.skipValue();
                    else {
                        mReader.beginArray();
                        while (mReader.hasNext())
                            parseComments(comments);
                        mReader.endArray();
                    }
                }
                mReader.endObject();
            }
        }
        mReader.endObject();
    }

    private void parseComments(CommentModel comments) throws IOException {
        mReader.beginObject();
        while (mReader.hasNext()) {
            if (!mReader.nextName().equalsIgnoreCase("data"))
                mReader.skipValue();
            else //get comments data
            {
                CommentModel newComment = new CommentModel();
                mReader.beginObject();
                while (mReader.hasNext()) {
                    String commentKey = mReader.nextName();
                    if (commentKey.equalsIgnoreCase("author"))
                        newComment.setAuthor(mReader.nextString());
                    else if (commentKey.equalsIgnoreCase("created_utc"))
                        newComment.setCreated(mReader.nextString());
                    else if (commentKey.equalsIgnoreCase("body"))
                        newComment.setBody(mReader.nextString());
                    else if (commentKey.equalsIgnoreCase("replies") && mReader.peek() == JsonToken.BEGIN_OBJECT)
                        parseListing(newComment);
                    else
                        mReader.skipValue();
                }
                if (newComment.getmAuthor() != null)
                    comments.getSubComments().add(newComment);

                mReader.endObject();
            }
        }
        mReader.endObject();
    }
}
