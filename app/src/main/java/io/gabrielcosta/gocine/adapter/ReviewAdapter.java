package io.gabrielcosta.gocine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import io.gabrielcosta.gocine.R;
import io.gabrielcosta.gocine.adapter.ReviewAdapter.ReviewVH;
import io.gabrielcosta.gocine.entity.vo.ReviewVO;
import java.util.List;

/**
 * Created by gabrielcosta on 04/03/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewVH> {

  private final List<ReviewVO> reviewVOs;

  public ReviewAdapter(final List<ReviewVO> reviewVOs) {
    this.reviewVOs = reviewVOs;
  }

  @Override
  public ReviewVH onCreateViewHolder(ViewGroup parent, int viewType) {
    View inflate = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_review, parent, Boolean.FALSE);
    return new ReviewVH(inflate);
  }

  @Override
  public void onBindViewHolder(ReviewVH holder, int position) {
    ReviewVO reviewVO = reviewVOs.get(position);
    holder.author.setText(reviewVO.getAuthor());
    holder.review.setText(reviewVO.getContent());
  }

  @Override
  public int getItemCount() {
    return reviewVOs.size();
  }

  final class ReviewVH extends RecyclerView.ViewHolder {

    private TextView author;
    private TextView review;

    private ReviewVH(View itemView) {
      super(itemView);
      author = (TextView) itemView.findViewById(R.id.textview_list_item_review_author);
      review = (TextView) itemView.findViewById(R.id.textview_list_item_review_description);
    }
  }

}
