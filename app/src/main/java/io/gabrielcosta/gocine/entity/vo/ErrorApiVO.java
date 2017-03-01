package io.gabrielcosta.gocine.entity.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gabrielcosta on 28/02/17.
 */

public class ErrorApiVO {

  @SerializedName("status_message")
  private final String statusMessage;
  @SerializedName("status_code")
  private final  int statusCode;

  public ErrorApiVO(final String statusMessage, final int statusCode) {
    this.statusMessage = statusMessage;
    this.statusCode = statusCode;
  }

  public String getStatusMessage() {
    return statusMessage;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
