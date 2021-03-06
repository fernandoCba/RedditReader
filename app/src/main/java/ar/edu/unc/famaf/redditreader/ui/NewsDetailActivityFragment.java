package ar.edu.unc.famaf.redditreader.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.net.URL;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.GetCommentsTask;
import ar.edu.unc.famaf.redditreader.model.CommentModel;
import ar.edu.unc.famaf.redditreader.model.PostModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsDetailActivityFragment extends Fragment {
    private final static String TAG = "REDDIT_DETAILSFRAGMENT";

    public NewsDetailActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
    }

    public void updateContent(final PostModel post) {
        TextView subreddit = (TextView) getView().findViewById(R.id.details_subreddit);
        subreddit.setText("/r/" + post.getSubreddit());

        TextView title = (TextView) getView().findViewById(R.id.details_title);
        title.setText(post.getTitle());

        TextView author = (TextView) getView().findViewById(R.id.details_author);
        author.setText(post.getAuthor());

        TextView created = (TextView) getView().findViewById(R.id.details_date);
        created.setText(post.getElapsedTime());

        Button url = (Button) getView().findViewById(R.id.details_url);
        url.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUrl(post);
            }
        });

        showPreview(post);
        showComments(post);
    }

    private void showComments(PostModel post) {
        GetCommentsTask commentsTaks = new GetCommentsTask(post) {
            @Override
            protected void onPostExecute(CommentModel commentModel) {
                super.onPostExecute(commentModel);
                setCommentListAdapter(commentModel);
            }
        };
        commentsTaks.execute();

    }

    private void setCommentListAdapter(CommentModel model) {
        ListView listView = (ListView) getActivity().findViewById(R.id.comments_list_view);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        CommentsAdapter adapter = new CommentsAdapter(getActivity(), R.layout.fragment_news_detail, model.getFlatList());
        listView.setAdapter(adapter);
    }

    private void showPreview(PostModel post) {
        if (post.getPreview() != null && !post.getPreview().isEmpty()) {
            try {
                URL[] urlArray = new URL[1];
                urlArray[0] = new URL(post.getPreview());

                ImageView preview = (ImageView) getView().findViewById(R.id.details_preview);
                ProgressBar progress = (ProgressBar) getView().findViewById(R.id.details_progress_bar);
                DownloadImageAsyncTask task = new DownloadImageAsyncTask(getContext(), preview, progress, 0) {
                    @Override
                    public void onPreExecute() {
                        super.onPreExecute();
                        mImageView.setVisibility(View.GONE);
                        mProgressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected void onPostExecute(Bitmap result) {
                        if (result != null) {
                            mImageView.setImageBitmap(result);
                            mImageView.setVisibility(View.VISIBLE);
                        }
                        mProgressBar.setVisibility(View.GONE);
                    }
                };
                task.execute(urlArray);
            } catch (Exception e) {
                Log.e(TAG, "Could not display preview");
            }
        }
    }

    public void showUrl(PostModel post) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra(NewsDetailActivity.EXTRA_POST_MODEL, post);
        startActivity(intent);
    }

}
