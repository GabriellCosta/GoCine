package io.gabrielcosta.gocine.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import io.gabrielcosta.gocine.R;
import io.gabrielcosta.gocine.adapter.PopularMoviesAdapter;
import io.gabrielcosta.gocine.entity.vo.PopularMovieResponseVO;
import io.gabrielcosta.gocine.presenter.MainPresenter;
import io.gabrielcosta.gocine.view.MainView;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {

  private MainPresenter mainPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mainPresenter = MainPresenter.newInstance(this);
    mainPresenter.fetchPopularMovies();
  }

  @Override
  public void refresh(List<PopularMovieResponseVO> popularMovieList) {

  }

  @Override
  public void setPopularMovieList(List<PopularMovieResponseVO> popularMovieList) {
    final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_main);
    recyclerView.setAdapter(new PopularMoviesAdapter(popularMovieList));
    recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
  }

  @Override
  public void onError(String errorMessage) {

  }
}
