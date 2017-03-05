package io.gabrielcosta.gocine.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import io.gabrielcosta.gocine.R;
import io.gabrielcosta.gocine.adapter.VideoAdapter.VideoVH;
import io.gabrielcosta.gocine.entity.vo.VideoVO;
import io.gabrielcosta.gocine.util.IntentUtil;
import java.util.List;

/**
 * Created by gabrielcosta on 05/03/17.
 */

public final class VideoAdapter extends Adapter<VideoVH> {

  private final List<VideoVO> videoVOList;

  public VideoAdapter(final List<VideoVO> videoVOList) {
    this.videoVOList = videoVOList;
  }

  @Override
  public VideoVH onCreateViewHolder(ViewGroup parent, int viewType) {
    View inflate = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_video, parent, Boolean.FALSE);
    return new VideoVH(inflate);
  }

  @Override
  public void onBindViewHolder(final VideoVH holder, int position) {
    final VideoVO videoVO = videoVOList.get(position);
    holder.name.setText(videoVO.getName());
    holder.itemView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        holder.itemView.getContext().startActivity(IntentUtil.buildYoutubeIntent(videoVO.getKey()));
      }
    });
  }

  @Override
  public int getItemCount() {
    return videoVOList.size();
  }

  final class VideoVH extends ViewHolder {

    private final TextView name;

    private VideoVH(View itemView) {
      super(itemView);
      name = (TextView) itemView.findViewById(R.id.textview_list_item_video_name);
    }
  }

}
