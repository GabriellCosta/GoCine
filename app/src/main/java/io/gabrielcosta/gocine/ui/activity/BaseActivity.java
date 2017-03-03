package io.gabrielcosta.gocine.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by gabrielcosta on 26/02/17.
 */

public class BaseActivity extends AppCompatActivity {

  void showErrorMessage(final String errorMessage) {
    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
  }

  public <T> T findView(final int id) {
    return (T) findViewById(id);
  }

}
