package io.gabrielcosta.gocine.util;

import android.content.Context;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import io.gabrielcosta.gocine.R;

/**
 * Util class to use Picasso
 * Created by gabrielcosta on 04/03/17.
 */
public final class PicassoUtil {

  private PicassoUtil() {
    throw new RuntimeException();
  }

  /**
   * Load poster Image with his size get from device width, from resources
   * @param imageView imageView to receive the Image Poster
   * @param posterPath path of poster image
   */
  public static void buildPosterImage(final ImageView imageView, final String posterPath) {
    final Context context = imageView.getContext();
    Picasso.with(context)
        .load(getPosterPath(context, posterPath))
        .placeholder(R.color.colorPrimary)
        .into(imageView);
  }

  //Build the image url, getting image size from resources
  private static String getPosterPath(final Context context, final String posterPath) {
    return context.getResources()
        .getString(R.string.api_image_url,
            context.getResources().getString(R.string.api_poster_size),
            posterPath);
  }
}
