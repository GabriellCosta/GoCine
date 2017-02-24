package io.gabrielcosta.gocine.model.retrofit;

import static io.gabrielcosta.gocine.BuildConfig.API_KEY;

import android.support.annotation.NonNull;
import io.gabrielcosta.gocine.BuildConfig;
import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gabrielcosta on 09/02/17.
 */

public class RetrofitModelImpl implements RetrofitModel {

  private static RetrofitModelImpl ourInstance = new RetrofitModelImpl();

  static RetrofitModelImpl getInstance() {
    return ourInstance;
  }

  private RetrofitModelImpl() {
  }

  @NonNull
  @Override
  public Retrofit getRetrofit() {
    return new Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildOkHttpClient())
        .build();
  }

  private OkHttpClient buildOkHttpClient() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient.Builder httpClient =
        new OkHttpClient.Builder();
    httpClient.addInterceptor(new MovieApiInterceptor());
    httpClient.addInterceptor(logging);

    return httpClient.build();
  }

  private class MovieApiInterceptor implements Interceptor {

    private static final String QUERY_KEY = "api_key";

    @Override
    public Response intercept(Chain chain) throws IOException {
      Request original = chain.request();
      HttpUrl originalHttpUrl = original.url();

      HttpUrl url = originalHttpUrl.newBuilder()
          .addQueryParameter(QUERY_KEY, API_KEY)
          .build();

      Request.Builder requestBuilder = original.newBuilder()
          .url(url);

      Request request = requestBuilder.build();
      return chain.proceed(request);
    }
  }
}
