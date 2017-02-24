package io.gabrielcosta.gocine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import io.gabrielcosta.gocine.R;
import io.gabrielcosta.gocine.adapter.PopularMoviesAdapter.PopularMoviesVH;
import io.gabrielcosta.gocine.entity.vo.PopularMovieResponseVO;
import java.util.List;

/**
 * Created by gabriel on 2/24/17.
 */

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesVH> {

  private final List<PopularMovieResponseVO> popularMovieVOs;

  public PopularMoviesAdapter(
      List<PopularMovieResponseVO> popularMovieVOs) {
    this.popularMovieVOs = popularMovieVOs;
  }

  @Override
  public PopularMoviesVH onCreateViewHolder(ViewGroup parent, int viewType) {
    final int layout = R.layout.list_item_popular_movies;

    final View view = LayoutInflater.from(parent.getContext())
        .inflate(layout, parent, false);
    return new PopularMoviesVH(view);
  }

  @Override
  public void onBindViewHolder(PopularMoviesVH holder, int position) {
    final PopularMovieResponseVO popularMovieVO = popularMovieVOs.get(position);
    Context context = holder.itemView.getContext();
    Picasso.with(context)
        .load(context.getResources()
            .getString(R.string.api_image_url,
                context.getResources().getString(R.string.api_poster_size),
                popularMovieVO.getPosterPath()))
        .into(holder.imageView);
  }

  @Override
  public int getItemCount() {
    return popularMovieVOs.size();
  }

  class PopularMoviesVH extends RecyclerView.ViewHolder {

    private final ImageView imageView;

    public PopularMoviesVH(View itemView) {
      super(itemView);
      imageView = (ImageView) itemView.findViewById(R.id.imageview_item_list_popular_movie);
    }
  }

}
