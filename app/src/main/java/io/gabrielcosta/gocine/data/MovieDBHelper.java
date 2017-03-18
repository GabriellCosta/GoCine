package io.gabrielcosta.gocine.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import io.gabrielcosta.gocine.R;

/**
 * Created by gabrielcosta on 18/03/17.
 */

public final class MovieDBHelper extends SQLiteOpenHelper {

  // If you change the database schema, you must increment the database version.
  private static final int DATABASE_VERSION = 1;

  public static final String DATABASE_NAME = "movie.db";

  private final String movieQuery;

  public MovieDBHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    movieQuery = getMovieQuery(context);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(movieQuery);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(String.format("DROP TABLE IF EXISTS %s", MovieEntry.TABLE_NAME));
    onCreate(db);
  }

  private String getMovieQuery(final Context context) {
    return context
        .getString(R.string.query_table_movie, MovieEntry.TABLE_NAME, MovieEntry.COLUMN_ID,
            MovieEntry.COLUMN_TITLE, MovieEntry.COLUMN_POSTER, MovieEntry.COLUMN_BACKDROP,
            MovieEntry.COLUMN_OVERVIEW, MovieEntry.COLUMN_RELEASE_DATE,
            MovieEntry.COLUMN_VOTE_AVERAGE, MovieEntry.COLUMN_RUNTIME);
  }

}
