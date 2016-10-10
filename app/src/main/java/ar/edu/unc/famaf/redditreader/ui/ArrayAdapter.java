package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;

import ar.edu.unc.famaf.redditreader.model.PostModel;

public class ArrayAdapter extends android.widget.ArrayAdapter<PostModel> {

    public ArrayAdapter(Context context, int resource) {
        super(context, resource);
    }
}
