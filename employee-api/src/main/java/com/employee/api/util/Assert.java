package com.employee.api.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Assert {

	public static boolean notEmpty(Boolean value) {
		return !isEmpty(value);
	}

	public static boolean isEmpty(Boolean value) {
		if (isNull(value)) {
			return true;
		}

		return false;
	}

	public static boolean notEmpty(String value) {
		return !isEmpty(value);
	}

	public static boolean isEmpty(String value) {
		if (isNull(value) || value.isEmpty()) {
			return true;
		}

		return false;
	}

	public static boolean notEmpty(Integer value) {
		return !isEmpty(value);
	}

	public static boolean isEmpty(Integer value) {
		if (isNull(value) || value == 0) {
			return true;
		}

		return false;
	}

	public static boolean notEmpty(Long value) {
		return !isEmpty(value);
	}

	public static boolean isEmpty(Long value) {
		if (isNull(value) || value == 0) {
			return true;
		}

		return false;
	}

	public static boolean notEmpty(Double value) {
		return !isEmpty(value);
	}

	public static boolean isEmpty(Double value) {
		if (isNull(value) || value == 0) {
			return true;
		}

		return false;
	}

	public static boolean notEmpty(Collection<?> value) {
		return !isEmpty(value);
	}

	public static boolean isEmpty(Collection<?> value) {
		if (isNull(value) || value.isEmpty()) {
			return true;
		}

		return false;
	}

	public static boolean notEmpty(List<?> value) {
		return !isEmpty(value);
	}

	public static boolean isEmpty(List<?> value) {
		if (isNull(value) || value.isEmpty()) {
			return true;
		}

		return false;
	}

	public static boolean notEmpty(Set<?> value) {
		return !isEmpty(value);
	}

	public static boolean isEmpty(Set<?> value) {
		if (isNull(value) || value.isEmpty()) {
			return true;
		}

		return false;
	}

	public static boolean notNull(Object value) {
		return !isNull(value);
	}

	public static boolean isNull(Object value) {
		if (value == null) {
			return true;
		}

		return false;
	}

	public static boolean isString(Object value) {
		if (isNull(value)) {
			return false;
		}

		if (value instanceof String) {
			return true;
		}

		return false;
	}

	public static boolean notString(Object value) {
		return !isString(value);
	}

	public static boolean notEmpty(byte[] value) {
		return !isEmpty(value);
	}

	public static boolean isEmpty(byte[] value) {
		if (isNull(value) || value.length == 0) {
			return true;
		}

		return false;
	}

	public static <T> boolean notEmpty(T[] value) {
		return !isEmpty(value);
	}

	public static <T> boolean isEmpty(T[] value) {
		if (isNull(value) || value.length == 0) {
			return true;
		}

		return false;
	}

	public static boolean notEmpty(Map<?, ?> value) {
		return !isEmpty(value);
	}

	public static boolean isEmpty(Map<?, ?> value) {
		if (isNull(value) || value.isEmpty()) {
			return true;
		}

		return false;
	}

	public static boolean isTrue(Boolean value) {
		if (Assert.isNull(value)) {
			return false;
		}

		return value;
	}

	public static boolean isFalse(Boolean value) {
		return !isTrue(value);
	}

}