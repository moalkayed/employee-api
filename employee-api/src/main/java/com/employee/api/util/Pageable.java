package com.employee.api.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Pageable implements Serializable {

	private static final long serialVersionUID = 4537260882932924871L;
	
	public static final Pageable DEFAULT_PAGEABLE;
	public static final Map<SortableParam, SortType> DEFAULT_SORT;

	private final Integer pageNumber;
	private final Integer pageSize;
	private final Long totalNumber;
	private final Boolean hasNext;
	private final Map<SortableParam, SortType> sort;

	static {
		DEFAULT_SORT = new HashMap<>();
		DEFAULT_SORT.put(SortableParam.HIRE_DATE, SortType.DESC);

		DEFAULT_PAGEABLE = new Pageable(new PageableBuilder().withPageNumber(0).withPageSize(1).withSort(DEFAULT_SORT)
				.withTotalNumber(1L).withHasNext(false));
	}

	private Pageable(PageableBuilder builder) {
		this.pageNumber = builder.pageNumber;
		this.pageSize = builder.pageSize;
		this.totalNumber = builder.totalNumber;
		this.hasNext = builder.hasNext;
		this.sort = builder.sort;
	}

	public static class PageableBuilder {

		private Integer pageNumber;
		private Integer pageSize;
		private Long totalNumber;
		private Boolean hasNext;
		private Map<SortableParam, SortType> sort;

		public PageableBuilder() {
		}

		public PageableBuilder withPageNumber(Integer pageNumber) {
			this.pageNumber = pageNumber;
			return this;
		}

		public PageableBuilder withPageSize(Integer pageSize) {
			this.pageSize = pageSize;
			return this;
		}

		public PageableBuilder withTotalNumber(Long totalNumber) {
			this.totalNumber = totalNumber;
			return this;
		}

		public PageableBuilder withHasNext(Boolean hasNext) {
			this.hasNext = hasNext;
			return this;
		}

		public PageableBuilder withSort(Map<SortableParam, SortType> sort) {
			this.sort = sort;
			return this;
		}

		public Pageable build() {
			return new Pageable(this);
		}

	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public Long getTotalNumber() {
		return totalNumber;
	}

	public Boolean getHasNext() {
		return hasNext;
	}

	public Map<SortableParam, SortType> getSort() {
		return sort;
	}

	public static Pageable of(int pageNumber, int pageSize) {
		return new PageableBuilder().withPageNumber(pageNumber).withPageSize(pageSize).withHasNext(false)
				.withTotalNumber(0L).withSort(DEFAULT_SORT).build();
	}

	public static Pageable of(int pageNumber, int pageSize, Map<SortableParam, SortType> sort) {
		return new PageableBuilder().withPageNumber(pageNumber).withPageSize(pageSize).withHasNext(false)
				.withTotalNumber(0L).withSort(sort).build();
	}

	public static Pageable of(int pageNumber, int pageSize, SortableParam sortableParam, SortType sortType) {
		Map<SortableParam, SortType> sort = new HashMap<>();
		sort.put(sortableParam, sortType);

		return new PageableBuilder().withPageNumber(pageNumber).withPageSize(pageSize).withHasNext(false)
				.withTotalNumber(0L).withSort(sort).build();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
