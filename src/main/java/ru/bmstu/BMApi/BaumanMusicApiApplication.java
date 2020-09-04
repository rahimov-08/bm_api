package ru.bmstu.BMApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BaumanMusicApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaumanMusicApiApplication.class, args);
	}

}
