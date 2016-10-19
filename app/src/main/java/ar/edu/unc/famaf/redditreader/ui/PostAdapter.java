package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class PostAdapter extends android.widget.ArrayAdapter<PostModel> {
    private final String mPostTitleAndAuthorText;
    private final String mCommentsText;
    private List<PostModel> mPosts;
    private Context context;

    public PostAdapter(Context context, int fragment_news, List<PostModel> postModelList) {
        super(context, fragment_news, postModelList);
        mPosts = postModelList;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.post_row, null);
            viewHolder = new ViewHolder(
                    (ImageView) convertView.findViewById(R.id.news_icon),
                    (TextView) convertView.findViewById(R.id.news_content),
                    (TextView) convertView.findViewById(R.id.number_comments)
            );
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PostModel post = mPosts.get(position);

        TextView postContent = viewHolder.postContentView;
        CharSequence contentText = postContent.getText();
        String s = mPostTitleAndAuthorText.replace("#TITLE#", post.getTitle()).replace("#AUTHOR#", post.getAuthor());
        postContent.setText(s);

        URL[] urlArray = new URL[1];
        String imgUrl = post.getImage();
        try {
            urlArray[0] = new URL(imgUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        DownloadImageAsyncTask downloadImageAsyncTask = new DownloadImageAsyncTask(viewHolder.imageView);
        downloadImageAsyncTask.execute(urlArray);

        TextView comments = viewHolder.commentsView;
        s = mCommentsText.replace("#COMMENTS#", "" + post.getComments());
        comments.setText(s);

        return convertView;
    }

    private class ViewHolder {
        public final ImageView imageView;
        public final TextView postContentView;
        public final TextView commentsView;

        public ViewHolder(ImageView img, TextView post, TextView comments) {
            imageView = img;
            postContentView = post;
            commentsView = comments;
        }
    }

    protected class DownloadImageAsyncTask extends AsyncTask<URL, Integer, Bitmap> {
        private static final String TAG = "REDDITREADER.ASYNC";
        ImageView mImageView;


        public DownloadImageAsyncTask(ImageView img) {
            super();
            mImageView = img;
        }


        @Override
        protected Bitmap doInBackground(URL... urls) {
            URL url = urls[0];
            Bitmap bitmap = null;
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is, null, null);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            mImageView.setImageBitmap(result);
        }
    }


}
