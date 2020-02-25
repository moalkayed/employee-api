package com.employee.api.infra.department;

import org.springframework.stereotype.Repository;

import com.employee.api.domain.Department;
import com.employee.api.infra.GenericRepository;

@Repository
public interface DepartmentRepository extends GenericRepository<Department, Long> {

}