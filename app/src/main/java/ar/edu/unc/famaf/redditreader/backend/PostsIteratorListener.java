package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;

import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

/**
 * Created by Fernando on 12/11/2016.
 */

public interface PostsIteratorListener {
    void nextPosts(List<PostModel> posts);

    Context getContext();
}
