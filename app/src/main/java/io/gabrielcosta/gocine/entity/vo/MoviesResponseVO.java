package io.gabrielcosta.gocine.entity.vo;

import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.List;

public class MoviesResponseVO {

  @SerializedName("poster_path")
  private final String posterPath;
  @SerializedName("adult")
  private final boolean adult;
  @SerializedName("overview")
  private final String overview;
  @SerializedName("release_date")
  private final String releaseDate;
  @SerializedName("genre_ids")
  private final List<Integer> genreIds;
  @SerializedName("id")
  private final int id;
  @SerializedName("original_title")
  private final String originalTitle;
  @SerializedName("original_language")
  private final String originalLanguage;
  @SerializedName("title")
  private final String title;
  @SerializedName("backdrop_path")
  private final String backdropPath;
  @SerializedName("popularity")
  private final double popularity;
  @SerializedName("vote_count")
  private final int voteCount;
  @SerializedName("video")
  private final boolean video;
  @SerializedName("vote_average")
  private final double voteAverage;

  MoviesResponseVO(String posterPath, boolean adult, String overview,
      String releaseDate, List<Integer> genreIds, int id, String originalTitle,
      String originalLanguage, String title, String backdropPath, double popularity, int voteCount,
      boolean video, double voteAverage) {
    this.posterPath = posterPath;
    this.adult = adult;
    this.overview = overview;
    this.releaseDate = releaseDate;
    this.genreIds = genreIds;
    this.id = id;
    this.originalTitle = originalTitle;
    this.originalLanguage = originalLanguage;
    this.title = title;
    this.backdropPath = backdropPath;
    this.popularity = popularity;
    this.voteCount = voteCount;
    this.video = video;
    this.voteAverage = voteAverage;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public boolean isAdult() {
    return adult;
  }

  public String getOverview() {
    return overview;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public List<Integer> getGenreIds() {
    return Collections.unmodifiableList(genreIds);
  }

  public int getId() {
    return id;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public String getOriginalLanguage() {
    return originalLanguage;
  }

  public String getTitle() {
    return title;
  }

  public String getBackdropPath() {
    return backdropPath;
  }

  public double getPopularity() {
    return popularity;
  }

  public int getVoteCount() {
    return voteCount;
  }

  public boolean isVideo() {
    return video;
  }

  public double getVoteAverage() {
    return voteAverage;
  }
}
