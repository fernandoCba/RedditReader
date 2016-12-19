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
import ar.edu.unc.famaf.redditreader.model.CommentModel;

public class CommentsAdapter extends android.widget.ArrayAdapter<CommentModel> {
    private List<CommentModel> mCommentList;
    private Context mContext;
    private int mFragment;

    public CommentsAdapter(Context context, int fragment, List<CommentModel> postModelList) {
        super(context, fragment, postModelList);
        mFragment = fragment;
        mCommentList = postModelList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mCommentList.size();
    }

    @Nullable
    @Override
    public CommentModel getItem(int position) {
        return mCommentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getPosition(CommentModel p) {
        return mCommentList.indexOf(p);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.comment_row, null);

        }
        if (convertView.getTag() == null) {
            viewHolder = new ViewHolder(
                    (TextView) convertView.findViewById(R.id.comment_author),
                    (TextView) convertView.findViewById(R.id.comment_created_on),
                    (TextView) convertView.findViewById(R.id.comment_body)
            );
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CommentModel comment = mCommentList.get(position);
        viewHolder.mAauthor.setText(comment.getAuthor());
        viewHolder.mBody.setText(comment.getBody());
        viewHolder.mCreatedOn.setText(comment.getElapsedTime());
        convertView.setPadding((30*comment.getDepth()), convertView.getPaddingTop(),
                convertView.getPaddingRight(), convertView.getPaddingBottom());

        return convertView;
    }

    private class ViewHolder {
        public final TextView mAauthor;
        public final TextView mCreatedOn;
        public final TextView mBody;

        public ViewHolder(TextView author, TextView createdOn, TextView body) {
            mAauthor = author;
            mCreatedOn = createdOn;
            mBody = body;
        }
    }
}
