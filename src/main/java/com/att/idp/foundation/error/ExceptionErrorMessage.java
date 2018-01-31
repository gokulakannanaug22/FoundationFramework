package com.att.idp.foundation.error;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * This class is used by IDP exception classes to store exception messages.
 * 
 * @author kamran
 *
 */
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public final class ExceptionErrorMessage extends AbstractErrorMessage {

	/** The cause. */
	private String cause;

	/** The stack trace. */
	private String stackTrace;

	/**
	 * Instantiates a new error message.
	 */
	private ExceptionErrorMessage() {
		super();
	}

	/**
	 * Create new error message.
	 * 
	 * @return Empty ErrorMessage object
	 */
	public static ExceptionErrorMessage newObject() {
		return new ExceptionErrorMessage();
	}

	/**
	 * Set error code. All errors may not have error code but error messages
	 * which contains user message must have code.
	 *
	 * @param code
	 *            the code
	 * @return the error message
	 */
	@Override
	public ExceptionErrorMessage code(final String code) {
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
	@Override
	public ExceptionErrorMessage userMessage(final String userMessage) {
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
	@Override
	public ExceptionErrorMessage developerMessage(final String developerMessage) {
		this.developerMessage = developerMessage;
		return this;
	}
	
	/**
	 * Cause of this error. what exception caused this error? It should only be
	 * set when this error is thrown due to some exception in the code. Error
	 * due to abnormal business logic conditions does not set this.
	 * DeveloperMessage and UserMessage should be sufficient to explain or debug
	 * this error.
	 *
	 * @param cause
	 *            the cause
	 * @return the error message
	 */
	public ExceptionErrorMessage cause(final Throwable cause) {

		this.cause = cause.getMessage();
		final StringWriter stringWriter = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(stringWriter);
		cause.printStackTrace(printWriter);
		this.stackTrace = stringWriter.toString();

		return this;
	}



	/**
	 * Gets the cause.
	 *
	 * @return the cause
	 */
	public String getCause() {
		return cause;

	}

	/**
	 * Gets the stack trace.
	 *
	 * @return the stack trace
	 */
	public String getStackTrace() {
		return stackTrace;
	}

	/**
	 * (non-Javadoc).
	 *
	 * @return the string
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExceptionErrorMessage [code=" + code + ", userMessage=" + userMessage + ", developerMessage=" + developerMessage
				+ ", cause=" + cause + ", stackTrace=" + stackTrace + "]";
	}

}
