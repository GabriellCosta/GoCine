package io.gabrielcosta.gocine.presenter;

import io.gabrielcosta.gocine.entity.dto.PaginatedResponseDTO;
import io.gabrielcosta.gocine.entity.vo.ReviewVO;
import io.gabrielcosta.gocine.model.service.MoviesServiceImpl;
import io.gabrielcosta.gocine.view.DetailView;
import java.net.HttpURLConnection;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gabriel on 3/1/17.
 */

public final class DetailPresenter {

  private final DetailView view;
  private MoviesServiceImpl movieService;

  private DetailPresenter(final DetailView view) {
    this.view = view;
  }

  public static DetailPresenter newInstance(final DetailView view) {
    final DetailPresenter presenter = new DetailPresenter(view);
    return presenter;
  }

  static DetailPresenter newInstance(final DetailView view, MoviesServiceImpl popularMovieService) {
    final DetailPresenter presenter = new DetailPresenter(view);
    presenter.movieService = popularMovieService;
    return presenter;
  }

  public void fetchReviews(final int movieId) {
    movieService.fetchReviews(movieId).enqueue(new Callback<PaginatedResponseDTO<ReviewVO>>() {
      @Override
      public void onResponse(Call<PaginatedResponseDTO<ReviewVO>> call,
          Response<PaginatedResponseDTO<ReviewVO>> response) {
        if (response.code() == HttpURLConnection.HTTP_OK) {
          sucessReview(response.body());
        }
      }

      @Override
      public void onFailure(Call<PaginatedResponseDTO<ReviewVO>> call, Throwable t) {

      }
    });
  }

  private void sucessReview(final PaginatedResponseDTO<ReviewVO> response) {
    final List<ReviewVO> results = response.getResults();
    if (!results.isEmpty()) {
      view.setReviews(results);
    } else {
      view.setEmptyReviews();
    }
  }

}
