package com.att.idp.foundation.error;

/**
 * This Error is thrown when any business rule is failed dues to which program can not continue to fulfill the user transaction.
 * @author kamran
 *
 */
public class BusinessError extends AbstractError {
	
	
	/**
	 * serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new business error.
	 *
	 * @param message the message
	 */
	public BusinessError(final ExceptionErrorMessage ...errorMessages) {
		super(errorMessages);
	}

}