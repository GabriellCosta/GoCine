package io.gabrielcosta.gocine.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import io.gabrielcosta.gocine.R;
import io.gabrielcosta.gocine.adapter.PopularMoviesAdapter;
import io.gabrielcosta.gocine.entity.vo.MoviesResponseVO;
import io.gabrielcosta.gocine.model.service.MovieEndpointType;
import io.gabrielcosta.gocine.presenter.MainPresenter;
import io.gabrielcosta.gocine.util.EndlessRecyclerOnScrollListener;
import io.gabrielcosta.gocine.util.GridLayoutHelper;
import io.gabrielcosta.gocine.util.NetworkUtil;
import io.gabrielcosta.gocine.view.MainView;
import java.util.List;

public class MainActivity extends BaseActivity implements MainView {

  private static final String SAVED_LAYOUT_MANAGER = "SAVED_LAYOUT_MANAGER";
  private MainPresenter mainPresenter;
  private final int GRID_SIZE = 2;
  private RecyclerView recyclerView;
  private GridLayoutManager gridLayoutManager;
  private PopularMoviesAdapter adapter;
  private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

    mainPresenter = MainPresenter.newInstance(this);
    initRecycler(mainPresenter.getMoviesListReference());
    tryFetchMoview();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState
        .putParcelable(SAVED_LAYOUT_MANAGER, recyclerView.getLayoutManager().onSaveInstanceState());
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    gridLayoutManager
        .onRestoreInstanceState(savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER));
    super.onRestoreInstanceState(savedInstanceState);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return Boolean.TRUE;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {
      case R.id.main_menu_popular:
        mainPresenter.setMovieEndpointType(MovieEndpointType.POPULAR);
        break;
      case R.id.main_menu_top_rated:
        mainPresenter.setMovieEndpointType(MovieEndpointType.TOP_RATED);
        break;
    }

    mainPresenter.fetchMovies();
    endlessRecyclerOnScrollListener.reset();

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void setPopularMovieList(List<MoviesResponseVO> movieList) {
    adapter.notifyDataSetChanged();
  }

  @Override
  public void onError(String errorMessage) {
    showError(errorMessage);
  }

  private void initRecycler(List<MoviesResponseVO> movieList) {
    recyclerView = (RecyclerView) findViewById(R.id.rv_main);
    adapter = new PopularMoviesAdapter(movieList);
    recyclerView.setAdapter(adapter);
    gridLayoutManager = new GridLayoutManager(this, GridLayoutHelper
        .getNumberOfColumns(getWindowManager(),
            getResources().getDimensionPixelSize(R.dimen.api_post_detail_size_px)));
    recyclerView.setLayoutManager(gridLayoutManager);
    endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(gridLayoutManager) {
      @Override
      public void onLoadMore(final int currentPage) {
        tryGetNextMoviePage();
      }
    };
    recyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
  }

  private void tryFetchMoview() {
    if (NetworkUtil.isOnline(this)) {
      mainPresenter.fetchMovies();
    } else {
      showConnectionError(new OnClickListener() {
        @Override
        public void onClick(View v) {
          tryFetchMoview();
        }
      });
    }
  }

  private void tryGetNextMoviePage() {
    if (NetworkUtil.isOnline(getBaseContext())) {
      mainPresenter.getNextMoviePage();
    } else {
      showConnectionError(new OnClickListener() {
        @Override
        public void onClick(View v) {
          tryGetNextMoviePage();
        }
      });
    }
  }
}
