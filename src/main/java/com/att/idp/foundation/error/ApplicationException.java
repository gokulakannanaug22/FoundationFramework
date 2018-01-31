package com.att.idp.foundation.error;

/**
 * This is checked exception and should be thrown when application can take alternate path and continue to execute 
 * user transaction. This exception is used rarely as most of the time exceptions are none resumable. 
 * @author kamran
 *
 */
public class ApplicationException extends AbstractException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1486235980564397592L;
	
	/**
	 * Constructor method.
	 */
	public ApplicationException(final ExceptionErrorMessage ...errorMessages) {
		super(errorMessages);
	}
	
}
