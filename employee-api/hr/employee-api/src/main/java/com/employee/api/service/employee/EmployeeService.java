package com.employee.api.service.employee;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.api.domain.Employee;
import com.employee.api.error.exception.EmployeeException;
import com.employee.api.error.exception.GenericException;
import com.employee.api.infra.employee.EmployeeRepository;
import com.employee.api.infra.query.QueryParam;
import com.employee.api.service.QueryMapper;
import com.employee.api.service.employee.EmployeeList.EmployeeListBuilder;
import com.employee.api.util.Assert;
import com.employee.api.util.Page;
import com.employee.api.util.Pageable;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	public Optional<EmployeeList> getById(Long id) throws GenericException {

		Optional<Employee> employee = repository.getById(id, Employee.class);
		if (!employee.isPresent()) {
			return Optional.empty();
		}

		EmployeeList employeeList = populateEmployeeList(employee);
		return Optional.ofNullable(employeeList);
	}

	public Optional<EmployeeList> getByIds(Pageable pageable, Set<Long> ids) throws GenericException {

		Page<Employee> employees = repository.getByIds(pageable, ids, Employee.class);
		if (Assert.isNull(employees) || employees.isEmpty()) {
			return Optional.empty();
		}

		EmployeeList employeeList = populateEmployeeList(employees);
		return Optional.ofNullable(employeeList);
	}

	public Optional<EmployeeList> getByFilter(Pageable pageable, EmployeeQueryValues values) throws EmployeeException {
		QueryMapper mapper = new QueryMapper();
		QueryParam[] param = mapper.map(values);

		if (Assert.isEmpty(param)) {
			return Optional.empty();
		}

		Page<Employee> employees = repository.getByFilter(pageable, param);
		if (Assert.isNull(employees) || employees.isEmpty()) {
			return Optional.empty();
		}

		EmployeeList employeeList = populateEmployeeList(employees);
		return Optional.ofNullable(employeeList);
	}

	public Optional<EmployeeList> save(Employee employee) throws GenericException {
		Optional<Employee> employees = repository.save(employee, Employee.class);

		if (!employees.isPresent()) {
			return Optional.empty();
		}

		EmployeeList employeeList = populateEmployeeList(employees);
		return Optional.ofNullable(employeeList);
	}

	public Boolean delete(Long id) throws GenericException {
		return repository.delete(id, Employee.class);    
	}
	
	private EmployeeList populateEmployeeList(Page<Employee> employees) {
		if (Assert.isEmpty(employees.getContent())) {
			return null;
		}

		EmployeeListBuilder employeeListBuilder = new EmployeeListBuilder();

		employeeListBuilder.withEmployees(employees.getContent());
		employeeListBuilder.withPageable(employees.getPageable());

		EmployeeList employeeList = employeeListBuilder.build();
		return employeeList;
	}

	private EmployeeList populateEmployeeList(Optional<Employee> employee) {
		EmployeeListBuilder employeeListBuilder = new EmployeeListBuilder();

		List<Employee> employees = Arrays.asList(employee.get());
		employeeListBuilder.withEmployees(employees);
		employeeListBuilder.withPageable(Pageable.DEFAULT_PAGEABLE);

		EmployeeList employeeList = employeeListBuilder.build();
		return employeeList;
	}
}
