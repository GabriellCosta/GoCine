package io.gabrielcosta.gocine.presenter;

import io.gabrielcosta.gocine.BuildConfig;
import io.gabrielcosta.gocine.entity.dto.PaginatedResponseDTO;
import io.gabrielcosta.gocine.entity.dto.VideoResponseDTO;
import io.gabrielcosta.gocine.entity.vo.ErrorApiVO;
import io.gabrielcosta.gocine.entity.vo.MovieDetailVO;
import io.gabrielcosta.gocine.entity.vo.ReviewVO;
import io.gabrielcosta.gocine.entity.vo.VideoVO;
import io.gabrielcosta.gocine.model.service.MoviesServiceImpl;
import io.gabrielcosta.gocine.view.DetailView;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gabriel on 3/1/17.
 */

public final class DetailPresenter {

  private static final String SERVICO_INDISPONIVEL = "Serviço indisponivel";

  private final DetailView view;
  private final MoviesServiceImpl movieService;

  private DetailPresenter(final DetailView view, final MoviesServiceImpl movieService) {
    this.view = view;
    this.movieService = movieService;
  }

  public static DetailPresenter newInstance(final DetailView view) {
    final DetailPresenter presenter = new DetailPresenter(view, MoviesServiceImpl
        .newInstance(BuildConfig.API_URL, BuildConfig.API_KEY));
    return presenter;
  }

  static DetailPresenter newInstance(final DetailView view,
      final MoviesServiceImpl popularMovieService) {
    final DetailPresenter presenter = new DetailPresenter(view, popularMovieService);
    return presenter;
  }

  public void fetchReviews(final int movieId) {
    movieService.fetchReviews(movieId).enqueue(new Callback<PaginatedResponseDTO<ReviewVO>>() {
      @Override
      public void onResponse(Call<PaginatedResponseDTO<ReviewVO>> call,
          Response<PaginatedResponseDTO<ReviewVO>> response) {
        if (response.code() == HttpURLConnection.HTTP_OK) {
          sucessReview(response.body());
        } else {
          view.onError(movieService.parseError(response.errorBody()));
        }
      }

      @Override
      public void onFailure(Call<PaginatedResponseDTO<ReviewVO>> call, Throwable t) {
        view.onError(genericError());
      }
    });
  }

  public void fetchVideos(final int movieId) {
    movieService.fetchVideos(movieId).enqueue(new Callback<VideoResponseDTO>() {
      @Override
      public void onResponse(Call<VideoResponseDTO> call, Response<VideoResponseDTO> response) {
        if (response.code() == HttpURLConnection.HTTP_OK) {
          sucessVideo(response.body());
        } else {
          view.onError(movieService.parseError(response.errorBody()));
        }
      }

      @Override
      public void onFailure(Call<VideoResponseDTO> call, Throwable t) {
        view.onError(genericError());
      }
    });
  }

  public void getMovieDetail(final int movieId) {
    movieService.getMovieDetail(movieId).enqueue(new Callback<MovieDetailVO>() {
      @Override
      public void onResponse(Call<MovieDetailVO> call, Response<MovieDetailVO> response) {
        if (response.code() == HttpURLConnection.HTTP_OK) {
          sucessDetail(response.body());
        } else {
          view.onError(movieService.parseError(response.errorBody()));
        }
      }

      @Override
      public void onFailure(Call<MovieDetailVO> call, Throwable t) {
        view.onError(genericError());
      }
    });
  }

  private void sucessDetail(final MovieDetailVO detail) {
    view.setMovieTitle(detail.getTitle());
    view.setMovieDescription(detail.getOverview());
    view.setMovieBackgroundImage(detail.getBackdropPath());
    view.setMoviePoster(detail.getPosterPath());
    view.setMovieDuration(detail.getRuntime());
    view.setMovieRatio(detail.getVoteAverage());
    setMovieYear(detail);
  }

  private void setMovieYear(MovieDetailVO detail) {
    try {
      final Date parse = SimpleDateFormat.getInstance().parse(detail.getReleaseDate());
      final Calendar calendar = Calendar.getInstance();
      calendar.setTime(parse);
      view.setMovieYear(calendar.get(Calendar.YEAR));
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  private void sucessVideo(final VideoResponseDTO response) {
    List<VideoVO> result = response.getResult();
    if (!result.isEmpty()) {
      view.setVideos(result);
    } else {
      view.setEmptyVideos();
    }
  }

  private void sucessReview(final PaginatedResponseDTO<ReviewVO> response) {
    final List<ReviewVO> results = response.getResults();
    if (!results.isEmpty()) {
      view.setReviews(results);
    } else {
      view.setEmptyReviews();
    }
  }

  private ErrorApiVO genericError() {
    return new ErrorApiVO(SERVICO_INDISPONIVEL, 0);
  }

}
