package io.gabrielcosta.gocine.util;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.UrlPathPattern;

/**
 * Created by gabrielcosta on 28/02/17.
 */

public abstract class BaseUnitTest {

  protected static final int WIRE_MOCK_PORT = 8089;
  private static final String WIRE_MOCK_URL = "http://localhost";
  protected static final String WIRE_MOCK_FINAL_URL = String
      .format("%1$S:%2$S/", WIRE_MOCK_URL, WIRE_MOCK_PORT);
  protected static final String WIRE_MOCK_API = "apiKeyMock";
  protected static final long DEF_SERVICE_ASYNC_WAIT = 100L;

  protected void sleep(final long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  protected void stubService(final String url, final String file, final int status) {
    WireMock.reset();
    stubService(urlPathEqualTo(url), file, status);
  }

  protected void stubService(final UrlPathPattern urlPathPattern, final String file,
      final int status) {
    stubFor(get(urlPathPattern)
        .willReturn(aResponse()
            .withStatus(status)
            .withBody(TestUtil.readFile(file))));
  }

}
