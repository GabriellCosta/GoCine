package io.gabrielcosta.gocine.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import io.gabrielcosta.gocine.R;
import io.gabrielcosta.gocine.adapter.ReviewAdapter;
import io.gabrielcosta.gocine.adapter.VideoAdapter;
import io.gabrielcosta.gocine.entity.vo.ErrorApiVO;
import io.gabrielcosta.gocine.entity.vo.ReviewVO;
import io.gabrielcosta.gocine.entity.vo.VideoVO;
import io.gabrielcosta.gocine.model.FavoriteContextPackage;
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
  private Button favoriteButton;
  private ProgressBar progress;

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
    final RecyclerView recyclerView = findView(R.id.rv_detail_video);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(new VideoAdapter(videos));
  }

  @Override
  public void setEmptyVideos() {
    findViewById(R.id.include_detail_video).setVisibility(View.GONE);
  }

  @Override
  public void onError(ErrorApiVO errorApiVO) {

  }

  @Override
  public void setMovieTitle(String title) {
    getSupportActionBar().setTitle(title);
    removeProgressBar();
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

  @Override
  public void setFavorite() {
    Toast.makeText(this, "Salvo", Toast.LENGTH_LONG).show();
    favoriteButton.setText(getString(R.string.detail_favorited));
  }

  @Override
  public void showFavoriteOption(boolean show) {
    if (show) {
      favoriteButton.setText(getString(R.string.detail_favorited));
    }

    favoriteButton.setClickable(!show);
  }

  private void init() {
    presenter = DetailPresenter.newInstance(this, new FavoriteContextPackage(this));
    setSupportActionBar((Toolbar) findViewById(R.id.toolbar_detail));
    getSupportActionBar().setDisplayHomeAsUpEnabled(Boolean.TRUE);
    progress = (ProgressBar) findViewById(R.id.progressbar_detail);
    favoriteButton = (Button) findViewById(R.id.button_detail_favorite);
    favoriteButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        presenter.favoriteMovie();
        v.setClickable(Boolean.FALSE);
        favoriteButton.setText(getString(R.string.detail_favorited));
      }
    });
  }

  private void fetchData() {
    presenter.getMovieDetail(movieId);
    presenter.fetchReviews(movieId);
    presenter.fetchVideos(movieId);
    presenter.checkMovieExistence(movieId);
  }

  private void setExtras() {
    final Bundle extras = getIntent().getExtras();
    if (extras != null && extras.containsKey(MOVIE_ID_EXTRA)) {
      movieId = extras.getInt(MOVIE_ID_EXTRA);
    } else {
      throw new RuntimeException(getString(R.string.activity_detail_extra_error));
    }
  }

  private void removeProgressBar() {
    progress.setVisibility(View.GONE);
    findViewById(R.id.sv_detail).setVisibility(View.VISIBLE);
  }
}
