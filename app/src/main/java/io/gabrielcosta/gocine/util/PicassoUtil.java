package io.gabrielcosta.gocine.util;

import android.content.Context;
import android.widget.ImageView;
import com.squareup.picasso.NetworkPolicy;
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

  public static void buildImage(final ImageView imageView, final String imagePath) {
    final Context context = imageView.getContext();
    boolean online = NetworkUtil.isOnline(context);
    Picasso.with(context)
        .load(imagePath)
        .placeholder(R.color.colorPrimary)
        .networkPolicy(online ? NetworkPolicy.NO_CACHE : NetworkPolicy.OFFLINE)
        .into(imageView);
  }
}
