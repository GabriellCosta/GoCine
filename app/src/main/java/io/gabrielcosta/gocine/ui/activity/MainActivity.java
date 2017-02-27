package io.gabrielcosta.gocine.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import io.gabrielcosta.gocine.R;
import io.gabrielcosta.gocine.adapter.PopularMoviesAdapter;
import io.gabrielcosta.gocine.entity.vo.PopularMovieResponseVO;
import io.gabrielcosta.gocine.presenter.MainPresenter;
import io.gabrielcosta.gocine.util.EndlessRecyclerOnScrollListener;
import io.gabrielcosta.gocine.view.MainView;
import java.util.List;

public class MainActivity extends BaseActivity implements MainView {

  private MainPresenter mainPresenter;
  private final int GRID_SIZE = 2;
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
  public void setPopularMovieList(List<PopularMovieResponseVO> popularMovieList) {
    adapter.addItens(popularMovieList);
  }

  @Override
  public void onError(String errorMessage) {
    showErrorMessage(errorMessage);
  }

  private void initRecycler() {
    final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_main);
    adapter = new PopularMoviesAdapter();
    recyclerView.setAdapter(adapter);
    final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, GRID_SIZE);
    recyclerView.setLayoutManager(gridLayoutManager);
    recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
      @Override
      public void onLoadMore(final int currentPage) {
        mainPresenter.fetchPopularMovies();
      }
    });
  }
}
