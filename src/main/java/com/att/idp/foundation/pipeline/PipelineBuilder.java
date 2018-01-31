package com.att.idp.foundation.pipeline;

import java.util.function.Predicate;

import com.att.idp.foundation.pipeline.impl.PipelineImpl;


/**
 * Builds pipeline.
 *
 * @author tsukhu
 * @param <T> the generic type
 */
public final class PipelineBuilder<T> {
	
	/** The first. */
	private PipelineImpl<T> firstNode; 
	
	/** The current. */
	private PipelineImpl<T> currentNode;

	/**
	 * Builder.
	 *
	 * @param <T> the generic type
	 * @return the pipeline builder
	 */
	public static <T> PipelineBuilder<T> builder() {
		return new PipelineBuilder<>();
	}

	/**
	 * Instantiates a new pipeline builder.
	 */
	private PipelineBuilder() {
	}

	/**
	 * Instantiates a new pipeline builder.
	 *
	 * @param first the first
	 * @param current the current
	 */
	private PipelineBuilder(final PipelineImpl<T> first, final PipelineImpl<T> current) {
		this.firstNode = first;
		this.currentNode = current;
	}

	/**
	 * First.
	 *
	 * @param handler the handler
	 * @return the pipeline builder
	 */
	public PipelineBuilder<T> first(final Processor<T> handler) {
		firstNode = new PipelineImpl<>(handler);
		currentNode = firstNode;
		return new PipelineBuilder<T>(firstNode, currentNode);
	}
	
	/**
	 * Branch.
	 *
	 * @param branchBuilder the branch builder
	 * @param predicate the predicate
	 * @return the pipeline builder
	 */
	public PipelineBuilder<T> branch(final PipelineBuilder<T> branchBuilder, final Predicate<T> predicate) {
		final PipelineImpl<T> next = branchBuilder.firstNode;
		currentNode.branch(next, predicate);
		return this;
	}
	
	/**
	 * Branch.
	 *
	 * @param branchBuilder the branch builder
	 * @return the pipeline builder
	 */
	public PipelineBuilder<T> branch(final PipelineBuilder<T> branchBuilder) {
		final PipelineImpl<T> next = branchBuilder.firstNode;
		currentNode.branch(next);
		return this;
	}
	
	/**
	 * Creates new branch with single processor with predicate as lamda expression;  
	 * @param handler Lamba expression or processor.
	 * @param predicate Lamda expression.
	 * @return PipelineBuilder object to provide fluent API.
	 */
	public PipelineBuilder<T> branch(final Processor<T> handler, final Predicate<T> predicate) {
		final PipelineBuilder<T> branchBuilder = PipelineBuilder.<T>builder().first(handler);
		final PipelineImpl<T> next = branchBuilder.firstNode;
		currentNode.branch(next, predicate);
		return this;
	}


	/**
	 * Creates new branch with single processor with default contition. This branch is executed if other 
	 * branches with predicate does not satisfy condition.   
	 * @param handler Lamba expression or processor.
	 * @return PipelineBuilder object to provide fluent API.
	 */
	public PipelineBuilder<T> branch(final Processor<T> handler) {
		final PipelineBuilder<T> branchBuilder = PipelineBuilder.<T>builder().first(handler);
		final PipelineImpl<T> next = branchBuilder.firstNode;
		currentNode.branch(next);
		return this;
	}

	
	/**
	 * Join.
	 *
	 * @return the pipeline builder
	 */
	public PipelineBuilder<T> join() {
		currentNode.setJoin(true);
		return this;
	}
	
	/**
	 * Execute processor when predicate condition satisfies.  
	 * @param handler Lamba expression or processor.
	 * @param predicate Lamda expression.
	 * @return PipelineBuilder object to provide fluent API.
	 */
	public PipelineBuilder<T> next(final Processor<T> handler, final Predicate<T> predicate) {
		final PipelineImpl<T> next = new PipelineImpl<>(handler);
		currentNode.setNext(next, predicate);
		return new PipelineBuilder<T>(firstNode, next);
	}
	
	
	/**
	 * Next.
	 *
	 * @param handler the handler
	 * @return the pipeline builder
	 */
	public PipelineBuilder<T> next(final Processor<T> handler) {
		final PipelineImpl<T> next = new PipelineImpl<>(handler);
		currentNode.setNext(next);
		return new PipelineBuilder<T>(firstNode, next);
	}

	/**
	 * Builds the.
	 *
	 * @return the pipeline
	 */
	public Pipeline<T> build() {
		return firstNode;
	}
}