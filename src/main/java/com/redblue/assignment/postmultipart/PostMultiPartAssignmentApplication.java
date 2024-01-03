package com.redblue.assignment.postmultipart;

import com.redblue.assignment.postmultipart.config.StorageProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class PostMultiPartAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostMultiPartAssignmentApplication.class, args);
	}

}
