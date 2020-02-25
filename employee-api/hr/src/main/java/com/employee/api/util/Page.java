package com.employee.api.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Page<T> implements Serializable {

	private static final long serialVersionUID = -6783160787696178818L;

	private final List<T> content;
	private final Pageable pageable;

	private Page(PageBuilder<T> builder) {
		this.content = builder.content;
		this.pageable = builder.pageable;
	}

	public static <T> Page<T> empty(Pageable pageable) {
		Pageable resultPageable = populateEmptyPageable(pageable);

		Page<T> emptyPage = populateEmptyPage(resultPageable);

		return emptyPage;
	}

	private static <T> Page<T> populateEmptyPage(Pageable resultPageable) {
		PageBuilder<T> pageBuilder = new Page.PageBuilder<T>();
		pageBuilder.withContent(Collections.emptyList());
		pageBuilder.withPageable(resultPageable);

		Page<T> emptyPage = pageBuilder.build();
		return emptyPage;
	}

	private static Pageable populateEmptyPageable(Pageable pageable) {
		Pageable.PageableBuilder pageableBuilder = new Pageable.PageableBuilder();

		pageableBuilder.withTotalNumber(0l);
		pageableBuilder.withPageNumber(pageable.getPageNumber());
		pageableBuilder.withPageSize(pageable.getPageSize());

		Pageable resultPageable = pageableBuilder.build();
		return resultPageable;
	}

	public boolean isEmpty() {
		return content.isEmpty();
	}

	public static class PageBuilder<T> {

		private List<T> content;
		private Pageable pageable;

		public PageBuilder() {
		}

		public PageBuilder<T> withContent(List<T> content) {
			this.content = content;
			return this;
		}

		public PageBuilder<T> withPageable(Pageable pageable) {
			this.pageable = pageable;
			return this;
		}

		public Page<T> build() {
			return new Page<T>(this);
		}

	}

	public List<T> getContent() {
		return content;
	}

	public Pageable getPageable() {
		return pageable;
	}

}
