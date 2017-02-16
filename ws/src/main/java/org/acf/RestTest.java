package org.acf;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * @author aflorea.
 */

@RestController
@RequestMapping("/RESTTest")
public class RestTest {

	final static Logger logger = Logger.getLogger(RestTest.class);

	@RequestMapping(method = { PUT, GET })
	public String greeting(@RequestBody(required = false) String input) {
		if (logger.isInfoEnabled()) {
			logger.info("Received request for correlationId " + input);
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String result = input + " - Hurray from the service at " + new java.util.Date();

		if (logger.isInfoEnabled()) {
			logger.info("Request processed for correlationId " + input);
		}

		if (Math.random() > 0.1) {
			return result;
		} else {
			throw new InternalError("Buba!!!");
		}
	}

}
