package ar.edu.unc.famaf.redditreader.model;

import java.util.ArrayList;
import java.util.List;

public class CommentModel {
    String mAuthor;
    String mBody;
    String mCreated;
    List<CommentModel> mSubComments = new ArrayList<CommentModel>();


    public String getmAuthor() {
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

    public List<CommentModel> getSubComments(){
        return mSubComments;
    }
    public String getCreated() {
        return mCreated;
    }

    public void setCreated(String created) {
        this.mCreated = created;
    }
}
