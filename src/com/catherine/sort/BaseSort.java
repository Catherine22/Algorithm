package com.catherine.sort;

public abstract class BaseSort<T extends Comparable<? super T>> {
	protected final boolean SHOW_DEBUG_LOG = false;

	public abstract T[] sort(T[] input, boolean isAscending);
}
