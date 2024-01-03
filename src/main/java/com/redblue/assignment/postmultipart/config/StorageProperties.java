package com.redblue.assignment.postmultipart.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 민경수
 * @description storage properties
 * @since 2024.01.02
 **********************************************************************************************************************/
@ConfigurationProperties("storage")
public class StorageProperties {

  /**
   * Folder location for storing files
   */
  private String rootLocation = "upload-dir";

  public String getRootLocation() {
    return rootLocation;
  }

  public void setLocation(String location) {
    this.rootLocation = location;
  }

}
