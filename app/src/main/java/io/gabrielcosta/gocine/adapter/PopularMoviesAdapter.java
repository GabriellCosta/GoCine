package io.gabrielcosta.gocine.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import io.gabrielcosta.gocine.R;
import io.gabrielcosta.gocine.adapter.PopularMoviesAdapter.PopularMoviesVH;
import io.gabrielcosta.gocine.entity.vo.MoviesResponseVO;
import io.gabrielcosta.gocine.ui.activity.DetailActivity;
import io.gabrielcosta.gocine.util.ImagePathUtil;
import io.gabrielcosta.gocine.util.PicassoUtil;
import java.util.List;

/**
 * Created by gabriel on 2/24/17.
 */

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesVH> {

  private List<MoviesResponseVO> popularMovieVOs;

  public PopularMoviesAdapter(
      List<MoviesResponseVO> popularMovieVOs) {
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
    final MoviesResponseVO popularMovieVO = popularMovieVOs.get(position);
    setPosterImage(holder, popularMovieVO);
    holder.itemView.setContentDescription(popularMovieVO.getTitle());
    holder.itemView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        final Intent intent = new Intent(v.getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.MOVIE_ID_EXTRA, popularMovieVO.getId());
        v.getContext().startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount() {
    return popularMovieVOs != null ? popularMovieVOs.size() : 0;
  }

  private void setPosterImage(final PopularMoviesVH holder, final MoviesResponseVO popularMovieVO) {
    final String imagePath = ImagePathUtil
        .getImagePath(holder.imageView.getContext(), R.string.api_poster_size,
            popularMovieVO.getPosterPath());
    PicassoUtil.buildImage(holder.imageView, imagePath);
  }

  class PopularMoviesVH extends RecyclerView.ViewHolder {

    private final ImageView imageView;

    public PopularMoviesVH(View itemView) {
      super(itemView);
      imageView = (ImageView) itemView.findViewById(R.id.imageview_item_list_popular_movie);
    }
  }

}
