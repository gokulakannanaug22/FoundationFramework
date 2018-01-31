package com.att.idp.foundation.error;

/**
 * Abstract Error class. Exception which extends this class are supposed to be thrown if application can not 
 * proceed to complete user transaction due to abnormal condition and must exit and error must be returned. These are unchecked exceptions and should not
 * be caught in the code. This is caught at the Rest interface by the RestResponse class to build the error response.  
 * @author kamran
 *
 */

@SuppressWarnings({"PMD.DoNotExtendJavaLangError", "PMD.MethodReturnsInternalArray"})
public abstract class AbstractError extends Error {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3705265262302703688L;
	
	/** Error messages. */	
	private final ExceptionErrorMessage[] errorMessages;
	
	/**
	 * Instantiates a new abstract error.
	 *
	 * @param message the message
	 */
	public AbstractError(final ExceptionErrorMessage ...errorMessages ) {
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
