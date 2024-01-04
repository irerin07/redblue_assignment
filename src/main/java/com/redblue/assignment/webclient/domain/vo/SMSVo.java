package com.redblue.assignment.webclient.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_ABSENT, content = JsonInclude.Include.NON_EMPTY)
public class SMSVo {
  @Getter
  @Setter
  @NoArgsConstructor
  public static class Create {
    @JsonProperty("title")
    private String title;
    @JsonProperty("content")
    private String content;
    @JsonProperty("targetPhoneNumber")
    private String targetPhoneNumber;
  }

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  public static class Request {
    @JsonProperty("title")
    private String title;
    @JsonProperty("content")
    private String content;
    @JsonProperty("targetPhoneNumber")
    private String targetPhoneNumber;

    public Request(String title, String content, String targetPhoneNumber) {
      this.title = title;
      this.content = content;
      this.targetPhoneNumber = targetPhoneNumber;
    }

  }

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  public static class Response {
    @JsonProperty("message")
    private String message;

    public Response(String message) {
      this.message = message;
    }

    public static Response toVo() {
      return Response.builder().message("test").build();
    }

  }

}
