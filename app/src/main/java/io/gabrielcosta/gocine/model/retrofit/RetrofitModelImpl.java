package io.gabrielcosta.gocine.model.retrofit;

import android.support.annotation.NonNull;
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

  public static RetrofitModelImpl getInstance() {
    return ourInstance;
  }

  private RetrofitModelImpl() {
  }

  @NonNull
  @Override
  public Retrofit getRetrofit(final String baseUrl, final String apiKey) {
    return new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildOkHttpClient(apiKey))
        .build();
  }

  private OkHttpClient buildOkHttpClient(final String apiKey) {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient.Builder httpClient =
        new OkHttpClient.Builder();
    httpClient.addInterceptor(new MovieApiInterceptor(apiKey));
    httpClient.addInterceptor(logging);

    return httpClient.build();
  }

  private class MovieApiInterceptor implements Interceptor {

    private static final String QUERY_KEY = "api_key";
    private final String apiKey;

    private MovieApiInterceptor(final String apiKey) {
      this.apiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
      Request original = chain.request();
      HttpUrl originalHttpUrl = original.url();

      HttpUrl url = originalHttpUrl.newBuilder()
          .addQueryParameter(QUERY_KEY, apiKey)
          .build();

      Request.Builder requestBuilder = original.newBuilder()
          .url(url);

      Request request = requestBuilder.build();
      return chain.proceed(request);
    }
  }
}
