package com.employee.api.infra.employee;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.employee.api.domain.Employee;

@NoRepositoryBean
public interface InfrastructureEmployeeRepository extends MongoRepository<Employee, String>, EmployeeRepository {

}
