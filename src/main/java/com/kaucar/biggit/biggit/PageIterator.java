package com.kaucar.biggit.biggit;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class PageIterator<E> implements Iterator<E> {
	protected final Deque<E> fetchedItems = new LinkedList<>();

	@Override
	public boolean hasNext() {
		if (fetchedItems.size() <= 1) {
			List<E> newItems = new ArrayList<>();
			try {
				newItems.addAll(fetchItems());
			} catch (Throwable e) {
				e.printStackTrace();
			}
			fetchedItems.addAll(newItems);
		}
		return !fetchedItems.isEmpty();
	}

	@Override
	public E next() {
		return fetchedItems.poll();
	}
	
	protected abstract List<? extends E> fetchItems() throws Exception;
}