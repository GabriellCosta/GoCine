package io.gabrielcosta.gocine.presenter;

import io.gabrielcosta.gocine.entity.dto.PopularMoviesResponseDTO;
import io.gabrielcosta.gocine.model.service.PopularMovieServiceImpl;
import io.gabrielcosta.gocine.view.MainView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gabriel on 2/24/17.
 */

public final class MainPresenter {

  private final MainView view;
  private final PopularMovieServiceImpl popularMovieService;
  private int pageNumber;


  private MainPresenter(MainView view) {
    this.view = view;
    popularMovieService = new PopularMovieServiceImpl();
  }

  public static MainPresenter newInstance(final MainView view) {
    return new MainPresenter(view);
  }

  public void fetchPopularMovies() {
    popularMovieService.fetchPopularMovies(++pageNumber)
        .enqueue(new Callback<PopularMoviesResponseDTO>() {
          @Override
          public void onResponse(Call<PopularMoviesResponseDTO> call,
              Response<PopularMoviesResponseDTO> response) {
            if (response.code() == 200) {
                view.setPopularMovieList(response.body().getPopularMovie());
                pageNumber = response.body().getPage();

            }
          }

          @Override
          public void onFailure(Call<PopularMoviesResponseDTO> call, Throwable t) {
            view.onError(t.getMessage());
          }
        });
  }

}
