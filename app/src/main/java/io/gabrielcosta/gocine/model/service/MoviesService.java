package io.gabrielcosta.gocine.model.service;

import io.gabrielcosta.gocine.entity.dto.MoviesResponseDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by gabriel on 2/24/17.
 */

interface MoviesService {

  String MOVIE_POPULAR_ENDPOINT = "movie/popular";

  @GET(MOVIE_POPULAR_ENDPOINT)
  Call<MoviesResponseDTO> fetchMovies(@Query("page") int page);

}
