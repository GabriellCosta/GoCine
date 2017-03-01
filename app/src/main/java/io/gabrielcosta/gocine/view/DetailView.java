package io.gabrielcosta.gocine.view;

import io.gabrielcosta.gocine.entity.vo.ErrorApiVO;
import io.gabrielcosta.gocine.entity.vo.ReviewVO;
import java.util.List;

/**
 * Created by gabriel on 3/1/17.
 */

public interface DetailView {

  void setReviews(final List<ReviewVO> reviews);

  void setEmptyReviews();

  void onError(final ErrorApiVO errorApiVO);

}
