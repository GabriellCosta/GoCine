package io.gabrielcosta.gocine.util;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by gabrielcosta on 05/03/17.
 */

public final class IntentUtil {

  private IntentUtil() {
    throw new RuntimeException();
  }

  public static Intent buildYoutubeIntent(final String key) {
    return new Intent(Intent.ACTION_VIEW, Uri.parse(
        String.format("http://www.youtube.com/watch?v=%s", key)));
  }

}
