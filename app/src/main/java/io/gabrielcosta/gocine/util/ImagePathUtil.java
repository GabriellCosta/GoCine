package io.gabrielcosta.gocine.util;

import android.content.Context;
import io.gabrielcosta.gocine.R;

/**
 * Created by gabrielcosta on 04/03/17.
 */

public final class ImagePathUtil {

  private ImagePathUtil() {
    throw new RuntimeException();
  }

  /**
   * Retrive image urlPath
   * @param context contex
   * @param imageSize image size used by api
   * @param imagePath image path
   * @return a string for the image url
   */
  public static String getImagePath(final Context context, final int imageSize,
      final String imagePath) {
    return context.getResources()
        .getString(R.string.api_image_url,
            context.getResources().getString(imageSize),
            imagePath);
  }

}
