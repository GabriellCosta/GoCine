package io.gabrielcosta.gocine.ui.activity;

import android.support.annotation.StringRes;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View.OnClickListener;
import io.gabrielcosta.gocine.R;

/**
 * Created by gabrielcosta on 26/02/17.
 */

public class BaseActivity extends AppCompatActivity {

  public <T> T findView(final int id) {
    return (T) findViewById(id);
  }

  void showError(final String message) {
    Snackbar
        .make(findViewById(android.R.id.content), message, BaseTransientBottomBar.LENGTH_INDEFINITE)
        .show();
  }

  void showError(@StringRes final int message) {
    Snackbar
        .make(findViewById(android.R.id.content), message, BaseTransientBottomBar.LENGTH_INDEFINITE)
        .show();
  }

  void showConnectionError(final OnClickListener onClickListener) {
    Snackbar.make(findViewById(android.R.id.content), R.string.no_internet_connection,
        BaseTransientBottomBar.LENGTH_INDEFINITE)
        .setAction(R.string.all_try_again, onClickListener)
        .show();
  }

}
