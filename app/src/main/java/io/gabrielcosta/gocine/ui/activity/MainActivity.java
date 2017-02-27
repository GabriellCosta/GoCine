package io.gabrielcosta.gocine.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import io.gabrielcosta.gocine.R;
import io.gabrielcosta.gocine.adapter.PopularMoviesAdapter;
import io.gabrielcosta.gocine.entity.vo.MoviesResponseVO;
import io.gabrielcosta.gocine.presenter.MainPresenter;
import io.gabrielcosta.gocine.util.EndlessRecyclerOnScrollListener;
import io.gabrielcosta.gocine.view.MainView;
import java.util.List;

public class MainActivity extends BaseActivity implements MainView {

  private static final String SAVED_LAYOUT_MANAGER = "SAVED_LAYOUT_MANAGER";
  private MainPresenter mainPresenter;
  private final int GRID_SIZE = 2;
  private RecyclerView recyclerView;
  private GridLayoutManager gridLayoutManager;
  private PopularMoviesAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mainPresenter = MainPresenter.newInstance(this);
    initRecycler();
    mainPresenter.fetchPopularMovies();

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
  public void setPopularMovieList(List<MoviesResponseVO> movieList) {
    adapter.addItens(movieList);
  }

  @Override
  public void onError(String errorMessage) {
    showErrorMessage(errorMessage);
  }

  private void initRecycler() {
    recyclerView = (RecyclerView) findViewById(R.id.rv_main);
    adapter = new PopularMoviesAdapter();
    recyclerView.setAdapter(adapter);
    gridLayoutManager = new GridLayoutManager(this, GRID_SIZE);
    recyclerView.setLayoutManager(gridLayoutManager);
    recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
      @Override
      public void onLoadMore(final int currentPage) {
        mainPresenter.getNextMoviePage();
      }
    });
  }
}
