package org.acf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author aflorea.
 */

@SpringBootApplication
public class WebServerConfig extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebServerConfig.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(WebServerConfig.class, args);
	}
}
