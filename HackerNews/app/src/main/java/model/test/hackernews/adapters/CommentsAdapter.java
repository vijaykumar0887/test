

package model.test.hackernews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import model.test.hackernews.R;
import model.test.hackernews.Utils.Article;
import model.test.hackernews.Utils.TimeUtils;
import model.test.hackernews.network.ArticleResponseListener;
import model.test.hackernews.network.NetworkAdapter;

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context mContext;
    List<Integer> mCommentIds;

    public CommentsAdapter(Context context, List<Integer> items) {
        mContext = context;
        mCommentIds = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_comment, parent, false);
            return new CommenttemHolder(v);

    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            final Integer itemId = mCommentIds.get(position);
            final CommenttemHolder commenttemHolder = (CommenttemHolder) holder;
        final Article[] articleData = new Article[1];
        commenttemHolder.mProgressBar.setVisibility(View.VISIBLE);
        NetworkAdapter.getInstance().getDetails(mContext,String.valueOf(itemId), new ArticleResponseListener() {
            @Override
            public void onSuccess(Article result) {
                articleData[0] = result;
                commenttemHolder.mProgressBar.setVisibility(View.INVISIBLE);

                if (result.getTime() != null) {
                    commenttemHolder.txtTime.setText(TimeUtils.getTimeDifferenceToPresent(result.getTime()));
                }
                if (result.getBy() != null) {
                    commenttemHolder.txtAuthor.setText(result.getBy());
                }
                if (result.getText() != null) {
                    commenttemHolder.txtComment.setText(Html.fromHtml(result.getText()));
                }

            }

            @Override
            public void onFailure() {
                commenttemHolder.mProgressBar.setVisibility(View.INVISIBLE);
            }
        });


    }

    public void update(List<Integer> newData){
        mCommentIds.clear();
        mCommentIds.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCommentIds.size();
    }


    class CommenttemHolder extends RecyclerView.ViewHolder {
        TextView txtAuthor;
        TextView txtTime;
        TextView txtComment;
        ProgressBar mProgressBar;

        public CommenttemHolder(View itemView) {
            super(itemView);
            this.txtAuthor = (TextView) itemView.findViewById(R.id.tv_item_author);
            this.txtTime = (TextView) itemView.findViewById(R.id.tv_item_time);
            this.txtComment = (TextView) itemView.findViewById(R.id.tv_item_comment);
            this.mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);


        }
    }


}


