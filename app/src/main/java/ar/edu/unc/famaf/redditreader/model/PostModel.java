package ar.edu.unc.famaf.redditreader.model;


import java.io.Serializable;

public class PostModel implements Serializable {
    private String mTitle;
    private String mAuthor;
    private long mCreatedOn;
    private int mComments;
    private String mImage;

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
}
