package io.gabrielcosta.gocine.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by gabrielcosta on 18/03/17.
 */

public class MovieProvider extends ContentProvider {

  private final UriMatcher uriMatcher = buildUriMatcher();
  private MovieDBHelper movieDBHelper;

  static final int MOVIE = 100;
  static final int MOVIE_WITH_ID = 101;

  @Override
  public boolean onCreate() {
    movieDBHelper = new MovieDBHelper(getContext());
    return Boolean.TRUE;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
      @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    return null;
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {

    switch (uriMatcher.match(uri)) {
      case MOVIE:
        return MovieEntry.CONTENT_TYPE;
      case MOVIE_WITH_ID:
        return MovieEntry.CONTENT_ITEM_TYPE;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    return null;
  }

  @Override
  public int delete(@NonNull Uri uri, @Nullable String selection,
      @Nullable String[] selectionArgs) {
    return 0;
  }

  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
      @Nullable String[] selectionArgs) {
    return 0;
  }

  static UriMatcher buildUriMatcher() {
    // I know what you're thinking.  Why create a UriMatcher when you can use regular
    // expressions instead?  Because you're not crazy, that's why.

    // All paths added to the UriMatcher have a corresponding code to return when a match is
    // found.  The code passed into the constructor represents the code to return for the root
    // URI.  It's common to use NO_MATCH as the code for this case.
    final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    final String authority = MovieContract.CONTENT_AUTHORITY;

    matcher.addURI(authority, MovieEntry.PATH_MOVIE, MOVIE);

    // For each type of URI you want to add, create a corresponding code.
    matcher.addURI(authority, MovieEntry.PATH_MOVIE + "/#", MOVIE_WITH_ID);

    return matcher;
  }
}
