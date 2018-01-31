package com.att.idp.foundation.pipeline;

// TODO: Auto-generated Javadoc
/**
 * Pipeline Processor.
 *
 * @author HCL
 * @param <T> the generic type
 */
public interface Processor<T> {
    
    /**
     * execute.
     *
     * @param context the context
     */
	void execute(T context);
}