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
import io.gabrielcosta.gocine.entity.vo.MoviesResponseVO;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabriel on 2/24/17.
 */

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesVH> {

  private final List<MoviesResponseVO> popularMovieVOs = new ArrayList<>();

  @Override
  public PopularMoviesVH onCreateViewHolder(ViewGroup parent, int viewType) {
    final int layout = R.layout.list_item_popular_movies;

    final View view = LayoutInflater.from(parent.getContext())
        .inflate(layout, parent, false);
    return new PopularMoviesVH(view);
  }

  @Override
  public void onBindViewHolder(PopularMoviesVH holder, int position) {
    final MoviesResponseVO popularMovieVO = popularMovieVOs.get(position);
    loadImage(holder.imageView, popularMovieVO);
    holder.itemView.setContentDescription(popularMovieVO.getTitle());
  }

  @Override
  public int getItemCount() {
    return popularMovieVOs.size();
  }

  public void addItens(final List<MoviesResponseVO> popularMovieVOs) {
    this.popularMovieVOs.addAll(popularMovieVOs);
    notifyDataSetChanged();
  }

  private void loadImage(final ImageView imageView, final MoviesResponseVO popularMovieVO) {
    Context context = imageView.getContext();
    Picasso.with(context)
        .load(context.getResources()
            .getString(R.string.api_image_url,
                context.getResources().getString(R.string.api_poster_size),
                popularMovieVO.getPosterPath()))
        .placeholder(R.drawable.progress_animation)
        .into(imageView);
  }

  class PopularMoviesVH extends RecyclerView.ViewHolder {

    private final ImageView imageView;

    public PopularMoviesVH(View itemView) {
      super(itemView);
      imageView = (ImageView) itemView.findViewById(R.id.imageview_item_list_popular_movie);
    }
  }

}
