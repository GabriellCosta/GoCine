package io.gabrielcosta.gocine.model.retrofit;

import android.support.annotation.NonNull;
import retrofit2.Retrofit;

/**
 * Created by gabrielcosta on 09/02/17.
 */

public interface RetrofitModel {

  @NonNull
  Retrofit getRetrofit();

}
