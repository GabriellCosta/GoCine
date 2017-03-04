package io.gabrielcosta.gocine;

import static junit.framework.Assert.assertEquals;

import io.gabrielcosta.gocine.util.BaseUnitTest;
import io.gabrielcosta.gocine.util.DataUtil;
import java.util.Calendar;
import org.junit.Test;

/**
 * Created by gabrielcosta on 03/03/17.
 */

public final class DataUtilTest extends BaseUnitTest {

  private static final String DATE = "2017-12-22";

  @Test
  public void shouldParseDate() {

    Calendar actual = Calendar.getInstance();
    actual.setTime(DataUtil.parseDate(DATE));
    Calendar expected = buildExpectedCalendar();

    assertEquals(actual.get(Calendar.DAY_OF_MONTH), expected.get(Calendar.DAY_OF_MONTH));
    assertEquals(actual.get(Calendar.MONTH), expected.get(Calendar.MONTH));
    assertEquals(actual.get(Calendar.YEAR), expected.get(Calendar.YEAR));
  }

  @Test
  public void shouldHaveYearAsParsed() {
    Calendar actual = Calendar.getInstance();
    actual.setTime(DataUtil.parseDate(DATE));

    assertEquals(DataUtil.getYearFromDate(DATE), 2017);
  }

  private Calendar buildExpectedCalendar() {
    Calendar expected = Calendar.getInstance();
    expected.set(Calendar.YEAR, 2017);
    expected.set(Calendar.MONTH, 11);
    expected.set(Calendar.DAY_OF_MONTH, 22);

    return expected;
  }

}
