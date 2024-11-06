package com.seokwns.crawling;

import com.seokwns.crawling._core.config.EnvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@PropertySource(value = "classpath:properties/env.yaml",
		factory = EnvConfig.class)
@EnableScheduling
public class CrawlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlingApplication.class, args);
	}

}
