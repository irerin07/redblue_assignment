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
 *
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
        throw new IllegalArgumentException("Could not read file: " + fileName);
      }

      return generateHTMLCode(resource);
    }
    catch (MalformedURLException e) {
      throw new IllegalArgumentException("Could not read file: " + fileName, e);
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
      throw new IllegalArgumentException(e);
    }
  }

  private String getCleanPathOriginalFileName(String originalFileName) {
    String filename = StringUtils.cleanPath(Objects.requireNonNull(originalFileName));

    if (filename.contains("..")) {
      throw new IllegalArgumentException("Filename contains invalid path sequence " + filename);
    }

    return filename;
  }

  private Path getDestinationFile(Path rootLocation, String originalFileName) {
    Path destinationFile = rootLocation.resolve(Paths.get(originalFileName)).normalize().toAbsolutePath();

    if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
      throw new IllegalArgumentException("Must Store Inside the current directory");
    }

    return destinationFile;
  }

  private void createDirectories(Path destinationFile) {
    try {
      Files.createDirectories(destinationFile);
    } catch (IOException e) {
      throw new IllegalArgumentException("Could Not Create Directories");
    }
  }

  private Path load(String filename) {
    return Paths.get(properties.getRootLocation()).resolve(filename);
  }

}
