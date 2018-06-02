package com.yoogurt.kettle;

import com.yoogurt.kettle.config.EnvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({EnvConfig.class})
public class KettleSyncBootstrap {

	public static void main(String[] args) {
		SpringApplication.run(KettleSyncBootstrap.class, args);
	}
}
