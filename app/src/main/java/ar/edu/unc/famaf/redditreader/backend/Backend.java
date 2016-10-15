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
        int[] images = new int[]{R.drawable.carrots, R.drawable.legs, R.drawable.dog};
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
