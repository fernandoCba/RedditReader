package ar.edu.unc.famaf.redditreader.backend;

import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class Backend {
    private static Backend ourInstance = new Backend();
    private GetTopPostsTask topPostsTask = null;
    private Listing mLastListing = null;

    private Backend() {
    }

    public static Backend getInstance() {
        return ourInstance;
    }

    public void getNextPosts(final PostsIteratorListener listener) {
        if (topPostsTask == null)
            topPostsTask = new GetTopPostsTask(listener.getContext(), mLastListing) {
                @Override
                protected void onPostExecute(Listing listing) {
                    super.onPostExecute(listing);
                    if(listing!=null) {
                        mLastListing = listing;
                        listener.nextPosts(listing.getPosts());
                    }
                }
            };

        topPostsTask.execute();
    }

}
