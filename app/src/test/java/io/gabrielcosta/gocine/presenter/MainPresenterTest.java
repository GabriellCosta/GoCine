package io.gabrielcosta.gocine.presenter;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.gabrielcosta.gocine.model.service.MovieEndpointType;
import io.gabrielcosta.gocine.model.service.MoviesServiceImpl;
import io.gabrielcosta.gocine.util.BaseUnitTest;
import io.gabrielcosta.gocine.view.MainView;
import java.util.List;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Created by gabriel on 2/24/17.
 */

public class MainPresenterTest extends BaseUnitTest {

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(WIRE_MOCK_PORT));

  @Mock
  MainView mainView;
  private MainPresenter mainPresenterImpl;
  private final MainRobots robots = new MainRobots();

  @Before
  public void before() {
    initMocks(this);
    mainPresenterImpl = MainPresenter
        .newInstance(mainView, MoviesServiceImpl.newInstance(WIRE_MOCK_FINAL_URL, WIRE_MOCK_API));
    mainPresenterImpl.setMovieEndpointType(MovieEndpointType.POPULAR);
  }

  @After
  public void after() {
    wireMockRule.shutdown();
  }

  @Test
  public void shouldSetPopularMoviesListOnView() {
    mockPopularMoviesService("popular_movies_page_1.json");

    robots.fetchMovies()
        .verifySetMovieEndpoint();
  }

  @Test
  public void moviesListShoulNotBeEmptyAfterService() {
    mockPopularMoviesService("popular_movies_page_1.json");

    robots.movieListisEmpty(Boolean.TRUE)
        .fetchMovies()
        .movieListisEmpty(Boolean.FALSE)
        .verifySetMovieEndpoint();
  }

  @Test
  public void movieListShouldBeEmptyAfterEndpointTypeChange() {
    mockPopularMoviesService("popular_movies_page_1.json");

    robots.movieListisEmpty(Boolean.TRUE)
        .fetchMovies()
        .movieListisEmpty(Boolean.FALSE)
        .verifySetMovieEndpoint()
        .changeEndpoint(MovieEndpointType.TOP_RATED)
        .movieListisEmpty(Boolean.TRUE);
  }

  @Test
  public void shouldNotClearMoviesListIfSameEndpoint() {
    mockPopularMoviesService("popular_movies_page_1.json");

    robots.movieListisEmpty(Boolean.TRUE)
        .fetchMovies()
        .movieListisEmpty(Boolean.FALSE)
        .verifySetMovieEndpoint()
        .changeEndpoint(MovieEndpointType.POPULAR)
        .movieListisEmpty(Boolean.FALSE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shoulThorwExceptionWhenMovieEndpointIsNull() {
    robots.changeEndpoint(null);
  }

  @Test
  public void shoulCallViewError() {
    mockUnauthorizedError();

    robots.fetchMovies()
        .verifyError("Invalid API key: You must be granted a valid key.");
  }

  @Test
  public void shouldCallViewErrorForNotFoundError() {
    mockNotFoundError();

    robots.fetchMovies()
        .verifyError("The resource you requested could not be found.");
  }

  @Test
  public void shouldReachPage2() {
    mockPopularMoviesService("popular_movies_page_1.json");
    mockPopularMoviesService("popular_movies_page_2.json");

    robots.fetchMovies()
        .fetchMovies()
        .verifyTimesPopulate(2);
  }

  @Test
  public void shouldHave40ItensOnMoviesList() {
    mockPopularMoviesService("popular_movies_page_1.json");
    mockPopularMoviesService("popular_movies_page_2.json");

    robots.fetchMovies()
        .getNextPage()
        .movieListisEmpty(Boolean.FALSE)
        .verifyNumberOfItensOnMoviesList(40);
  }

  @Test
  public void shouldCallViewErrorServiceError() {
    stubError();

    robots.fetchMovies();
    verify(mainView, times(1)).onError(anyString());
  }

  private void mockPopularMoviesService(final String mockFile) {
    stubService("/movie/popular", mockFile, HttpStatus.OK_200);
  }

  private void mockUnauthorizedError() {
    stubService("/movie/popular", "unauthorized_401.json", HttpStatus.UNAUTHORIZED_401);
  }

  private void mockNotFoundError() {
    stubService("/movie/popular", "not_found_404.json", HttpStatus.NOT_FOUND_404);
  }

  private void stubError() {
    WireMock.reset();
    stubFor(get(urlPathEqualTo("/movie/popular"))
        .willReturn(aResponse().withFault(Fault.MALFORMED_RESPONSE_CHUNK)));
  }

  private class MainRobots {

    private MainRobots fetchMovies() {
      mainPresenterImpl.fetchMovies();
      sleep(DEF_SERVICE_ASYNC_WAIT);
      return this;
    }

    private MainRobots getNextPage() {
      mainPresenterImpl.getNextMoviePage();
      sleep(DEF_SERVICE_ASYNC_WAIT);
      return this;
    }

    private MainRobots movieListisEmpty(final boolean is) {
      assertEquals(mainPresenterImpl.getMoviesListReference().isEmpty(), is);
      return this;
    }

    private MainRobots verifyTimesPopulate(final int times) {
      verify(mainView, times(times)).setPopularMovieList(any(List.class));
      return this;
    }

    private MainRobots verifySetMovieEndpoint() {
      verify(mainView, times(1)).setPopularMovieList(any(List.class));
      return this;
    }

    private MainRobots verifyError(final String errorString) {
      verify(mainView, times(1)).onError(errorString);
      return this;
    }

    private MainRobots changeEndpoint(final MovieEndpointType endpointType) {
      mainPresenterImpl.setMovieEndpointType(endpointType);
      return this;
    }

    private MainRobots verifyNumberOfItensOnMoviesList(final int quantity) {
      assertEquals(quantity, mainPresenterImpl.getMoviesListReference().size());
      return this;
    }

  }

}
