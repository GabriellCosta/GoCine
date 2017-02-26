package io.gabrielcosta.gocine.model.service;

import io.gabrielcosta.gocine.entity.dto.PopularMoviesResponseDTO;
import io.gabrielcosta.gocine.model.retrofit.AbstractRetrofitServiceCall;
import retrofit2.Call;

/**
 * Created by gabriel on 2/24/17.
 */

public class PopularMovieServiceImpl extends AbstractRetrofitServiceCall {

  private final PopularMoviesService retrofit = getService(PopularMoviesService.class);

  public Call<PopularMoviesResponseDTO> fetchPopularMovies(final int page) {
    return retrofit.fetchPopularMovies(page);
  }

}
