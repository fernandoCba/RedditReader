package ar.edu.unc.famaf.redditreader.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.backend.EndlessScrollListener;
import ar.edu.unc.famaf.redditreader.backend.GetTopPostsTask;
import ar.edu.unc.famaf.redditreader.backend.PostsIteratorListener;
import ar.edu.unc.famaf.redditreader.model.PostModel;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment implements PostsIteratorListener
{
    private PostAdapter mPostAdapter;
    public NewsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GetTopPostsTask topPostsTask = new GetTopPostsTask(getActivity()) {
            @Override
            protected void onPostExecute(List<PostModel> postModels) {
                if (postModels != null) {
                    PostAdapter ad = new PostAdapter(getActivity(), R.layout.fragment_news, postModels);
                    ListView postsLV = (ListView) getActivity().findViewById(R.id.postsListView);
                    postsLV.setAdapter(ad);
                }
            }
        };

        topPostsTask.execute();
    }

    @Override
    public void nextPosts(List<PostModel> posts) {
        if(posts == null)
            return;

        if (mPostAdapter == null) {
            PostAdapter ad = new PostAdapter(getActivity(), R.layout.fragment_news, posts);
            ListView postsLV = (ListView) getActivity().findViewById(R.id.postsListView);
            postsLV.setAdapter(ad);
        }
        else{
            mPostAdapter.addAll(posts);
            mPostAdapter.notifyDataSetChanged();
        }
    }
}
