package com.att.idp.foundation.pipeline;

// TODO: Auto-generated Javadoc
/**
 * Pipeline interface.
 *
 * @author HCL
 * @param <T> the generic type
 */
public interface Pipeline<T> {
	
	/**
	 * Execute pipeline.
	 *
	 * @param context the context
	 */
    void execute(T context);
}
