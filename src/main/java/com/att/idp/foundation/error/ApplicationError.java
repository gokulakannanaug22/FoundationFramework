package com.att.idp.foundation.error;

/**
 * This Error is thrown when code has encountered any situation which can not be handled and program can not continue to
 * complete user request. These situations are due to gaps and issues in the program logic.
 * @author kamran
 *
 */
public class ApplicationError extends AbstractError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1933213925713546002L;

	/**
	 * Instantiates a new application error.
	 *
	 * @param message the message
	 */
	public ApplicationError(final ExceptionErrorMessage ...errorMessages) {
		super(errorMessages);
	}
	
}
