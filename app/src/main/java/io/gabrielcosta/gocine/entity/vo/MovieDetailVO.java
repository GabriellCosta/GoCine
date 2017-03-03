package io.gabrielcosta.gocine.entity.vo;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by gabriel on 3/2/17.
 */

public class MovieDetailVO extends MoviesResponseVO {

  @SerializedName("runtime")
  private final int runtime;

  private MovieDetailVO(String posterPath, boolean adult, String overview,
      String releaseDate, List<Integer> genreIds, int id, String originalTitle,
      String originalLanguage, String title, String backdropPath, double popularity, int voteCount,
      boolean video, float voteAverage, int runtime) {
    super(posterPath, adult, overview, releaseDate, genreIds, id, originalTitle, originalLanguage,
        title, backdropPath, popularity, voteCount, video, voteAverage);
    this.runtime = runtime;
  }

  public int getRuntime() {
    return runtime;
  }
}
