package ar.edu.unc.famaf.redditreader.model;


import java.util.Date;

public class PostModel {
    private String mTitle;
    private String mAuthor;
    private String mCreatedOn;
    private String mImageUrl;

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

    public String getCreatedOn() {
        return mCreatedOn;
    }

    public void setCreatedOn(String mCreatedOn) {
        this.mCreatedOn = mCreatedOn;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
