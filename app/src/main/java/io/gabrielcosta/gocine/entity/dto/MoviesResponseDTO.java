package io.gabrielcosta.gocine.entity.dto;

import com.google.gson.annotations.SerializedName;
import io.gabrielcosta.gocine.entity.vo.MoviesResponseVO;
import java.util.Collections;
import java.util.List;

/**
 * Created by gabrielcosta on 23/02/17.
 */

public final class MoviesResponseDTO {

  @SerializedName("page")
  private final int page;
  @SerializedName("results")
  private final List<MoviesResponseVO> movies;
  @SerializedName("total_results")
  private final int totalResults;
  @SerializedName("total_pages")
  private final int totalPages;

  private MoviesResponseDTO(int page,
      List<MoviesResponseVO> movies, int totalResults, int totalPages) {
    this.page = page;
    this.movies = movies;
    this.totalResults = totalResults;
    this.totalPages = totalPages;
  }

  public int getPage() {
    return page;
  }

  public List<MoviesResponseVO> getMovies() {
    return Collections.unmodifiableList(movies);
  }

  public int getTotalResults() {
    return totalResults;
  }

  public int getTotalPages() {
    return totalPages;
  }
}
