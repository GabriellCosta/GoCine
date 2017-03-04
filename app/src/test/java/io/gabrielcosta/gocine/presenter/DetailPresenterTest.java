package io.gabrielcosta.gocine.presenter;

import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
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
  public void shouldSetMovieTitle() {
    stubMovieDetail();
    robots.getMovieDetail();
    verify(view, times(1)).setMovieTitle(anyString());
    verify(view).setMovieTitle("John Wick");
  }

  @Test
  public void shouldSetMovieYear() {
    stubMovieDetail();
    robots.getMovieDetail();
    verify(view, times(1)).setMovieYear(anyInt());
    verify(view).setMovieYear(2014);
  }

  @Test
  public void shouldSetMovieOverView() {
    stubMovieDetail();
    robots.getMovieDetail();
    verify(view, times(1)).setMovieDescription(anyString());
    verify(view).setMovieDescription("After the sudden death of his beloved wife, John Wick receives one last gift from her, a beagle puppy named Daisy, and a note imploring him not to forget how to love. But John's mourning is interrupted when his 1969 Boss Mustang catches the eye of sadistic thug Iosef Tarasov who breaks into his house and steals it, beating John unconscious in the process and kills his puppy. Unwittingly, he has just reawakened one of the most brutal assassins the underworld has ever known.");
  }

  @Test
  public void shouldSetMovieRuntime() {
    stubMovieDetail();
    robots.getMovieDetail();
    verify(view, times(1)).setMovieDuration(anyInt());
    verify(view).setMovieDuration(101);
  }

  @Test
  public void shouldSetMovieRatio() {
    stubMovieDetail();
    robots.getMovieDetail();
    verify(view, times(1)).setMovieRatio(anyFloat());
    verify(view).setMovieRatio(7.0f);
  }

  @Test
  public void shouldSetMoviePoster() {
    stubMovieDetail();
    robots.getMovieDetail();
    verify(view, times(1)).setMoviePoster(anyString());
    verify(view).setMoviePoster("/5vHssUeVe25bMrof1HyaPyWgaP.jpg");
  }

  @Test
  public void shouldSetMovieBackdrop() {
    stubMovieDetail();
    robots.getMovieDetail();
    verify(view, times(1)).setMovieBackgroundImage(anyString());
    verify(view).setMovieBackgroundImage("/mFb0ygcue4ITixDkdr7wm1Tdarx.jpg");
  }

  @Test
  public void shouldCallOnError() {
    mockUnauthorizedError();
    robots.getMovieDetail();
    verify(view).onError(any(ErrorApiVO.class));
  }

  private void stubMovieDetail() {
    stubService("/movie/13", "movie_detail.json", HttpStatus.OK_200);
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
