package ar.edu.unc.famaf.redditreader.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Fernando on 6/11/2016.
 */

public class Utils {
    Context mContext;

    public Utils(Context context) {
        mContext = context;
    }

    public boolean checkInternetConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
