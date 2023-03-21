package com.example.siren;

import com.example.siren.config.JpaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootApplication
public class SirenApplication {

	public static void main(String[] args) {
		SpringApplication.run(SirenApplication.class, args);
	}

}
