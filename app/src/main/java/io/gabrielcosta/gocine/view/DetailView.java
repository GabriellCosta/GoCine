package io.gabrielcosta.gocine.view;

import io.gabrielcosta.gocine.entity.vo.ErrorApiVO;
import io.gabrielcosta.gocine.entity.vo.ReviewVO;
import io.gabrielcosta.gocine.entity.vo.VideoVO;
import java.util.List;

/**
 * Created by gabriel on 3/1/17.
 */

public interface DetailView {

  void setReviews(final List<ReviewVO> reviews);

  void setEmptyReviews();

  void setVideos(final List<VideoVO> videos);

  void setEmptyVideos();

  void onError(final ErrorApiVO errorApiVO);

  void setMovieTitle(final String title);

  void setMovieDescription(final String description);

  void setMovieYear(final int year);

  void setMovieDuration(final int duration);

  void setMovieRatio(final float min);

  void setMoviePoster(final String posterPath);

  void setMovieBackgroundImage(final String backgroundPath);

}
