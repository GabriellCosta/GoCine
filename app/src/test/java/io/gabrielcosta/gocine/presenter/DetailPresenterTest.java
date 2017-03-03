package io.gabrielcosta.gocine.presenter;

import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.gabrielcosta.gocine.entity.vo.ErrorApiVO;
import io.gabrielcosta.gocine.model.service.MoviesServiceImpl;
import io.gabrielcosta.gocine.util.BaseUnitTest;
import io.gabrielcosta.gocine.view.DetailView;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Created by gabrielcosta on 03/03/17.
 */

public class DetailPresenterTest extends BaseUnitTest {

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
  public void shouldCallOnError() {
    mockUnauthorizedError();
    robots.getMovieDetail();
    verify(view).onError(any(ErrorApiVO.class));
  }

  private void mockUnauthorizedError() {
    stubService(urlPathMatching("/movie/.*"), "unauthorized_401.json",
        HttpStatus.UNAUTHORIZED_401);
  }

  private class DetailRobots {
    private DetailRobots getMovieDetail() {
      detailPresenter.getMovieDetail(13);
      sleep(DEF_SERVICE_ASYNC_WAIT);
      return this;
    }
  }

}
