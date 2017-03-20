package io.gabrielcosta.gocine.data;

import static io.gabrielcosta.gocine.data.MovieContract.CONTENT_AUTHORITY;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;

/**
 * Created by gabrielcosta on 18/03/17.
 */

public class MovieEntry {

  public static final String PATH_MOVIE = "movie";

  public static final Uri BASE_CONTENT_URI = MovieContract.BASE_CONTENT_URI.buildUpon()
      .appendPath(PATH_MOVIE).build();

  public static final String CONTENT_TYPE =
      ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
  public static final String CONTENT_ITEM_TYPE =
      ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

  public static final String TABLE_NAME = "MOVIE";

  public static final String COLUMN_ID = "movie_id";
  public static final String COLUMN_TITLE = "title";
  public static final String COLUMN_POSTER = "poster";
  public static final String COLUMN_BACKDROP = "backdrop";
  public static final String COLUMN_OVERVIEW = "overview";
  public static final String COLUMN_RELEASE_DATE = "release_date";
  public static final String COLUMN_VOTE_AVERAGE = "vote_average";
  public static final String COLUMN_RUNTIME = "runtime";

  public static final int COLUMN_ID_INDEX = 0;
  public static final int COLUMN_TITLE_INDEX = 1;
  public static final int COLUMN_POSTER_INDEX = 2;
  public static final int COLUMN_BACKDROP_INDEX = 3;
  public static final int COLUMN_OVERVIEW_INDEX = 4;
  public static final int COLUMN_RELEASE_DATE_INDEX = 5;
  public static final int COLUMN_VOTE_AVERAGE_INDEX = 6;
  public static final int COLUMN_RUNTIME_INDEX = 7;

  public static Uri buildMovieUri(final long id) {
    return ContentUris.withAppendedId(BASE_CONTENT_URI, id);
  }
}
