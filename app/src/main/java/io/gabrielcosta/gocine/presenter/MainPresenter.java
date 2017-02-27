package io.gabrielcosta.gocine.presenter;

import io.gabrielcosta.gocine.entity.dto.PopularMoviesResponseDTO;
import io.gabrielcosta.gocine.entity.vo.PopularMovieResponseVO;
import io.gabrielcosta.gocine.model.service.PopularMovieServiceImpl;
import io.gabrielcosta.gocine.view.MainView;
import java.util.ArrayList;
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
  private PopularMovieServiceImpl popularMovieService = new PopularMovieServiceImpl();
  private int pageNumber;
  private List<PopularMovieResponseVO> movieResponseVOs = new ArrayList<>();

  private MainPresenter() {
  }

  public static MainPresenter newInstance(final MainView view) {
    presenter.view = view;
    return presenter;
  }

  public void fetchPopularMovies() {
    if (movieResponseVOs == null || movieResponseVOs.isEmpty()) {
      getPopularMoviesFromServer(++pageNumber);
    } else {
      view.setPopularMovieList(movieResponseVOs);
    }
  }

  public void getNextMoviePage() {
    getPopularMoviesFromServer(++pageNumber);
  }

  private void getPopularMoviesFromServer(final int pageNumber) {
    popularMovieService.fetchPopularMovies(pageNumber)
        .enqueue(new Callback<PopularMoviesResponseDTO>() {
          @Override
          public void onResponse(Call<PopularMoviesResponseDTO> call,
              Response<PopularMoviesResponseDTO> response) {
            if (response.code() == SUCESS_RESPONSE) {
              sucessResponse(response);
            }
          }

          @Override
          public void onFailure(Call<PopularMoviesResponseDTO> call, Throwable t) {
            view.onError(t.getMessage());
          }
        });
  }

  private void sucessResponse(final Response<PopularMoviesResponseDTO> response) {
    view.setPopularMovieList(response.body().getPopularMovie());
    movieResponseVOs.addAll(response.body().getPopularMovie());
    MainPresenter.this.pageNumber = response.body().getPage();
  }

}
