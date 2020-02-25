package com.employee.api.util;

import java.util.List;

public class PageImpl<T> {

	private final List<T> content;
	private Pageable pageable;
	private final long total;

	public PageImpl(List<T> content, Pageable pageable, long total) {
		this.content = content;
		this.pageable = pageable;
		this.total = total;
	}

	public PageImpl(List<T> content, long total) {
		this.content = content;
		this.total = total;
	}

	public Pageable getPageable() {
		Pageable.PageableBuilder pageableBuilder = new Pageable.PageableBuilder();

		pageableBuilder.withTotalNumber(total);
		pageableBuilder.withPageNumber(pageable.getPageNumber());
		pageableBuilder.withPageSize(pageable.getPageSize());
		pageableBuilder.withHasNext(hasNext());

		return pageableBuilder.build();
	}

	public boolean hasNext() {
		return pageable.getPageNumber() + 1 < getTotalPages();
	}

	public boolean isLast() {
		return !hasNext();
	}

	public int getTotalPages() {
		return pageable.getPageSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) pageable.getPageSize());
	}

	public List<T> getContent() {
		return content;
	}

}
