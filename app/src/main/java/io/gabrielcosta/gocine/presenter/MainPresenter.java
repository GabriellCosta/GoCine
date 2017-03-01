package io.gabrielcosta.gocine.presenter;

import io.gabrielcosta.gocine.BuildConfig;
import io.gabrielcosta.gocine.entity.dto.PaginatedResponseDTO;
import io.gabrielcosta.gocine.entity.vo.MoviesResponseVO;
import io.gabrielcosta.gocine.model.service.MovieEndpointType;
import io.gabrielcosta.gocine.model.service.MoviesServiceImpl;
import io.gabrielcosta.gocine.view.MainView;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gabriel on 2/24/17.
 */

public final class MainPresenter {

  private MainView view;
  private MoviesServiceImpl popularMovieService;
  private int pageNumber;
  private List<MoviesResponseVO> movieResponseVOs = new ArrayList<>();
  private MovieEndpointType movieType = MovieEndpointType.POPULAR;

  private MainPresenter() {
  }

  public static MainPresenter newInstance(final MainView view) {
    final MainPresenter presenter = new MainPresenter();
    presenter.view = view;
    presenter.popularMovieService = MoviesServiceImpl
        .newInstance(BuildConfig.API_URL, BuildConfig.API_KEY);
    return presenter;
  }

  static MainPresenter newInstance(final MainView view, MoviesServiceImpl popularMovieService) {
    final MainPresenter presenter = new MainPresenter();
    presenter.view = view;
    presenter.popularMovieService = popularMovieService;
    return presenter;
  }

  public void fetchMovies() {
    if (movieResponseVOs == null || movieResponseVOs.isEmpty()) {
      getMoviesFromServer(++pageNumber);
    } else {
      view.setPopularMovieList(movieResponseVOs);
    }
  }

  public void getNextMoviePage() {
    getMoviesFromServer(++pageNumber);
  }

  public void setMovieEndpointType(final MovieEndpointType movieEndpointType) {

    if (movieEndpointType == null) {
      throw new IllegalArgumentException(MovieEndpointType.class + " should not be null");
    }

    if (!this.movieType.equals(movieEndpointType)) {
      this.movieType = movieEndpointType;
      movieResponseVOs.clear();
      pageNumber = 0;
    }
  }

  public List<MoviesResponseVO> getMoviesListReference() {
    return Collections.unmodifiableList(movieResponseVOs);
  }

  private void getMoviesFromServer(final int pageNumber) {
    popularMovieService.fetchMovies(pageNumber, movieType)
        .enqueue(new Callback<PaginatedResponseDTO<MoviesResponseVO>>() {
          @Override
          public void onResponse(Call<PaginatedResponseDTO<MoviesResponseVO>> call,
              Response<PaginatedResponseDTO<MoviesResponseVO>> response) {
            if (HttpURLConnection.HTTP_OK == response.code()) {
              sucessResponse(response);
            } else {
              view.onError(popularMovieService.parseError(response.errorBody()).getStatusMessage());
            }
          }

          @Override
          public void onFailure(Call<PaginatedResponseDTO<MoviesResponseVO>> call, Throwable t) {
            view.onError(t.getMessage());
          }
        });
  }

  private void sucessResponse(final Response<PaginatedResponseDTO<MoviesResponseVO>> response) {
    view.setPopularMovieList(response.body().getResults());
    movieResponseVOs.addAll(response.body().getResults());
    MainPresenter.this.pageNumber = response.body().getPage();
  }

}
