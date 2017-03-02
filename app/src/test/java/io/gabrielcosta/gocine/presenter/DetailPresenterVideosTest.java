package io.gabrielcosta.gocine.presenter;

import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.gabrielcosta.gocine.entity.vo.VideoVO;
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
 * Created by gabrielcosta on 01/03/17.
 */

public final class DetailPresenterVideosTest extends BaseUnitTest {

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
  public void shouldFetchVideosAndSetVideosOnView() {

    stubSucessService();
    robots.fetchVideos()
        .verifySetVideos();
  }

  @Test
  public void shouldFetchVideosAndSetEmptyOnView() {
    stubSucessEmptyService();

    robots.fetchVideos()
        .verifySetVideosEmpty();
  }

  private void stubSucessService() {
    stubService(urlPathMatching("/movie/.*/videos"), "videos.json", HttpStatus.OK_200);
  }

  private void stubSucessEmptyService() {
    stubService(urlPathMatching("/movie/.*/videos"), "videos_empty.json", HttpStatus.OK_200);
  }

  private final class DetailRobots {

    private DetailRobots fetchVideos() {
      detailPresenter.fetchVideos(245891);
      sleep(DEF_SERVICE_ASYNC_WAIT);
      return this;
    }

    private DetailRobots verifySetVideos() {
      verify(view, times(1)).setVideos(ArgumentMatchers.<VideoVO>anyList());
      return this;
    }

    private void verifySetVideosEmpty() {
      verify(view, times(1)).setEmptyVideos();
    }
  }

}
