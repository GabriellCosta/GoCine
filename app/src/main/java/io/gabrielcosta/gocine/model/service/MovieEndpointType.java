package io.gabrielcosta.gocine.model.service;

/**
 * Created by gabrielcosta on 27/02/17.
 */

public enum MovieEndpointType {
  POPULAR(MoviesService.MOVIE_POPULAR_ENDPOINT), TOP_RATED(MoviesService.MOVIE_TOP_RATED_ENDPOINT),
  FAVORITE("");

  private final String endpoint;

  MovieEndpointType(String endpoint) {
    this.endpoint = endpoint;
  }

  public String getEndpoint() {
    return endpoint;
  }
}
