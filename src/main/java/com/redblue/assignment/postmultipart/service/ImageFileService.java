package com.redblue.assignment.postmultipart.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.redblue.assignment.postmultipart.config.StorageProperties;
import com.redblue.assignment.postmultipart.domain.vo.FileVo;

import lombok.RequiredArgsConstructor;

/**
 * 일괄적으로 IllegalArgumentException을 던지도록 구현했지만 커스텀 Exception을 만들어 처리하는쪽이 더 나았을 것이라 생각한다.
 * 업로드 한 파일이 이미지 파일인지 아닌지 체크하기 위해 MIME Type등을 검증하는 방법도 생각해봤지만 우선은 체크하지 않고 구현했다.
 * 이미지 조회시 넘겨받은 파일명이 이미지 파일 확장자가 아니라면 예외처리를 하는것도 좋을 것 같다.
 * webp를 지원하지 않는다. 외부 라이브러리등을 사용하면 지원할 수 있지만 우선은 사용하지 않고 구현했다.
 */
@Service
@RequiredArgsConstructor
public class ImageFileService {

  private final StorageProperties properties;

  public void set(FileVo.Create create) {
    MultipartFile file = create.getFile();

    Path destinationFile = getDestinationFile(Paths.get(properties.getRootLocation()), getCleanPathOriginalFileName(file.getOriginalFilename()));

    if (!destinationFile.toFile().exists()) {
      createDirectories(destinationFile);
    }

    storeFile(file, destinationFile);
  }

  public String get(String fileName) {
    try {
      Path file = load(fileName);
      Resource resource = new UrlResource(file.toUri());
      if (!resource.exists() || !resource.isReadable()) {
        throw new IllegalArgumentException("파일을 읽을 수 없습니다.: " + fileName);
      }

      return generateHTMLCode(resource);
    }
    catch (MalformedURLException e) {
      throw new IllegalArgumentException("파일을 읽을 수 없습니다.: " + fileName, e);
    }
  }

  private String generateHTMLCode(Resource resource) {
    String path = null;

    try {
      path = resource.getFile().getPath();
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }

    return "<html><body><h1>Hello, HTML!</h1>" +
      "<img src=\"/" + path.replace("\\", "/") + "\" alt=\"Uploaded Image\">" +
      "</body></html>";
  }

  private void storeFile(MultipartFile file, Path destinationFile) {
    try (InputStream inputStream = file.getInputStream()) {
      Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new IllegalArgumentException("파일을 저장할 수 없습니다.");
    }
  }

  private String getCleanPathOriginalFileName(String originalFileName) {
    String filename = StringUtils.cleanPath(Objects.requireNonNull(originalFileName));

    if (filename.contains("..")) {
      throw new IllegalArgumentException("파일명을 확인해주세요." + filename);
    }

    return filename;
  }

  private Path getDestinationFile(Path rootLocation, String originalFileName) {
    Path destinationFile = rootLocation.resolve(Paths.get(originalFileName)).normalize().toAbsolutePath();

    if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
      throw new IllegalArgumentException("옳지 않은 디렉터리입니다.");
    }

    return destinationFile;
  }

  private void createDirectories(Path destinationFile) {
    try {
      Files.createDirectories(destinationFile);
    } catch (IOException e) {
      throw new IllegalArgumentException("디렉터리를 생성할 수 없습니다.");
    }
  }

  private Path load(String filename) {
    return Paths.get(properties.getRootLocation()).resolve(filename);
  }

}
