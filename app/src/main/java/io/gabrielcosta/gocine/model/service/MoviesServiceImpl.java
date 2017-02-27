package io.gabrielcosta.gocine.model.service;

import io.gabrielcosta.gocine.entity.dto.MoviesResponseDTO;
import io.gabrielcosta.gocine.model.retrofit.AbstractRetrofitServiceCall;
import retrofit2.Call;

/**
 * Created by gabriel on 2/24/17.
 */

public class MoviesServiceImpl extends AbstractRetrofitServiceCall {

  private final MoviesService retrofit = getService(MoviesService.class);

  public Call<MoviesResponseDTO> fetchMovies(final int page, final MovieEndpointType listType) {
    return retrofit.fetchMovies(listType.getEndpoint(), page);
  }

}
