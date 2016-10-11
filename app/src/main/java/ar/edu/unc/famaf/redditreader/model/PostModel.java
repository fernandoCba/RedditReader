package ar.edu.unc.famaf.redditreader.model;


import java.util.Date;

public class PostModel {
    private String mTitle;
    private String mAuthor;
    private String mCreatedOn;



    private int mComments;
    private int mImage;

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

    public int getComments() {
        return mComments;
    }

    public void setComments(int mComments) {
        this.mComments = mComments;
    }
    public int getImage() {
        return mImage;
    }

    public void setImageUrl(int mImageUrl) {
        this.mImage = mImageUrl;
    }
}
