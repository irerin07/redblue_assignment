package com.redblue.assignment.postmultipart.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_ABSENT, content = JsonInclude.Include.NON_EMPTY)
public class FileVo {

  @Getter
  @Setter
  @NoArgsConstructor
  public static class Create {

    @NotEmpty(message = "파일을 업로드해 주세요.")
    private MultipartFile file;

  }

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  public static class Response {
    private String imaTag;

    public Response(String imaTag) {
      this.imaTag = imaTag;
    }

    public static Response toVo(String absolutePath) {
      return Response.builder().imaTag("String.format()").build();
    }

  }

}
