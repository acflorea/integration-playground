package org.acf;

public class HttpException extends RuntimeException {

	private static final long serialVersionUID = -5886602137536707676L;
	
	private final String message;
	private final Integer httpCode;
	private final String executionStep;
	private final String resourceUri;
	
	public HttpException(String message, Integer httpCode, String executionStep, String resourceUri){
		super(message);
		this.message = message;
		this.httpCode = httpCode;
		this.executionStep = executionStep;
		this.resourceUri = resourceUri;
	}

	public String getMessage() {
		return message;
	}

	public Integer getHttpCode() {
		return httpCode;
	}


	public String getExecutionStep() {
		return executionStep;
	}

	public String getResourceUri() {
		return resourceUri;
	}
}
