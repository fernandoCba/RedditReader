package ar.edu.unc.famaf.redditreader.backend;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public List<PostModel> getTopPosts() {
        List<PostModel> list = new ArrayList<PostModel>();
        String[] images = new String[]{
                "http://b.thumbs.redditmedia.com/sAL2NHm198e7bmk4MjFlMotYJPbVVZCk11pDcdJuliU.jpg",
                "http://a.thumbs.redditmedia.com/TyMvfQ0OJNikhHObKMrA9JUXEQbNFsAeuXK9GJx4BH4.jpg",
                "http://b.thumbs.redditmedia.com/xusZjnsKKM_0lWHtpyGoUtFYBsDs_sTZwLVb8nCMI2Y.jpg"};
        PostModel p;
        for (int i = 0; i < 5; i++) {
            p = new PostModel();
            p.setAuthor("author " + i);
            p.setCreatedOn("10/09/2016");
            p.setTitle("Title " + i);
            p.setImageUrl(images[i%images.length]);
            p.setComments(i*5);
            list.add(p);
        }
        return list;
    }
}
