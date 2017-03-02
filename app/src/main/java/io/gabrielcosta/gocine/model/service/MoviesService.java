package io.gabrielcosta.gocine.model.service;

import io.gabrielcosta.gocine.entity.dto.PaginatedResponseDTO;
import io.gabrielcosta.gocine.entity.dto.VideoResponseDTO;
import io.gabrielcosta.gocine.entity.vo.MoviesResponseVO;
import io.gabrielcosta.gocine.entity.vo.ReviewVO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by gabriel on 2/24/17.
 */

interface MoviesService {

  String MOVIE_POPULAR_ENDPOINT = "movie/popular";
  String MOVIE_TOP_RATED_ENDPOINT = "movie/top_rated";
  String REVIEW_ENDPOINT = "movie/{id}/reviews";
  String VIDEO_ENDPOINT = "movie/{id}/videos";

  @GET
  Call<PaginatedResponseDTO<MoviesResponseVO>> fetchMovies(@Url String url, @Query("page") int page);

  @GET(REVIEW_ENDPOINT)
  Call<PaginatedResponseDTO<ReviewVO>> fetchReviews(@Path("id") final int id);

  @GET(VIDEO_ENDPOINT)
  Call<VideoResponseDTO> fetchVideos(@Path("id") final int id);

}
