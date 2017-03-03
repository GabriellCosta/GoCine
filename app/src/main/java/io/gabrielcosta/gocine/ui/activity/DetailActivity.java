package io.gabrielcosta.gocine.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import io.gabrielcosta.gocine.R;
import io.gabrielcosta.gocine.entity.vo.ErrorApiVO;
import io.gabrielcosta.gocine.entity.vo.ReviewVO;
import io.gabrielcosta.gocine.entity.vo.VideoVO;
import io.gabrielcosta.gocine.presenter.DetailPresenter;
import io.gabrielcosta.gocine.view.DetailView;
import java.util.List;

/**
 * Created by gabriel on 3/3/17.
 */

public class DetailActivity extends BaseActivity implements DetailView {

  public static final String MOVIE_ID_EXTRA = "MOVIE_ID_EXTRA";
  private int movieId;

  private DetailPresenter presenter;

  public static void newInstance(final Context context, final int movieId) {
    final Intent intent = new Intent(context, DetailActivity.class);
    intent.putExtra(MOVIE_ID_EXTRA, movieId);
    context.startActivity(intent);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    init();
    setExtras();
    fetchData();
  }

  @Override
  public void setReviews(List<ReviewVO> reviews) {

  }

  @Override
  public void setEmptyReviews() {

  }

  @Override
  public void setVideos(List<VideoVO> videos) {

  }

  @Override
  public void setEmptyVideos() {

  }

  @Override
  public void onError(ErrorApiVO errorApiVO) {

  }

  @Override
  public void setMovieTitle(String title) {
    TextView textView = findView(R.id.textview_detail_title);
    textView.setText(title);
  }

  @Override
  public void setMovieDescription(String description) {
    TextView textView = findView(R.id.textview_detail_overview);
    textView.setText(description);
  }

  @Override
  public void setMovieYear(int year) {
    TextView textView = findView(R.id.textview_detail_year);
    textView.setText(String.valueOf(year));
  }

  @Override
  public void setMovieDuration(int duration) {
    TextView textView = findView(R.id.textview_detail_runtime);
    textView.setText(String.valueOf(duration));
  }

  @Override
  public void setMovieRatio(float min) {
    TextView textView = findView(R.id.textview_detail_ratio);
    textView.setText(String.format("%s/10", min));
  }

  @Override
  public void setMoviePoster(String posterPath) {
    Picasso.with(this)
        .load(getString(R.string.api_image_url,
            getResources().getString(R.string.api_poster_detail_size),
            posterPath))
        .into(
            (ImageView) findViewById(R.id.imageview_detail_poster));
  }

  @Override
  public void setMovieBackgroundImage(String backgroundPath) {
    Picasso.with(this)
        .load(getString(R.string.api_image_url,
            getResources().getString(R.string.api_backdrop_size),
            backgroundPath))
        .into(
        (ImageView) findViewById(R.id.imageview_detail_backdrop));
  }

  private void init() {
    presenter = DetailPresenter.newInstance(this);
  }

  private void fetchData() {
   presenter.getMovieDetail(movieId);
  }

  private void setExtras() {
    final Bundle extras = getIntent().getExtras();
    if (extras != null && extras.containsKey(MOVIE_ID_EXTRA)) {
      movieId = extras.getInt(MOVIE_ID_EXTRA);
    } else {
      throw new RuntimeException(getString(R.string.activity_detail_extra_error));
    }
  }
}
