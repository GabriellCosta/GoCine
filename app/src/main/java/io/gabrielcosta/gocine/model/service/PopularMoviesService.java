package io.gabrielcosta.gocine.model.service;

import io.gabrielcosta.gocine.entity.dto.PopularMoviesResponseDTO;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by gabriel on 2/24/17.
 */

interface PopularMoviesService {

  String MOVIE_POPULAR_ENDPOINT = "movie/popular";

  @GET(MOVIE_POPULAR_ENDPOINT)
  Call<PopularMoviesResponseDTO> fetchPopularMovies();

}
