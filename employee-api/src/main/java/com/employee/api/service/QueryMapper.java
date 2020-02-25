package com.employee.api.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.employee.api.infra.query.QueryParam;
import com.employee.api.service.employee.EmployeeQueryValues;
import com.employee.api.util.Assert;

public class QueryMapper {

	public QueryParam[] map(EmployeeQueryValues queryValues) {
		List<QueryParam> qparamList = new ArrayList<>();

		populateFirstName(queryValues, qparamList);
		populateLastName(queryValues, qparamList);
		populateEmail(queryValues, qparamList);
		populatePhoneNumber(queryValues, qparamList);
		populateMaritalStatus(queryValues, qparamList);
		populateDOB(queryValues, qparamList);
		populateNationality(queryValues, qparamList);
		populateHireDate(queryValues, qparamList);
		populateSalary(queryValues, qparamList);
		populateManagerId(queryValues, qparamList);
		populateDepartmentId(queryValues, qparamList);
		populateActive(queryValues, qparamList);

		return qparamList.toArray(new QueryParam[qparamList.size()]);

	}

	private void populateFirstName(EmployeeQueryValues queryValues, List<QueryParam> qparamList) {
		if (queryValues == null) {
			return;
		}

		String firstName = queryValues.getFirstName();
		if (Assert.isNull(firstName)) {
			return;
		}

		QueryParam param = eqQuery(FilterableParam.FIRST_NAME, firstName);
		qparamList.add(param);
	}

	private void populateLastName(EmployeeQueryValues queryValues, List<QueryParam> qparamList) {
		if (queryValues == null) {
			return;
		}

		String lastName = queryValues.getLastName();
		if (Assert.isNull(lastName)) {
			return;
		}

		QueryParam param = eqQuery(FilterableParam.LAST_NAME, lastName);
		qparamList.add(param);
	}

	private void populateEmail(EmployeeQueryValues queryValues, List<QueryParam> qparamList) {
		if (queryValues == null) {
			return;
		}

		String email = queryValues.getEmail();
		if (Assert.isNull(email)) {
			return;
		}

		QueryParam param = eqQuery(FilterableParam.EMAIL, email);
		qparamList.add(param);
	}

	private void populatePhoneNumber(EmployeeQueryValues queryValues, List<QueryParam> qparamList) {
		if (queryValues == null) {
			return;
		}

		String phoneNumber = queryValues.getPhoneNumber();
		if (Assert.isNull(phoneNumber)) {
			return;
		}

		QueryParam param = eqQuery(FilterableParam.PHONE_NUMBER, phoneNumber);
		qparamList.add(param);
	}

	private void populateMaritalStatus(EmployeeQueryValues queryValues, List<QueryParam> qparamList) {
		if (queryValues == null) {
			return;
		}

		String maritalStatus = queryValues.getMaritalStatus();
		if (Assert.isNull(maritalStatus)) {
			return;
		}

		QueryParam param = eqQuery(FilterableParam.MARITAL_STATUS, maritalStatus);
		qparamList.add(param);
	}

	private void populateDOB(EmployeeQueryValues queryValues, List<QueryParam> qparamList) {
		if (queryValues == null) {
			return;
		}

		Integer yearOfBirthday = queryValues.getYearOfBirthday();
		if (Assert.isNull(yearOfBirthday)) {
			return;
		}

		Calendar instance = Calendar.getInstance();

		Integer from = yearOfBirthday;
		instance.set(from, 0, 1, 0, 0);
		Date dateFrom = instance.getTime();

		Integer to = yearOfBirthday + 1;
		instance.set(to, 0, 1, 0, 0);
		Date dateTo = instance.getTime();

		Object[] DOB = new Object[] { dateFrom, dateTo };
		QueryParam param = betweenQuery(FilterableParam.DOB, DOB);
		qparamList.add(param);
	}

	private void populateNationality(EmployeeQueryValues queryValues, List<QueryParam> qparamList) {
		if (queryValues == null) {
			return;
		}

		String nationality = queryValues.getNationality();
		if (Assert.isNull(nationality)) {
			return;
		}

		QueryParam param = eqQuery(FilterableParam.NATIONALITY, nationality);
		qparamList.add(param);
	}

