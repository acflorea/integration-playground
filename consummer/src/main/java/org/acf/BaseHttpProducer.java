package org.acf;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.component.http.HttpOperationFailedException;
import org.codehaus.jackson.JsonProcessingException;

import javax.annotation.Resource;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

public class BaseHttpProducer {

	@Resource(name = "camelTemplate")
	private ProducerTemplate producer;

	protected String getEndpoint() {
		return "http://localhost:8080/RESTTest";
	}

	@Handler
	public void sendRequest(Exchange exchange) throws JsonProcessingException {

		// call endpoint
		consumeHttpEndpoint(exchange);

		// treat response
		String executionStep = getProperty(exchange,
						MessageHeaderConstants.EXECUTION_STEP);
		String resourceURI = getProperty(exchange,
						MessageHeaderConstants.HTTP_RESOURCE_URI);

		if (exchange.getException() != null) {
			if (exchange.getException() instanceof HttpOperationFailedException) {
				HttpOperationFailedException exception = (HttpOperationFailedException) exchange
								.getException();
				throw new HttpException(exception.getMessage(),
								exception.getStatusCode(), executionStep, resourceURI);
			}
			if (exchange.getException() instanceof ConnectException) {
				ConnectException exception = (ConnectException) exchange
								.getException();
				throw new FourOFourException(exception.getMessage(),
								executionStep, resourceURI);
			}
			throw new HttpException(exchange.getException().getMessage(), null,
							executionStep, resourceURI);
		} else {
			Message out = exchange.getOut();
			int responseCode = out.getHeader(Exchange.HTTP_RESPONSE_CODE,
							Integer.class);
			if (responseCode != 200) {
				if (responseCode == 404) {
					throw new FourOFourException("Invalid response code",
									executionStep, resourceURI);
				} else {
					throw new HttpException("Invalid response code", responseCode,
									executionStep, resourceURI);
				}
			}
		}
	}

	public void consumeHttpEndpoint(Exchange exchange) {
		Map<String, Object> headers = getHeaders(exchange);
		headers.put(Exchange.HTTP_METHOD, "PUT");
		headers.put(Exchange.CONTENT_TYPE, "application/json");

		headers.put(MessageHeaderConstants.EXECUTION_STEP, this.getClass().getSimpleName());
		headers.put(MessageHeaderConstants.HTTP_RESOURCE_URI, getEndpoint());

		try {
			getProducer().send(getEndpoint(), exchange);
		} catch (RuntimeCamelException e1) {
			exchange.setException(e1);
		}
	}

	protected Map<String, Object> getHeaders(Exchange exchange) {
		Map<String, Object> headers = exchange.getIn().getHeaders();
		if (headers == null) {
			headers = new HashMap<>();
		}
		return headers;
	}

	private String getProperty(Exchange exchange, String propertyKey) {
		return (String) exchange.getIn().getHeader(propertyKey);
	}

	public ProducerTemplate getProducer() {
		return producer;
	}

	public void setProducer(ProducerTemplate producer) {
		this.producer = producer;
	}
}
