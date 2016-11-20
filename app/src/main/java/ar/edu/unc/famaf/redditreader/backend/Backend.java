package ar.edu.unc.famaf.redditreader.backend;

import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;
import ar.edu.unc.famaf.redditreader.utils.Utils;

public class Backend {
    private static Backend ourInstance = new Backend();
    private Listing mLastListing = null;
    //number of elements read from internet
    private final int ITEMS_TO_READ = 25; //number of elements to read from Internet
    private int mItemsRead = 0;
    private int dbItemsRead = 0; //number of items read from database
    private final int SCROLLING_PAGE_SIZE = 5;

    private Backend() {
    }

    public static Backend getInstance() {
        return ourInstance;
    }

    public void getNextPosts(final PostsIteratorListener listener) {
        final RedditDBHelper dbHelper = new RedditDBHelper(listener.getContext());
        if (mItemsRead <= dbItemsRead && new Utils(listener.getContext()).checkInternetConnection()) {
            GetTopPostsTask topPostsTask = new GetTopPostsTask(mLastListing, ITEMS_TO_READ) {
                @Override
                protected void onPostExecute(Listing listing) {
                    super.onPostExecute(listing);
                    if (listing != null) {
                        mLastListing = listing;
                        dbHelper.persistListing(listing);
                        mItemsRead = listing.getPosts().size();
                        dbItemsRead = 0;
                        returnNextFives(listener, dbHelper);
                    }
                }
            };

            topPostsTask.execute();
        } else
            returnNextFives(listener, dbHelper);

    }

    private void returnNextFives(final PostsIteratorListener listener, RedditDBHelper dbHelper) {
        List<PostModel> list = dbHelper.getTopPostsFromDB(SCROLLING_PAGE_SIZE,dbItemsRead);
        listener.nextPosts(list);
        dbItemsRead += list.size();

    }

}
