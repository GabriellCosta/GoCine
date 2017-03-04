package io.gabrielcosta.gocine.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by gabrielcosta on 03/03/17.
 */

public final class DataUtil {

  private static final String API_DATE_PATTERN = "yyyy-MM-dd";

  private DataUtil() {
    throw new RuntimeException();
  }

  public static Date parseDate(final String date) {
    try {
      return new SimpleDateFormat(API_DATE_PATTERN, Locale.getDefault()).parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  public static int getYearFromDate(final String date) {
      final Calendar calendar = Calendar.getInstance();
      calendar.setTime(parseDate(date));
      return calendar.get(Calendar.YEAR);
  }

}
