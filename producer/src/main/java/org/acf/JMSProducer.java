package org.acf;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.UUID;

/**
 * @author aflorea.
 */
public class JMSProducer {

	public static void main(String[] args) throws Exception {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		CamelContext camelContext = SpringCamelContext.springCamelContext(appContext, false);
		try {
			ProducerTemplate template = camelContext.createProducerTemplate();
			for (int i = 1; i <= 10000; i++) {
				if (i % 100 == 0) {
					int counter = i;
					System.out.println("Processed " + counter + " messages!");
				}
				template.sendBodyAndHeader("activemq:test.queue", "" + i, "JMSXGroupID", i % 100);
			}
		} finally {
			camelContext.stop();
		}
	}
}
