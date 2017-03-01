package io.gabrielcosta.gocine.presenter;

import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.gabrielcosta.gocine.entity.vo.ReviewVO;
import io.gabrielcosta.gocine.model.service.MoviesServiceImpl;
import io.gabrielcosta.gocine.util.BaseUnitTest;
import io.gabrielcosta.gocine.view.DetailView;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;

/**
 * Created by gabriel on 3/1/17.
 */

public final class DetailPresenterTest extends BaseUnitTest {

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(WIRE_MOCK_PORT));

  @Mock
  DetailView view;
  private DetailPresenter detailPresenter;
  private final DetailRobots robots = new DetailRobots();

  @Before
  public void before() {
    initMocks(this);
    detailPresenter = DetailPresenter.newInstance(view, MoviesServiceImpl
        .newInstance(WIRE_MOCK_FINAL_URL, WIRE_MOCK_API));
  }

  @Test
  public void shouldFetchReviewsAndSetReviewsOnView() {

    stubService(urlPathMatching("/movie/.*/reviews"), "review_page_1.json", HttpStatus.OK_200);
    robots.fetchReviews()
        .verifySetReviews(1);
  }

  @Test
  public void shouldFetchReviewsAndSetEmptyOnView() {
    stubService(urlPathMatching("/movie/.*/reviews"), "reviews_empty.json", HttpStatus.OK_200);

    robots.fetchReviews()
        .verifySetReviewsEmpty(1);
  }

  private final class DetailRobots {
    private DetailRobots fetchReviews() {
      detailPresenter.fetchReviews(245891);
      sleep(DEF_SERVICE_ASYNC_WAIT);
      return this;
    }

    private DetailRobots verifySetReviews(final int times) {
      verify(view, times(times)).setReviews(ArgumentMatchers.<ReviewVO>anyList());
      return this;
    }

    private void verifySetReviewsEmpty(final int times) {
      verify(view, times(times)).setEmptyReviews();
    }
  }

}
