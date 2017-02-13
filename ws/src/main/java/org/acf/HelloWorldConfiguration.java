package org.acf;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.AbstractProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

/**
 * @autor aflorea.
 */

@SpringBootApplication
public class HelloWorldConfiguration {
	public static void main(String[] args) {
		SpringApplication.run(HelloWorldConfiguration.class, args);
	}

	@Bean
	public EmbeddedServletContainerFactory getEmbeddedServletContainerFactory() {
		TomcatEmbeddedServletContainerFactory containerFactory = new TomcatEmbeddedServletContainerFactory();
		containerFactory
						.addConnectorCustomizers(new TomcatConnectorCustomizer() {
							public void customize(Connector connector) {
								((AbstractProtocol) connector.getProtocolHandler())
												.setKeepAliveTimeout(3000);
								((AbstractProtocol) connector.getProtocolHandler())
												.setSoTimeout(3000);
								((AbstractProtocol) connector.getProtocolHandler())
												.setConnectionTimeout(3000);
							}
						});

		return containerFactory;
	}
}
