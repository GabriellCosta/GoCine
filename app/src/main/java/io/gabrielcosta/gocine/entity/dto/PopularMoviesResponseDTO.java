package io.gabrielcosta.gocine.entity.dto;

import com.google.gson.annotations.SerializedName;
import io.gabrielcosta.gocine.entity.vo.PopularMovieResponseVO;
import java.util.List;

/**
 * Created by gabrielcosta on 23/02/17.
 */

public final class PopularMoviesResponseDTO {

  @SerializedName("page")
  private final int page;
  @SerializedName("results")
  private final List<PopularMovieResponseVO> popularMovie;
  @SerializedName("total_results")
  private final int totalResults;
  @SerializedName("total_pages")
  private final int totalPages;

  private PopularMoviesResponseDTO(int page,
      List<PopularMovieResponseVO> popularMovie, int totalResults, int totalPages) {
    this.page = page;
    this.popularMovie = popularMovie;
    this.totalResults = totalResults;
    this.totalPages = totalPages;
  }
}