package io.gabrielcosta.gocine.model.retrofit;

import io.gabrielcosta.gocine.entity.vo.ErrorApiVO;
import java.io.IOException;
import java.lang.annotation.Annotation;
import okhttp3.ResponseBody;
import retrofit2.Converter;
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

  public ErrorApiVO parseError(final ResponseBody responseBody) {
    Converter<ResponseBody, ErrorApiVO> converter = retrofitModel
        .responseBodyConverter(ErrorApiVO.class, new Annotation[0]);

    try {
      return converter.convert(responseBody);
    } catch (IOException e) {
      return new ErrorApiVO(null, 0);
    }
  }

}
