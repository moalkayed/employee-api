package com.employee.api.infra.employee;

import org.springframework.stereotype.Repository;

import com.employee.api.domain.Employee;
import com.employee.api.infra.GenericRepository;
import com.employee.api.infra.query.QueryParam;
import com.employee.api.util.Page;
import com.employee.api.util.Pageable;

@Repository
public interface EmployeeRepository extends GenericRepository<Employee, Long> {

	public Page<Employee> getByFilter(Pageable pageable, QueryParam... qps);
}
