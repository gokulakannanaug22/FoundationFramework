package com.att.idp.foundation.error;

/**
 * This Error is thrown when code has encountered any situation which can not be handled and program can not continue to
 * complete user request.This situation happens when user has not provided all the required input or have provided incorrect input 
 * to start the user request. This situation can always be avoided if user retries the request with correct input values.
 * @author kamran
 *
 */
public class InputError extends AbstractError {
	
	/**
	 * serial version uid.
	 */
	private static final long serialVersionUID = -1199203987091964448L;

	/**
	 * Instantiates a new input error.
	 *
	 * @param message the message
	 */
	public InputError(final ExceptionErrorMessage ...errorMessages) {
		super(errorMessages);
	}

}