package io.gabrielcosta.gocine.util;

/**
 * Created by gabrielcosta on 28/02/17.
 */

public abstract class BaseUnitTest {

  protected static final int WIRE_MOCK_PORT = 8089;
  private static final String WIRE_MOCK_URL = "http://localhost";
  protected static final String WIRE_MOCK_FINAL_URL = String.format("%1$S:%2$S/",WIRE_MOCK_URL, WIRE_MOCK_PORT);
  protected static final String WIRE_MOCK_API = "apiKeyMock";
  protected static final long DEF_SERVICE_ASYNC_WAIT = 100L;

  protected void sleep(final long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

}
