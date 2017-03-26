package io.gabrielcosta.gocine.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import io.gabrielcosta.gocine.data.MovieEntry;
import io.gabrielcosta.gocine.entity.vo.MovieDetailVO;

/**
 * Created by gabrielcosta on 24/03/17.
 */

public final class FavoriteMovieModel {

  private final Context context;

  public FavoriteMovieModel(FavoriteContextPackage contextPackage) {
    context = contextPackage.getContext();
  }

  public void saveMovieOnDatabase(final MovieDetailVO movieDetailVO) {
    final ContentValues contentValues = generateContentValues(movieDetailVO);

    context.getContentResolver().insert(MovieEntry.BASE_CONTENT_URI, contentValues);

  }

  public boolean isSaved(final int movieId) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(MovieEntry.COLUMN_ID, movieId);

    final Cursor cursor = context.getContentResolver()
        .query(MovieEntry.BASE_CONTENT_URI, new String[]{MovieEntry.COLUMN_ID},
            MovieEntry.COLUMN_ID + "=?", new String[]{String.valueOf(movieId)}, null);

    final boolean result = cursor.getCount() > 0;
    cursor.close();
    return result;
  }

  private ContentValues generateContentValues(final MovieDetailVO movieDetailVO) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(MovieEntry.COLUMN_ID, movieDetailVO.getId());
    contentValues.put(MovieEntry.COLUMN_TITLE, movieDetailVO.getTitle());
    contentValues.put(MovieEntry.COLUMN_OVERVIEW, movieDetailVO.getOverview());
    contentValues.put(MovieEntry.COLUMN_POSTER, movieDetailVO.getPosterPath());
    contentValues.put(MovieEntry.COLUMN_BACKDROP, movieDetailVO.getBackdropPath());
    contentValues.put(MovieEntry.COLUMN_RELEASE_DATE, movieDetailVO.getReleaseDate());
    contentValues.put(MovieEntry.COLUMN_VOTE_AVERAGE, movieDetailVO.getVoteAverage());
    contentValues.put(MovieEntry.COLUMN_RUNTIME, movieDetailVO.getRuntime());

    return contentValues;
  }

}
