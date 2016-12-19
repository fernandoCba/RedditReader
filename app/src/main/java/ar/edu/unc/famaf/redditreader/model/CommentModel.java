package ar.edu.unc.famaf.redditreader.model;

import android.text.format.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentModel {
    String mAuthor;
    String mBody;
    long mCreated;
    int mDepth = -1;
    List<CommentModel> mSubComments = new ArrayList<CommentModel>();


    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        this.mBody = body;
    }

    public List<CommentModel> getSubComments() {
        return mSubComments;
    }

    public long getCreated() {
        return mCreated;
    }

    public int getDepth()
    {
        return mDepth;
    }

    public String getElapsedTime() {
        CharSequence elapsed = DateUtils.getRelativeTimeSpanString(mCreated * 1000, new Date().getTime(), DateUtils.HOUR_IN_MILLIS, 0);
        return elapsed.toString();
    }

    public List<CommentModel> getFlatList() {
        List<CommentModel> list = new ArrayList<CommentModel>();
        dfTree(this, list);
        return list;
    }

    private void dfTree(CommentModel c, List<CommentModel> dfList) {
        if (c.getAuthor() != null)//don't add the root
            dfList.add(c);
        for (CommentModel child : c.mSubComments) {
            child.mDepth = c.mDepth + 1;
            dfTree(child, dfList);
        }
    }

    public void setCreated(long created) {
        this.mCreated = created;
    }
}
