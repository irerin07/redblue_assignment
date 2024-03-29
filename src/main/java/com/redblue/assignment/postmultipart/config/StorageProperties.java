package com.redblue.assignment.postmultipart.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

  private String rootLocation = "upload-dir";

  public String getRootLocation() {
    return rootLocation;
  }

  public void setLocation(String location) {
    this.rootLocation = location;
  }

}
