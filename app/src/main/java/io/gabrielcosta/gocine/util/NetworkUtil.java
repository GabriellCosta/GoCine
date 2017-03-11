package io.gabrielcosta.gocine.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by gabrielcosta on 11/03/17.
 */

public final class NetworkUtil {

  private NetworkUtil() {
    throw new RuntimeException();
  }

  public static boolean isOnline(final Context context) {
    ConnectivityManager cm =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo netInfo = cm.getActiveNetworkInfo();
    return netInfo != null && netInfo.isConnected();
  }

}
