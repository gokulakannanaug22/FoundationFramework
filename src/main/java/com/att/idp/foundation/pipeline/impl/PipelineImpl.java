package com.att.idp.foundation.pipeline.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import com.att.idp.foundation.pipeline.Pipeline;
import com.att.idp.foundation.pipeline.Processor;

// TODO: Auto-generated Javadoc
/**
 * The Class PipelineImpl.
 *
 * @param <T>
 *            the generic type
 */
public class PipelineImpl<T> implements Pipeline<T> {

	/** The processor. */
	private final Processor<T> processor;

	/** The next. */
	private Pipeline<T> next;
	
	/** predicate for next **/
	private Predicate<T> nextPredicate;
	

	/** The join. */
	private boolean join;

	/** The branch map. */
	private final ConcurrentHashMap<Predicate<T>, Pipeline<T>> branchMap = new ConcurrentHashMap<Predicate<T>, Pipeline<T>>();

	/** The default branch. */
	private Pipeline<T> defaultBranch;
	

	/**
	 * Instantiates a new pipeline impl.
	 *
	 * @param current
	 *            the current
	 */
	public PipelineImpl(final Processor<T> current) {
		this.processor = current;
	}

	/**
	 * Sets the next.
	 *
	 * @param next
	 *            the new next
	 */
	public void setNext(final Pipeline<T> next) {
		if ((defaultBranch != null || !(branchMap.isEmpty())) && !join) {
			throw new IllegalStateException("next can not be called if in branch mode without join.");
		}
		this.next = next;
	}
	
	/**
	 * Branch.
	 *
	 * @param next
	 *            the next
	 * @param predicte
	 *            the predicte
	 */
	public void setNext(final Pipeline<T> next, final Predicate<T> predicte) {
		if ((defaultBranch != null || !(branchMap.isEmpty())) && !join) {
			throw new IllegalStateException("next can not be called if in branch mode without join.");
		}
		this.next = next;
		this.nextPredicate = predicte;
	}
	

	/**
	 * Sets the join.
	 *
	 * @param join
	 *            the new join
	 */
	public void setJoin(final boolean join) {
		this.join = join;
	}

	/**
	 * Branch.
	 *
	 * @param next
	 *            the next
	 * @param predicte
	 *            the predicte
	 */
	public void branch(final Pipeline<T> next, final Predicate<T> predicte) {
		branchMap.put(predicte, next);
	}

	/**
	 * Branch.
	 *
	 * @param next
	 *            the next
	 */
	public void branch(final Pipeline<T> next) {
		defaultBranch = next;
	}

	/**
	 * execute.
	 *
	 * @param context the context
	 */
	@Override
	@SuppressWarnings({ "PMD.UselessParentheses", "PMD.ConfusingTernary","PMD.CyclomaticComplexity"})
	public void execute(final T context) {
		processor.execute(context);
		final List<Pipeline<T>> branchPipelines = getBranchPipeline(context);
		if((!branchPipelines.isEmpty()) || (defaultBranch != null)) {
			//If in branch mode, execute all branches that satisfy conditions otherwise default branch pipeline if exist
			if(!branchPipelines.isEmpty()) {
				for(final Pipeline<T> pipeline: branchPipelines) {
					pipeline.execute(context);
				}
			} else if (defaultBranch != null) {
				defaultBranch.execute(context);
			}
			//if branches are joined then continues the pipeline chain
			if(join && (next != null)) {
		            next.execute(context);
			}
		} else {
			if(next != null) {
				if(nextPredicate!=null) {
					if(nextPredicate.test(context)) {
						next.execute(context);
					}
				} else {
					next.execute(context);
				}
				
			}
		}
	}

	/**
	 * Gets the branch pipeline.
	 *
	 * @param context
	 *            the context
	 * @return the branch pipeline
	 */
	private List<Pipeline<T>> getBranchPipeline(final T context) {
		final List<Pipeline<T>> pipelines = new ArrayList<Pipeline<T>>();
		for (final Entry<Predicate<T>, Pipeline<T>> entry : branchMap.entrySet()) {
			if (entry.getKey().test(context)) {
				pipelines.add(entry.getValue());
			}
		}
		return pipelines;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
	 * To String.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "PipelineImpl [processor=" + processor + ", next=" + next + ", join=" + join + ", branchMap=" + branchMap
				+ ", defaultBranch=" + defaultBranch + "]";
	}
}