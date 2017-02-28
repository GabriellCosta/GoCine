package io.gabrielcosta.gocine.presenter;

import io.gabrielcosta.gocine.BuildConfig;
import io.gabrielcosta.gocine.entity.dto.MoviesResponseDTO;
import io.gabrielcosta.gocine.entity.vo.MoviesResponseVO;
import io.gabrielcosta.gocine.model.service.MovieEndpointType;
import io.gabrielcosta.gocine.model.service.MoviesServiceImpl;
import io.gabrielcosta.gocine.view.MainView;
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

  private static final int SUCESS_RESPONSE = 200;
  private static final MainPresenter presenter = new MainPresenter();

  private MainView view;
  private MoviesServiceImpl popularMovieService;
  private int pageNumber;
  private List<MoviesResponseVO> movieResponseVOs = new ArrayList<>();
  private MovieEndpointType movieType = MovieEndpointType.POPULAR;

  private MainPresenter() {
  }

  public static MainPresenter newInstance(final MainView view) {
    presenter.view = view;
    presenter.popularMovieService = MoviesServiceImpl
        .newInstance(BuildConfig.API_URL, BuildConfig.API_KEY);
    return presenter;
  }

  static MainPresenter newInstance(final MainView view, MoviesServiceImpl popularMovieService) {
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
        .enqueue(new Callback<MoviesResponseDTO>() {
          @Override
          public void onResponse(Call<MoviesResponseDTO> call,
              Response<MoviesResponseDTO> response) {
            if (response.code() == SUCESS_RESPONSE) {
              sucessResponse(response);
            }
          }

          @Override
          public void onFailure(Call<MoviesResponseDTO> call, Throwable t) {
            view.onError(t.getMessage());
          }
        });
  }

  private void sucessResponse(final Response<MoviesResponseDTO> response) {
    view.setPopularMovieList(response.body().getMovies());
    movieResponseVOs.addAll(response.body().getMovies());
    MainPresenter.this.pageNumber = response.body().getPage();
  }

}
