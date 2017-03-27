package io.gabrielcosta.gocine.entity.vo;

import android.database.Cursor;
import com.google.gson.annotations.SerializedName;
import io.gabrielcosta.gocine.data.MovieEntry;
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

  public MovieDetailVO(final Cursor cursor) {
    super(cursor.getString(MovieEntry.COLUMN_POSTER_INDEX), Boolean.FALSE,
        cursor.getString(MovieEntry.COLUMN_OVERVIEW_INDEX),
        cursor.getString(MovieEntry.COLUMN_RELEASE_DATE_INDEX), null,
        cursor.getInt(MovieEntry.COLUMN_ID_INDEX), null,
        null,
        cursor.getString(MovieEntry.COLUMN_TITLE_INDEX),
        cursor.getString(MovieEntry.COLUMN_BACKDROP_INDEX), 0, 0, Boolean.FALSE,
        cursor.getFloat(MovieEntry.COLUMN_VOTE_AVERAGE_INDEX));
    runtime = cursor.getInt(MovieEntry.COLUMN_RUNTIME_INDEX);
  }

  public int getRuntime() {
    return runtime;
  }
}
