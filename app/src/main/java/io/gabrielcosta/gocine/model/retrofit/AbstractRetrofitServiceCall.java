package io.gabrielcosta.gocine.model.retrofit;

import retrofit2.Retrofit;

/**
 * Created by gabrielcosta on 09/02/17.
 */

public abstract class AbstractRetrofitServiceCall {

  private Retrofit retrofitModel;

  public AbstractRetrofitServiceCall(Retrofit retrofitModel) {
    this.retrofitModel = retrofitModel;
  }

  protected <T> T getService(Class<T> t) {
    return retrofitModel.create(t);
  }

}
