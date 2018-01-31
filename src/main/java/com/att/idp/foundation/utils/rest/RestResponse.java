package com.att.idp.foundation.utils.rest;

import java.util.ArrayList;
import java.util.List;

import com.att.idp.foundation.error.ApplicationError;
import com.att.idp.foundation.error.EnvironmentError;
import com.att.idp.foundation.error.ExceptionErrorMessage;
import com.att.idp.foundation.error.InputError;
import com.att.idp.foundation.error.RestErrorMessage;

/**
 * The Class is used to generate the REST response of all REST APIs. I
 */
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public class RestResponse {

	/** The status. */
	private int status;

	/** The response. */
	private Object response;

	/** The error. */
	private List<RestErrorMessage> error;

	/**
	 * Builds the.
	 *
	 * @param service
	 *            the service
	 * @return the rest response
	 */
	@SuppressWarnings("PMD.AvoidCatchingGenericException")
	public static RestResponse build(final ServiceExecutor service) {
		final RestResponse response = new RestResponse();
		try {
			final Object res = service.execute();
			if (res == null) {
				response.status(400).errorMessage(RestErrorMessage.newObject().developerMessage("No data found."));
			} else {
				response.status(200).response(res);
			}

		} catch (InputError err) {
			response.status(400).errorMessage(err.getErrorMessages());

		} catch (ApplicationError err) {
			response.status(500).errorMessage(err.getErrorMessages());
			
		} catch (EnvironmentError err) {
			response.status(500).errorMessage(err.getErrorMessages());
		} catch (Exception ex) {
			response.status(500).errorMessage(RestErrorMessage.newObject().developerMessage(ex.getMessage()));
		}
		return response;
	}

	/**
	 * set status.
	 *
	 * @param status
	 *            the status
	 * @return the rest response
	 */
	private RestResponse status(final int status) {
		this.status = status;
		return this;
	}

	/**
	 * set response.
	 *
	 * @param response
	 *            the response
	 * @return the rest response
	 */
	private RestResponse response(final Object response) {
		this.response = response;
		return this;
	}

	/**
	 * set error messages.
	 *
	 * @param error
	 *            the error
	 * @return the rest response
	 */
	private RestResponse errorMessage(final ExceptionErrorMessage ...errors) {
		error = new ArrayList<RestErrorMessage>();
		
		for(final ExceptionErrorMessage errorMessage: errors) {
			error.add(RestErrorMessage.newObject()
								.code(errorMessage.getCode())
								.userMessage(errorMessage.getUserMessage())
								.developerMessage(errorMessage.getDeveloperMessage())
							  );
		}
		return this;
	}
	
	/**
	 * set error messages.
	 *
	 * @param error
	 *            the error
	 * @return the rest response
	 */
	private RestResponse errorMessage(final RestErrorMessage ...errors) {
		error = new ArrayList<RestErrorMessage>();
		
		for(final RestErrorMessage errorMessage: errors) {
			error.add(errorMessage);
		}
		return this;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public Object getResponse() {
		return response;
	}

	/**
	 * Gets the error.
	 *
	 * @return the error
	 */
	public List<RestErrorMessage> getError() {
		return error;
	}
}
