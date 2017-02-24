package io.gabrielcosta.gocine.view;

import io.gabrielcosta.gocine.entity.vo.PopularMovieResponseVO;
import java.util.List;

/**
 * Created by gabriel on 2/24/17.
 */

public interface MainView {

  void refresh(final List<PopularMovieResponseVO> popularMovieList);

  void setPopularMovieList(final List<PopularMovieResponseVO> popularMovieList);

  void onError(final String errorMessage);

}
