package org.acf;

/**
 * @author aflorea.
 */
public class FourOFourException extends HttpException {

	public FourOFourException(String message, String executionStep, String resourceUri) {
		super(message, 404, executionStep, resourceUri);
	}

}