	private void populateHireDate(EmployeeQueryValues queryValues, List<QueryParam> qparamList) {
		if (queryValues == null) {
			return;
		}

		Object[] hireDates = null;
		QueryParam param = null;

		Date hireDateFrom = queryValues.getHireDateFrom();
		Date hireDateTo = queryValues.getHireDateTo();
		if (Assert.notNull(hireDateFrom) && Assert.notNull(hireDateTo)) {
			hireDates = new Object[] { hireDateFrom, hireDateTo };
			param = betweenQuery(FilterableParam.HIRE_DATE, hireDates);
		} else if (Assert.notNull(hireDateFrom)) {
			param = gtQuery(FilterableParam.HIRE_DATE, hireDateFrom);
		} else if (Assert.notNull(hireDateTo)) {
			param = ltQuery(FilterableParam.HIRE_DATE, hireDateTo);
		}

		if (!Assert.isNull(param)) {
			qparamList.add(param);
		}
	}

	private void populateSalary(EmployeeQueryValues queryValues, List<QueryParam> qparamList) {
		if (queryValues == null) {
			return;
		}

		Double salary = queryValues.getSalary();
		if (Assert.isNull(salary)) {
			return;
		}

		QueryParam param = lteQuery(FilterableParam.SALARY, salary);
		qparamList.add(param);
	}

	private void populateManagerId(EmployeeQueryValues queryValues, List<QueryParam> qparamList) {
		if (queryValues == null) {
			return;
		}

		Long managerId = queryValues.getManagerId();
		if (Assert.isNull(managerId)) {
			return;
		}

		QueryParam param = eqQuery(FilterableParam.MANAGER_ID, managerId);
		qparamList.add(param);
	}

	private void populateDepartmentId(EmployeeQueryValues queryValues, List<QueryParam> qparamList) {
		if (queryValues == null) {
			return;
		}

		Long depratmentId = queryValues.getDepratmentId();
		if (Assert.isNull(depratmentId)) {
			return;
		}

		QueryParam param = eqQuery(FilterableParam.DEPARTMENT_ID, depratmentId);
		qparamList.add(param);
	}

	private void populateActive(EmployeeQueryValues queryValues, List<QueryParam> qparamList) {
		if (queryValues == null) {
			return;
		}

		Boolean active = queryValues.getActive();
		if (Assert.isNull(active)) {
			return;
		}

		QueryParam param = eqQuery(FilterableParam.ACTIVE, active);
		qparamList.add(param);
	}

	QueryParam query(FilterableParam filterableParam, OperatorEnum operatorEnum, Object val) {
		return new QueryParam(filterableParam.getParam(), operatorEnum.toString(), val);
	}

	QueryParam inQuery(FilterableParam filterableParam, Object val) {
		return query(filterableParam, OperatorEnum.IN, val);
	}

	QueryParam eqQuery(FilterableParam filterableParam, Object val) {

		return query(filterableParam, OperatorEnum.EQ, val);
	}

	QueryParam betweenQuery(FilterableParam filterableParam, Object val) {
		return query(filterableParam, OperatorEnum.BETWEEN, val);
	}

	QueryParam gtQuery(FilterableParam filterableParam, Object val) {
		return query(filterableParam, OperatorEnum.GT, val);
	}

	QueryParam ltQuery(FilterableParam filterableParam, Object val) {
		return query(filterableParam, OperatorEnum.LT, val);
	}

	QueryParam eqlQuery(FilterableParam filterableParam, Object val) {
		return query(filterableParam, OperatorEnum.EQL, val);
	}

	QueryParam gteQuery(FilterableParam filterableParam, Object val) {
		return query(filterableParam, OperatorEnum.GTE, val);
	}

	QueryParam lteQuery(FilterableParam filterableParam, Object val) {
		return query(filterableParam, OperatorEnum.LTE, val);
	}

	boolean checkCollection(Collection<?> collection) {
		return collection == null || collection.size() <= 0;
	}
}
