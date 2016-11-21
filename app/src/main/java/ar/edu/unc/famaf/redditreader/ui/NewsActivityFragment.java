package ar.edu.unc.famaf.redditreader.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
public class NewsActivityFragment extends Fragment implements PostsIteratorListener {
    OnItemClickListener mCallback;
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
        Backend.getInstance().getNextPosts(this);
        ListView listView = (ListView) getActivity().findViewById(R.id.postsListView);
        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                Backend.getInstance().getNextPosts(NewsActivityFragment.this);
                return true;
            }
        });

        try {
            mCallback = (OnItemClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void nextPosts(List<PostModel> posts) {
        if (posts == null)
            return;

        if (mPostAdapter == null) {
            mPostAdapter = new PostAdapter(getActivity(), R.layout.fragment_news, posts);
            ListView postsLV = (ListView) getActivity().findViewById(R.id.postsListView);
            postsLV.setAdapter(mPostAdapter);
            postsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                               @Override
                                               public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                                                   PostModel post = mPostAdapter.getItem(position);
                                                   mCallback.onPostItemPicked(post);
                                               }
                                           }
            );
        } else {
            mPostAdapter.addAll(posts);
            mPostAdapter.notifyDataSetChanged();
        }
    }
}
