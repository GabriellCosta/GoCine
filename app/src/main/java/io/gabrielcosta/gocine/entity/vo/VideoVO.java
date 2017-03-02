package io.gabrielcosta.gocine.entity.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gabrielcosta on 01/03/17.
 */

public final class VideoVO {

  @SerializedName("name")
  private final String name;
  @SerializedName("key")
  private final String key;

  private VideoVO(String name, String key) {
    this.name = name;
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public String getKey() {
    return key;
  }
}
