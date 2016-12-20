package ar.edu.unc.famaf.redditreader.backend;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by Ro on 20/12/2016.
 */
public class ImageCache {
    private static ImageCache ourInstance = new ImageCache();
    private HashMap<String, Bitmap> mCache = new HashMap<String, Bitmap>();
    public static ImageCache getInstance() {
        return ourInstance;
    }

    private ImageCache() {
    }

    public Bitmap getImage(String url){
        url = url.replace("https://", "http://");
        return mCache.get(url);
    }
    public void setImage(String url, Bitmap image){
        url = url.replace("https://", "http://");
        mCache.put(url, image);
    }
}
