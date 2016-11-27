package ar.edu.unc.famaf.redditreader.model;


import android.text.format.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class PostModel implements Serializable {
    private String mTitle;
    private String mAuthor;
    private long mCreatedOn;
    private int mComments;
    private String mImage;
    private String mPreview;
    private String mSubreddit;
    private String mUrl;

    public String getSubreddit() {
        return mSubreddit;
    }

    public void setSubreddit(String subreddit) {
        this.mSubreddit = subreddit;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public long getCreatedOn() {
        return mCreatedOn;
    }

    public void setCreatedOn(long mCreatedOn) {
        this.mCreatedOn = mCreatedOn;
    }

    public int getComments() {
        return mComments;
    }

    public void setComments(int mComments) {
        this.mComments = mComments;
    }

    public String getImage() {
        return mImage;
    }

    public void setImageUrl(String mImageUrl) {
        if (mImageUrl != null && mImageUrl.contains("http"))
            this.mImage = mImageUrl;
    }

    public String getElapsedTime() {
        CharSequence elapsed = DateUtils.getRelativeTimeSpanString(mCreatedOn * 1000, new Date().getTime(), DateUtils.HOUR_IN_MILLIS, 0);
        return elapsed.toString();
    }

    public String getPreview() {
        return mPreview;
    }

    public void setPreview(String preview) {
        this.mPreview = preview;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    @Override
    public String toString(){
        String res = String.format("Title:\t%s\n" +
                "Author:\t%s\n" +
                "CreatedOn:\t%s\n" +
                "Comments:\t%s\n" +
                "Image:\t%s\n" +
                "Preview:\t%s\n" +
                "Subreddit:\t%s\n",
                getTitle(), getAuthor(), getCreatedOn(), getComments(), getImage(), getPreview(), getSubreddit());
        return res;
    }
}
