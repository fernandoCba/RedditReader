package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class PostAdapter extends android.widget.ArrayAdapter<PostModel> {

    private List<PostModel> mPosts;
    public PostAdapter(Context context, int resource) {
        super(context, resource);
    }

    public PostAdapter(Context context, int fragment_news, List<PostModel> postModelList) {
        super(context, fragment_news, postModelList);
        mPosts = postModelList;
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
    public int getPosition(PostModel p)
    {
        return mPosts.indexOf(p);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.post_row, null);
            PostModel post = mPosts.get(position);

            TextView title = (TextView) convertView.findViewById(R.id.postTitle);
            title.setText(post.getTitle());

            TextView author = (TextView) convertView.findViewById(R.id.postAuthor);
            author.setText(post.getAuthor());

        }
        return convertView;
    }
}
