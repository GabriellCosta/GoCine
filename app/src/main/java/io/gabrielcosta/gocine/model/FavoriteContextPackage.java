package io.gabrielcosta.gocine.model;

import android.content.Context;

/**
 * Created by gabrielcosta on 24/03/17.
 */

public final class FavoriteContextPackage {

  private final Context context;

  public FavoriteContextPackage(Context context) {
    this.context = context;
  }

  public Context getContext() {
    return context;
  }
}
