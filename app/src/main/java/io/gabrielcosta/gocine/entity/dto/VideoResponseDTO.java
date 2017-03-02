package io.gabrielcosta.gocine.entity.dto;

import com.google.gson.annotations.SerializedName;
import io.gabrielcosta.gocine.entity.vo.VideoVO;
import java.util.Collections;
import java.util.List;

/**
 * Created by gabrielcosta on 01/03/17.
 */

public final class VideoResponseDTO {

  @SerializedName("results")
  private final List<VideoVO> result;

  private VideoResponseDTO(List<VideoVO> result) {
    this.result = result;
  }

  public List<VideoVO> getResult() {
    return Collections.unmodifiableList(result);
  }
}
