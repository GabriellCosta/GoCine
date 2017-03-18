package io.gabrielcosta.gocine.data;

import android.net.Uri;

/**
 * Created by gabrielcosta on 18/03/17.
 */

public final class MovieContract {

  static final String CONTENT_AUTHORITY = "io.gabrielcosta.gocine";

  // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
  // the content provider.
  static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
}
