package io.gabrielcosta.gocine.data;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by gabrielcosta on 19/03/17.
 */

public final class MovieEntryTest {

  public static final int COLUMN_ID_VALUE = 1;
  public static final String COLUMN_TITLE_VALUE = "My Title";
  public static final String COLUMN_OVERVIEW_VALUE = "My Overview";
  public static final String COLUMN_POSTER_VALUE = "poster path";
  public static final String COLUMN_BACKDROP_VALUE = "backdrop path";
  public static final int COLUMN_RUNTIME_VALUE = 12;
  public static final double COLUMN_AVERAGE_VALUE = 35.4;
  public static final int COLUMN_DATE_VALUE = 31231231;
  public static final String ALTERED_TITLE = "Altered Title";
  private Context context;

  @Before
  public void before() {
    context = InstrumentationRegistry.getTargetContext();
    context.getContentResolver().delete(MovieEntry.BASE_CONTENT_URI, null, null);
  }

  @Test
  public void shouldHaveProviderRegistred() {
    PackageManager pm = context.getPackageManager();

    // We define the component name based on the package name from the context and the
    // WeatherProvider class.
    ComponentName componentName = new ComponentName(context.getPackageName(),
        MovieProvider.class.getName());
    try {
      // Fetch the provider info using the component name from the PackageManager
      // This throws an exception if the provider isn't registered.
      ProviderInfo providerInfo = pm
          .getProviderInfo(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);

      // Make sure that the registered authority matches the authority from the Contract.
      assertEquals("Error: MovieProvider registered with authority: " + providerInfo.authority +
              " instead of authority: " + MovieContract.CONTENT_AUTHORITY,
          providerInfo.authority, MovieContract.CONTENT_AUTHORITY);
    } catch (PackageManager.NameNotFoundException e) {
      // I guess the provider isn't registered correctly.
      assertTrue("Error: MovieProvider not registered at " + context.getPackageName(),
          false);
    }
  }

  @Test
  public void shouldInsert() {
    Uri insert = context.getContentResolver()
        .insert(MovieEntry.BASE_CONTENT_URI, generateMoviesValues());

    long InsertedUriId = ContentUris.parseId(insert);

    assertTrue("Values wasn't inserted", InsertedUriId != -1);

    final Cursor cursor = context.getContentResolver().query(
        MovieEntry.BASE_CONTENT_URI,
        null, // leaving "columns" null just returns all the columns.
        null, // cols for "where" clause
        null, // values for "where" clause
        null  // sort order
    );

    assertTrue("Number of rows is invalid", cursor.getCount() == 1);

    validateGeneratedMovieValues(cursor);

  }

  @Test
  public void shouldDelete() {

    context.getContentResolver()
        .insert(MovieEntry.BASE_CONTENT_URI, generateMoviesValues());

    final int delete = context.getContentResolver().delete(MovieEntry.BASE_CONTENT_URI, null, null);
    assertTrue("Values still remain on database", delete > 0);
  }

  @Test
  public void shouldQuery() {
    context.getContentResolver()
        .insert(MovieEntry.BASE_CONTENT_URI, generateMoviesValues());

    Cursor query = context.getContentResolver()
        .query(MovieEntry.BASE_CONTENT_URI, null, null, null, null);

    validateGeneratedMovieValues(query);
  }

  @Test
  public void shouldUpdate() {
    context.getContentResolver()
        .insert(MovieEntry.BASE_CONTENT_URI, generateMoviesValues());
    ContentValues updatedValues = generateMoviesValues();

    updatedValues.put(MovieEntry.COLUMN_TITLE, ALTERED_TITLE);
    int update = context.getContentResolver()
        .update(MovieEntry.BASE_CONTENT_URI, updatedValues, MovieEntry.COLUMN_TITLE +"=?",
            new String[]{COLUMN_TITLE_VALUE});

    assertTrue("No rows are updated", update > 0);

    final Cursor query = context.getContentResolver()
        .query(MovieEntry.BASE_CONTENT_URI, null, null, null, null);

    assertTrue("Update don't has values to query", query.moveToFirst());
    assertEquals(query.getString(MovieEntry.COLUMN_TITLE_INDEX), ALTERED_TITLE);
  }

  private ContentValues generateMoviesValues() {
    ContentValues contentValues = new ContentValues();
    contentValues.put(MovieEntry.COLUMN_ID, COLUMN_ID_VALUE);
    contentValues.put(MovieEntry.COLUMN_TITLE, COLUMN_TITLE_VALUE);
    contentValues.put(MovieEntry.COLUMN_OVERVIEW, COLUMN_OVERVIEW_VALUE);
    contentValues.put(MovieEntry.COLUMN_POSTER, COLUMN_POSTER_VALUE);
    contentValues.put(MovieEntry.COLUMN_BACKDROP, COLUMN_BACKDROP_VALUE);
    contentValues.put(MovieEntry.COLUMN_RUNTIME, COLUMN_RUNTIME_VALUE);
    contentValues.put(MovieEntry.COLUMN_VOTE_AVERAGE, COLUMN_AVERAGE_VALUE);
    contentValues.put(MovieEntry.COLUMN_RELEASE_DATE, COLUMN_DATE_VALUE);
    return contentValues;
  }

  private void validateGeneratedMovieValues(final Cursor cursor) {
    cursor.moveToFirst();
    assertEquals(cursor.getInt(MovieEntry.COLUMN_ID_INDEX), COLUMN_ID_VALUE);
    assertEquals(cursor.getString(MovieEntry.COLUMN_TITLE_INDEX), COLUMN_TITLE_VALUE);
    assertEquals(cursor.getString(MovieEntry.COLUMN_OVERVIEW_INDEX), COLUMN_OVERVIEW_VALUE);
    assertEquals(cursor.getString(MovieEntry.COLUMN_POSTER_INDEX), COLUMN_POSTER_VALUE);
    assertEquals(cursor.getString(MovieEntry.COLUMN_BACKDROP_INDEX), COLUMN_BACKDROP_VALUE);
    assertEquals(cursor.getInt(MovieEntry.COLUMN_RUNTIME_INDEX), COLUMN_RUNTIME_VALUE);
    assertEquals(cursor.getDouble(MovieEntry.COLUMN_VOTE_AVERAGE_INDEX), COLUMN_AVERAGE_VALUE);
    assertEquals(cursor.getInt(MovieEntry.COLUMN_RELEASE_DATE_INDEX), COLUMN_DATE_VALUE);
  }

}
