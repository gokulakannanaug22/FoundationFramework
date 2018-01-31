package com.att.idp.foundation.error;

/**
 * This Error is thrown when code has encountered any situation which can not be handled and program can not continue to
 * complete user request. This error is thrown due to gaps and issues in configurations and deployment of the micro service or its
 * external dependencies are not working as expected.  This error situation can be avoided by properly configuring 
 * the mS deployment and its external dependencies. Some of the situations when this error is throws are below.
 * 1. Database connection error
 * 2. File read/write error
 * 3. mS configurations read/write error
 * 4. Incorrect configuration value
 * 4. Other mS connectivity error
 * 6. CSI connectivity error
 * 7. COSC connectivity or configuration error
 * @author kamran
 *
 */
public class EnvironmentError extends AbstractError {
	
	/**
	 * seral version uid.
	 */
	private static final long serialVersionUID = -7273531869344730381L;

	/**
	 * Instantiates a new environment error.
	 *
	 * @param message the message
	 */
	public EnvironmentError(final ExceptionErrorMessage ...errorMessages) {
		super(errorMessages);
	}

}
