package io.gabrielcosta.gocine.model.service;

import io.gabrielcosta.gocine.entity.dto.MoviesResponseDTO;
import io.gabrielcosta.gocine.model.retrofit.AbstractRetrofitServiceCall;
import io.gabrielcosta.gocine.model.retrofit.RetrofitModelImpl;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by gabriel on 2/24/17.
 */

public class MoviesServiceImpl extends AbstractRetrofitServiceCall {

  private final MoviesService retrofit = getService(MoviesService.class);

  private MoviesServiceImpl(Retrofit retrofitModel) {
    super(retrofitModel);
  }

  public static MoviesServiceImpl newInstance(final String baseUrl, final String apiKey) {
    return new MoviesServiceImpl(RetrofitModelImpl.getInstance().getRetrofit(baseUrl, apiKey));
  }

  public Call<MoviesResponseDTO> fetchMovies(final int page, final MovieEndpointType listType) {
    return retrofit.fetchMovies(listType.getEndpoint(), page);
  }

}
