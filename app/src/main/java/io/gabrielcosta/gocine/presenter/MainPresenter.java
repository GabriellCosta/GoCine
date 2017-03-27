package io.gabrielcosta.gocine.presenter;

import io.gabrielcosta.gocine.BuildConfig;
import io.gabrielcosta.gocine.entity.dto.PaginatedResponseDTO;
import io.gabrielcosta.gocine.entity.vo.MoviesResponseVO;
import io.gabrielcosta.gocine.model.FavoriteContextPackage;
import io.gabrielcosta.gocine.model.FavoriteMovieModel;
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

  private final MainView view;
  private final MoviesServiceImpl popularMovieService;
  private int pageNumber;
  private List<MoviesResponseVO> movieResponseVOs = new ArrayList<>();
  private MovieEndpointType movieType = MovieEndpointType.POPULAR;
  private final FavoriteMovieModel favoriteMovieModel;

  private MainPresenter(final MainView view, final MoviesServiceImpl moviesService,
      FavoriteMovieModel favoriteMovieModel) {
    this.view = view;
    this.popularMovieService = moviesService;
    this.favoriteMovieModel = favoriteMovieModel;
  }

  public static MainPresenter newInstance(final MainView view,
      FavoriteContextPackage contextPackage) {
    return new MainPresenter(view, MoviesServiceImpl
        .newInstance(BuildConfig.API_URL, BuildConfig.API_KEY),
        new FavoriteMovieModel(contextPackage));
  }

  static MainPresenter newInstance(final MainView view, MoviesServiceImpl popularMovieService) {
    return new MainPresenter(view, popularMovieService, new FavoriteMovieModel(null));
  }

  public void fetchMovies() {
    if (movieType.equals(MovieEndpointType.FAVORITE)) {
      getMoviesFromDB();
    } else {
      getMoviesFromInternet();
    }
  }

  public void getNextMoviePage() {
    getMoviesFromServer(pageNumber + 1);
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

  public MovieEndpointType getMovieType() {
    return movieType;
  }

  private void getMoviesFromServer(final int pageNumber) {
    popularMovieService.fetchMovies(pageNumber, movieType)
        .enqueue(new Callback<PaginatedResponseDTO<MoviesResponseVO>>() {
          @Override
          public void onResponse(Call<PaginatedResponseDTO<MoviesResponseVO>> call,
              Response<PaginatedResponseDTO<MoviesResponseVO>> response) {
            if (HttpURLConnection.HTTP_OK == response.code()) {
              sucessResponse(response);
              MainPresenter.this.pageNumber++;
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

  private void getMoviesFromInternet() {
    if (movieResponseVOs == null || movieResponseVOs.isEmpty()) {
      getMoviesFromServer(++pageNumber);
    } else {
      view.setPopularMovieList(movieResponseVOs);
    }
  }

  private void getMoviesFromDB() {
    movieResponseVOs.clear();
    List<MoviesResponseVO> moviesFavorite = favoriteMovieModel.fetchMovieList();
    movieResponseVOs.addAll(moviesFavorite);
    pageNumber = 0;
    view.setPopularMovieList(moviesFavorite);
  }

  private void sucessResponse(final Response<PaginatedResponseDTO<MoviesResponseVO>> response) {
    view.setPopularMovieList(response.body().getResults());
    movieResponseVOs.addAll(response.body().getResults());
    MainPresenter.this.pageNumber = response.body().getPage();
  }

}
