package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.RedditDBHelper;
import ar.edu.unc.famaf.redditreader.model.PostModel;
import ar.edu.unc.famaf.redditreader.utils.Utils;

public class PostAdapter extends android.widget.ArrayAdapter<PostModel> {
    private final String mPostTitleAndAuthorText;
    private final String mCommentsText;
    private List<PostModel> mPosts;
    private Context mContext;

    public PostAdapter(Context context, int fragment_news, List<PostModel> postModelList) {
        super(context, fragment_news, postModelList);
        mPosts = postModelList;
        this.mContext = context;
        mPostTitleAndAuthorText = context.getResources()
                .getString(R.string.post_title_and_author);
        mCommentsText = context.getResources()
                .getString(R.string.post_comments);
    }

    @Override
    public int getCount() {
        return mPosts.size();
    }

    @Nullable
    @Override
    public PostModel getItem(int position) {
        return mPosts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getPosition(PostModel p) {
        return mPosts.indexOf(p);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.post_row, null);

        }
        if (convertView.getTag() == null) {
            viewHolder = new ViewHolder(
                    (ImageView) convertView.findViewById(R.id.news_icon),
                    (ProgressBar) convertView.findViewById(R.id.progressBarNewsIcon),
                    (TextView) convertView.findViewById(R.id.news_content),
                    (TextView) convertView.findViewById(R.id.number_comments),
                    (TextView) convertView.findViewById(R.id.timespanContent),
                    (TextView) convertView.findViewById(R.id.reddit_channel)
            );
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PostModel post = mPosts.get(position);
        TextView postContent = viewHolder.mPostContentView;
        CharSequence contentText = postContent.getText();
        String s = mPostTitleAndAuthorText.replace("#TITLE#", post.getTitle()).replace("#AUTHOR#", post.getAuthor());
        postContent.setText(s);

        String imgUrl = post.getImage();
        try {
            URL[] urlArray = new URL[1];

            if (imgUrl != null)
                urlArray[0] = new URL(imgUrl);
            else
                urlArray[0] = null;

            DownloadImageAsyncTask downloadImageAsyncTask = new DownloadImageAsyncTask(getContext(),
                    viewHolder.mImageView, viewHolder.mProgressBar, position) {

                @Override
                public void onPreExecute() {
                    super.onPreExecute();
                    mImageView.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.VISIBLE);
                    mImageView.setImageResource(R.drawable.reddit_icon);
                }

                @Override
                protected void onPostExecute(Bitmap result) {
                    if (result != null && getPosition() == position)
                        mImageView.setImageBitmap(result);
                    mProgressBar.setVisibility(View.GONE);
                    mImageView.setVisibility(View.VISIBLE);
                }
            };
            downloadImageAsyncTask.execute(urlArray);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView comments = viewHolder.mCommentsView;
        s = mCommentsText.replace("#COMMENTS#", "" + post.getComments());
        comments.setText(s);

        TextView created = viewHolder.mCreatedOnView;
        created.setText(post.getElapsedTime());

        TextView subreddit = viewHolder.mSubreddit;
        subreddit.setText("/r/" + post.getSubreddit());

        return convertView;
    }

    private class ViewHolder {
        public final ImageView mImageView;
        public final ProgressBar mProgressBar;
        public final TextView mPostContentView;
        public final TextView mCommentsView;
        public final TextView mCreatedOnView;
        public final TextView mSubreddit;

        public ViewHolder(ImageView img, ProgressBar progress, TextView post, TextView comments,
                          TextView createdOn, TextView subreddit) {
            mImageView = img;
            mProgressBar = progress;
            mPostContentView = post;
            mCommentsView = comments;
            mCreatedOnView = createdOn;
            mSubreddit = subreddit;
        }
    }
}
