package io.gabrielcosta.gocine.entity.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gabrielcosta on 01/03/17.
 */

public final class ReviewVO {

  @SerializedName("id")
  private final int id;
  @SerializedName("author")
  private final String author;
  @SerializedName("content")
  private final String content;
  @SerializedName("url")
  private final String url;

  private ReviewVO(int id, String author, String content, String url) {
    this.id = id;
    this.author = author;
    this.content = content;
    this.url = url;
  }

  public int getId() {
    return id;
  }

  public String getAuthor() {
    return author;
  }

  public String getContent() {
    return content;
  }

  public String getUrl() {
    return url;
  }
}
