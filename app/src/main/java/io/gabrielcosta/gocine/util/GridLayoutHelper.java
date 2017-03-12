package io.gabrielcosta.gocine.util;

import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by gabrielcosta on 11/03/17.
 */

public final class GridLayoutHelper {

  private GridLayoutHelper() {
    throw new RuntimeException();
  }

  public static int getNumberOfColumns(final WindowManager windowManager, final int gridItem) {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    windowManager.getDefaultDisplay().getMetrics(displayMetrics);
    int width = displayMetrics.widthPixels;
    int nColumns = width / gridItem;
    if (nColumns < 2) return 2;
    return nColumns;
  }

}
