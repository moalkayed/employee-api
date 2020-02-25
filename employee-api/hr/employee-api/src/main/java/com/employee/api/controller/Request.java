package com.employee.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.employee.api.util.SortType;
import com.employee.api.util.SortableParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(with = { JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES })
public class Request {

	@JsonProperty("pageNumber")
	private Integer pageNumber;

	@JsonProperty("pageSize")
	private Integer pageSize;

	@JsonProperty("sort")
	private Map<SortableParam, SortType> sort;

	@JsonProperty("headers")
	private Map<String, String> headers;

	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * @return the pageNumber
	 */
	public Integer getPageNumber() {
		return pageNumber;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @return the sort
	 */
	public Map<SortableParam, SortType> getSort() {
		return sort;
	}

	public Map<SortableParam, SortType> defaultSort() {
		Map<SortableParam, SortType> sort = new HashMap<>();
		sort.put(SortableParam.HIRE_DATE, SortType.DESC);

		return sort;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
