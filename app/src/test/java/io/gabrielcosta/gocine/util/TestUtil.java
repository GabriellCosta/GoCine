package io.gabrielcosta.gocine.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by gabrielcosta on 28/02/17.
 */

public class TestUtil {

  private static final String TEST_RESOURCE = "src/test/resources/";

  public static String readFile(final String resource) {
    try {
      return readFile(new File(TEST_RESOURCE, resource));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  private static String readFile(final File file) throws FileNotFoundException {
    return new Scanner(file).useDelimiter("\\Z").next();
  }

}
