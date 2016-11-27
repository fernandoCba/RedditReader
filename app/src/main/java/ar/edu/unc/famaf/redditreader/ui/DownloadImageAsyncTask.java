package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ar.edu.unc.famaf.redditreader.backend.RedditDBHelper;
import ar.edu.unc.famaf.redditreader.utils.Utils;


public class DownloadImageAsyncTask extends AsyncTask<URL, Integer, Bitmap> {
    private static final String TAG = "REDDITREADER.DownImg";
    ImageView mImageView;
    ProgressBar mProgressBar;
    Context mContext;
    private int mPosition;


    public DownloadImageAsyncTask(Context context, ImageView img, ProgressBar progress, int position) {
        mImageView = img;
        mProgressBar = progress;
        mContext = context;
        mPosition = position;
    }

    public int getPosition() {
        return mPosition;
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
        mImageView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Bitmap doInBackground(URL... urls) {
        RedditDBHelper dbHelper = new RedditDBHelper(mContext);
        URL url = urls[0];
        if (url == null)
            return null;
        Bitmap bitmap = dbHelper.getImage(url);
        HttpURLConnection connection = null;
        if (bitmap == null && new Utils(mContext).checkInternetConnection()) {
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(3000);
                InputStream is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is, null, null);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                bitmap = null;
            }
            if (bitmap != null)
                dbHelper.persistImage(url.toString(), bitmap);
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (result != null)
            mImageView.setImageBitmap(result);
        mProgressBar.setVisibility(View.GONE);
        mImageView.setVisibility(View.VISIBLE);
    }
}
