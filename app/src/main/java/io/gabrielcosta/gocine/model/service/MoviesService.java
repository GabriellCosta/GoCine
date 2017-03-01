package io.gabrielcosta.gocine.model.service;

import io.gabrielcosta.gocine.entity.dto.PaginatedResponseDTO;
import io.gabrielcosta.gocine.entity.vo.MoviesResponseVO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by gabriel on 2/24/17.
 */

interface MoviesService {

  String MOVIE_POPULAR_ENDPOINT = "movie/popular";
  String MOVIE_TOP_RATED_ENDPOINT = "movie/top_rated";

  @GET
  Call<PaginatedResponseDTO<MoviesResponseVO>> fetchMovies(@Url String url, @Query("page") int page);

}
