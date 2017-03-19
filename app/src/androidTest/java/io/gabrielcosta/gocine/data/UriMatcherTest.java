package io.gabrielcosta.gocine.data;

import static junit.framework.Assert.assertEquals;

import android.content.UriMatcher;
import android.net.Uri;
import org.junit.Test;

/**
 * Created by gabrielcosta on 19/03/17.
 */

public final class UriMatcherTest {

  private final Uri movieUri = MovieEntry.BASE_CONTENT_URI;
  private final Uri movieWithIdUri = MovieEntry.buildMovieUri(10000);

  @Test
  public void shouldRespondWithMovieEntry() {
    final UriMatcher uriMatcher =
        MovieProvider.buildUriMatcher();

    assertEquals("Uri matches for movie is matched wrong", uriMatcher.match(movieUri),
        MovieProvider.MOVIE);

  }

  @Test
  public void shouldRespondWithMovieEntryId() {
    final UriMatcher uriMatcher =
        MovieProvider.buildUriMatcher();

    assertEquals("Uri matches for movie id (detail) is matched wrong", uriMatcher.match(movieWithIdUri),
        MovieProvider.MOVIE_WITH_ID);
  }

}
