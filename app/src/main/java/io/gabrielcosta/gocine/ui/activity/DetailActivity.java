package io.gabrielcosta.gocine.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import io.gabrielcosta.gocine.R;
import io.gabrielcosta.gocine.adapter.ReviewAdapter;
import io.gabrielcosta.gocine.entity.vo.ErrorApiVO;
import io.gabrielcosta.gocine.entity.vo.ReviewVO;
import io.gabrielcosta.gocine.entity.vo.VideoVO;
import io.gabrielcosta.gocine.presenter.DetailPresenter;
import io.gabrielcosta.gocine.util.ImagePathUtil;
import io.gabrielcosta.gocine.util.PicassoUtil;
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
    final RecyclerView recyclerView = findView(R.id.rv_detail_review);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(new ReviewAdapter(reviews));
  }

  @Override
  public void setEmptyReviews() {
    findViewById(R.id.include_detail_review).setVisibility(View.GONE);
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
    getSupportActionBar().setTitle(title);
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
    textView.setText(String.format(getString(R.string.movie_detail_runtime_formatable), duration));
  }

  @Override
  public void setMovieRatio(float min) {
    TextView textView = findView(R.id.textview_detail_ratio);
    textView.setText(getString(R.string.movie_detail_ratio_formatable, min));
  }

  @Override
  public void setMoviePoster(String posterPath) {
    final ImageView imageView = (ImageView) findViewById(R.id.imageview_detail_poster);
    final String imagePath = ImagePathUtil
        .getImagePath(this, R.string.api_poster_detail_size, posterPath);
    PicassoUtil.buildImage(imageView, imagePath);
  }

  @Override
  public void setMovieBackgroundImage(String backgroundPath) {
    final ImageView imageView = findView(R.id.imageview_detail_backdrop);
    final String imagePath = ImagePathUtil
        .getImagePath(this, R.string.api_backdrop_size, backgroundPath);
    PicassoUtil.buildImage(imageView, imagePath);

  }

  private void init() {
    presenter = DetailPresenter.newInstance(this);
    setSupportActionBar((Toolbar) findViewById(R.id.toolbar_detail));
    getSupportActionBar().setDisplayHomeAsUpEnabled(Boolean.TRUE);
  }

  private void fetchData() {
    presenter.getMovieDetail(movieId);
    presenter.fetchReviews(movieId);
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
