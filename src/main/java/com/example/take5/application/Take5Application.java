package com.example.take5.application;

import com.example.take5.config.SecurityConfig;
import com.example.take5.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories("com.example.take5.repository")
@SpringBootApplication(exclude= SecurityAutoConfiguration.class) //TODO - remove this
public class Take5Application {

	@Autowired
	ActivityRepository activityRepository;

	public static void main(String[] args) {
		SpringApplication.run(Take5Application.class, args);
	}

}
