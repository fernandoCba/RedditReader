package ar.edu.unc.famaf.redditreader.backend;

import ar.edu.unc.famaf.redditreader.model.Listing;

public class Backend {
    private static Backend ourInstance = new Backend();
    private Listing mLastListing = null;

    private Backend() {
    }

    public static Backend getInstance() {
        return ourInstance;
    }

    public void getNextPosts(final PostsIteratorListener listener) {
        GetTopPostsTask topPostsTask = new GetTopPostsTask(listener.getContext(), mLastListing) {
            @Override
            protected void onPostExecute(Listing listing) {
                super.onPostExecute(listing);
                if (listing != null) {
                    mLastListing = listing;
                    listener.nextPosts(listing.getPosts());
                }
            }
        };

        topPostsTask.execute();
    }

}
