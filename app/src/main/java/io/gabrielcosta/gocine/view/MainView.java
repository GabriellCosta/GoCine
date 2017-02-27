package io.gabrielcosta.gocine.view;

import io.gabrielcosta.gocine.entity.vo.MoviesResponseVO;
import java.util.List;

/**
 * Created by gabriel on 2/24/17.
 */

public interface MainView {

  void setPopularMovieList(final List<MoviesResponseVO> popularMovieList);

  void onError(final String errorMessage);

}
