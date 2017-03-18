package io.gabrielcosta.gocine.data;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by gabrielcosta on 18/03/17.
 */

public final class MovieDbTest {

  public static final int COLUMN_NOT_EXISTS = -1;
  private Context context;

  @Before
  public void before() {
    context = InstrumentationRegistry.getTargetContext();
    context.deleteDatabase(MovieDBHelper.DATABASE_NAME);
  }

  @Test
  public void shouldCreateDatabase() {
    final SQLiteDatabase db = new MovieDBHelper(context).getWritableDatabase();
    assertEquals(true, db.isOpen());
  }

  @Test
  public void shouldHaveMovieTable() {
    final SQLiteDatabase readableDatabase = new MovieDBHelper(context).getReadableDatabase();
    Cursor query = readableDatabase
        .rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

    assertTrue("Database was not created correctly", query.moveToFirst());

    Set<String> movieSet = new HashSet<>();
    movieSet.add(MovieEntry.TABLE_NAME);

    do {
      movieSet.remove(query.getString(0));
    } while (query.moveToNext());

    assertTrue("Movie Table was not created", movieSet.isEmpty());

  }

  @Test
  public void shouldHaveIdColumn() {
    validateColumn(MovieEntry.COLUMN_ID);
  }

  @Test
  public void shouldHaveTitleColumn() {
    validateColumn(MovieEntry.COLUMN_TITLE);
  }

  @Test
  public void shouldHaveOverviewColumn() {
    validateColumn(MovieEntry.COLUMN_OVERVIEW);
  }

  @Test
  public void shouldHavePosterColumn() {
    validateColumn(MovieEntry.COLUMN_POSTER);
  }

  @Test
  public void shouldHaveBackdropColumn() {
    validateColumn(MovieEntry.COLUMN_BACKDROP);
  }

  @Test
  public void shouldHaveReleaseDateColumn() {
    validateColumn(MovieEntry.COLUMN_RELEASE_DATE);
  }

  @Test
  public void shouldHaveVoteAverageColumn() {
    validateColumn(MovieEntry.COLUMN_VOTE_AVERAGE);
  }

  @Test
  public void shouldHaveRuntimeColumn() {
    validateColumn(MovieEntry.COLUMN_RUNTIME);
  }

  private void validateColumn(final String column) {
    final SQLiteDatabase readableDatabase = new MovieDBHelper(context).getReadableDatabase();
    Cursor query = readableDatabase
        .query(MovieEntry.TABLE_NAME, null, null, null, null, null, null, null);

    assertTrue("Column does not exist",
        COLUMN_NOT_EXISTS != query.getColumnIndex(column));
    query.close();
  }

}
