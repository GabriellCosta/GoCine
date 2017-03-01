package io.gabrielcosta.gocine.entity.dto;

import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.List;

/**
 * Created by gabrielcosta on 23/02/17.
 */

public final class PaginatedResponseDTO<T> {

  @SerializedName("page")
  private final int page;
  @SerializedName("results")
  private final List<T> results;
  @SerializedName("total_results")
  private final int totalResults;
  @SerializedName("total_pages")
  private final int totalPages;

  private PaginatedResponseDTO(int page,
      List<T> results, int totalResults, int totalPages) {
    this.page = page;
    this.results = results;
    this.totalResults = totalResults;
    this.totalPages = totalPages;
  }

  public int getPage() {
    return page;
  }

  public List<T> getResults() {
    return Collections.unmodifiableList(results);
  }

  public int getTotalResults() {
    return totalResults;
  }

  public int getTotalPages() {
    return totalPages;
  }
}
