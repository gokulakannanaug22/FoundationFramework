package com.att.idp.foundation.error;

/**
 * Abstract parent class for check exception.
 */
@SuppressWarnings("PMD.MethodReturnsInternalArray")
public abstract class AbstractException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3768670794934266376L;
	/** Error messages. */	
	private final ExceptionErrorMessage[] errorMessages;

	/**
	 * Instantiates a new abstract exception.
	 *
	 * @param message the message
	 */
	public AbstractException(final ExceptionErrorMessage ...errorMessages) {
		super();
		this.errorMessages = errorMessages;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public ExceptionErrorMessage[] getErrorMessages() {
		return this.errorMessages;
	}
}
