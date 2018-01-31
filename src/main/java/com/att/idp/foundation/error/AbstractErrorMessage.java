package com.att.idp.foundation.error;

// TODO: Auto-generated Javadoc
/**
 * This class is the parent of RestErrorMessage and ExceptionErrorMessage.
 * RestErrorMessage class is for sending the error message in a REST response.
 * ExceptionErrorMessage class is used for sending the error message in IDP exception classes. 
 * 
 * @author kamran
 *
 */
@SuppressWarnings({"PMD.AvoidFieldNameMatchingMethodName", "PMD.AbstractClassWithoutAbstractMethod"})
public abstract class AbstractErrorMessage {

	/** The code. */
	public String code;
	
	/** The user message. */
	public String userMessage;
	
	/** The developer message. */
	public String developerMessage;


	/**
	 * Set error code. All errors may not have error code but error messages
	 * which contains user message must have code.
	 *
	 * @param code
	 *            the code
	 * @return the error message
	 */
	public AbstractErrorMessage code(final String code) {
		this.code = code;
		return this;
	}

	/**
	 * Error message to be shown to user/customer.
	 *
	 * @param userMessage
	 *            the user message
	 * @return the error message
	 */
	public AbstractErrorMessage userMessage(final String userMessage) {
		this.userMessage = userMessage;
		return this;
	}

	/**
	 * Detail error message which developer can use to debug the application
	 * issues.
	 *
	 * @param developerMessage
	 *            the developer message
	 * @return the error message
	 */
	public AbstractErrorMessage developerMessage(final String developerMessage) {
		this.developerMessage = developerMessage;
		return this;
	}


	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Gets the user message.
	 *
	 * @return the user message
	 */
	public String getUserMessage() {
		return this.userMessage;
	}

	/**
	 * Gets the developer message.
	 *
	 * @return the developer message
	 */
	public String getDeveloperMessage() {
		return this.developerMessage;
	}

}
