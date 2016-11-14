package ar.edu.unc.famaf.redditreader.model;

import java.util.ArrayList;
import java.util.List;

public class Listing {
    String before;
    String after;
    int mCount = 0;
    
    List<PostModel> posts = new ArrayList<PostModel>();

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        this.mCount = count;
    }

    public List<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(List<PostModel> posts) {
        this.posts = posts;
    }

}
